/*
 * Copyright (c) 2016. Sten Martinez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package net.longfalcon.newsj;

import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.model.Message;
import net.longfalcon.newsj.model.MessagePart;
import net.longfalcon.newsj.model.Part;
import net.longfalcon.newsj.model.PartRepair;
import net.longfalcon.newsj.nntp.NntpConnectionFactory;
import net.longfalcon.newsj.nntp.client.NewsArticle;
import net.longfalcon.newsj.nntp.client.NewsClient;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.persistence.PartDAO;
import net.longfalcon.newsj.persistence.PartRepairDAO;
import net.longfalcon.newsj.util.ArrayUtil;
import net.longfalcon.newsj.util.Defaults;
import net.longfalcon.newsj.util.EncodingUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.nntp.Article;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 10/23/15
 * Time: 9:26 PM
 */
@Service
public class FetchBinaries {
    private static PeriodFormatter _periodFormatter = PeriodFormat.wordBased();
    public static int MESSAGE_BUFFER = 20000;
    private static final Log _log = LogFactory.getLog(FetchBinaries.class);

    private Blacklist blacklist;
    private BinaryDAO binaryDAO;
    private PartDAO partDAO;
    private PartRepairDAO partRepairDAO;
    private NntpConnectionFactory nntpConnectionFactory;
    private PlatformTransactionManager transactionManager;

    // This method should be an atomic transaction
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public long scan(NewsClient nntpClient, Group group, long firstArticle, long lastArticle, String type, boolean compressedHeaders) throws IOException {
        // this is a hack - tx is not working ATM
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));

        long startHeadersTime = System.currentTimeMillis();

        long maxNum = 0;
        Map<String, Message> messages = new LinkedHashMap<>(MESSAGE_BUFFER + 1);

        Iterable<NewsArticle> articlesIterable = null;
        try {
            if (compressedHeaders) {
                _log.warn("Compressed Headers setting not currently functional");
                articlesIterable = nntpClient.iterateArticleInfo(firstArticle, lastArticle);
            } else {
                articlesIterable = nntpClient.iterateArticleInfo(firstArticle, lastArticle);
            }
        } catch (IOException e) {
            _log.error(e.toString());
            if (nntpClient.getReplyCode() == 400) {
                _log.info("NNTP connection timed out. Reconnecting...");
                nntpClient = nntpConnectionFactory.getNntpClient();
                nntpClient.selectNewsgroup(group.getName());
                articlesIterable = nntpClient.iterateArticleInfo(firstArticle, lastArticle);
            }
        }

        Period headersTime = new Period(startHeadersTime, System.currentTimeMillis());

        Set<Long> rangeRequested = ArrayUtil.rangeSet(firstArticle, lastArticle);
        Set<Long> messagesReceived = new HashSet<>();
        Set<Long> messagesBlacklisted = new HashSet<>();
        Set<Long> messagesIgnored = new HashSet<>();
        Set<Long> messagesInserted = new HashSet<>();
        Set<Long> messagesNotInserted = new HashSet<>();

        // check error codes?

        long startUpdateTime = System.currentTimeMillis();

        if (articlesIterable != null) {
            for( NewsArticle article : articlesIterable) {
                long articleNumber = article.getArticleNumberLong();

                if (articleNumber == 0) {
                    continue;
                }

                messagesReceived.add(articleNumber);

                Pattern pattern = Defaults.PARTS_SUBJECT_REGEX;
                String subject = article.getSubject();
                Matcher matcher = pattern.matcher(subject);
                if (ValidatorUtil.isNull(subject) || !matcher.find()) {
                    // not a binary post most likely.. continue
                    messagesIgnored.add(articleNumber);
                    if (_log.isDebugEnabled()) {
                        _log.debug(String.format("Skipping message no# %s : %s", articleNumber, subject));
                    }
                    continue;
                }

                //Filter binaries based on black/white list
                if (isBlacklisted(article, group)) {
                    messagesBlacklisted.add(articleNumber);
                    continue;
                }
                String group1 = matcher.group(1);
                String group2 = matcher.group(2);
                if (ValidatorUtil.isNumeric(group1) && ValidatorUtil.isNumeric(group2)) {
                    int currentPart = Integer.parseInt(group1);
                    int maxParts = Integer.parseInt(group2);
                    subject = (matcher.replaceAll("")).trim();

                    if (!messages.containsKey(subject)) {
                        messages.put(subject, new Message(article, currentPart, maxParts));
                    } else if (currentPart > 0) {
                        Message message = messages.get(subject);
                        String articleId = article.getArticleId();
                        String messageId = articleId.substring(1, articleId.length()-1);
                        int size = article.getSize();
                        message.addPart(currentPart, messageId, articleNumber, size);
                        messages.put(subject, message);
                    }
                }
            }

            long count = 0;
            long updateCount = 0;
            long partCount = 0;
            maxNum = lastArticle;

            // add all the requested then remove the ones we did receive.
            Set<Long> rangeNotRecieved = new HashSet<>();
            rangeNotRecieved.addAll(rangeRequested);
            rangeNotRecieved.removeAll(messagesReceived);

            if (!type.equals("partrepair")) {
                _log.info(String.format("Received %d articles of %d requested, %d blacklisted, %d not binaries", messagesReceived.size(), lastArticle - firstArticle + 1, messagesBlacklisted.size(), messagesIgnored.size()));
            }

            if ( rangeNotRecieved.size() > 0) {
                switch (type) {
                    case "backfill":
                        // don't add missing articles
                        break;
                    case "partrepair":
                    case "update":
                    default:
                        addMissingParts(rangeNotRecieved, group);
                        break;
                }
                _log.info("Server did not return article numbers " + ArrayUtil.stringify(rangeNotRecieved));
            }

            if (!messages.isEmpty()) {

                long dbUpdateTime = 0;
                maxNum = firstArticle;
                //insert binaries and parts into database. when binary already exists; only insert new parts
                for (Map.Entry<String, Message> entry : messages.entrySet()) {
                    String subject = entry.getKey();
                    Message message = entry.getValue();

                    Map<Integer, MessagePart> partsMap = message.getPartsMap();
                    if (!ValidatorUtil.isNull(subject) && !partsMap.isEmpty()) {
                        String binaryHash = EncodingUtil.md5Hash(subject + message.getFrom() + String.valueOf(group.getId()));
                        Binary binary = binaryDAO.findByBinaryHash(binaryHash);
                        if (binary == null) {
                            long startDbUpdateTime = System.currentTimeMillis();
                            binary = new Binary();
                            binary.setName(subject);
                            binary.setFromName(message.getFrom());
                            binary.setDate(message.getDate().toDate());
                            binary.setXref(message.getxRef());
                            binary.setTotalParts(message.getMaxParts());
                            binary.setGroupId(group.getId());
                            binary.setBinaryHash(binaryHash);
                            binary.setDateAdded(new Date());
                            binaryDAO.updateBinary(binary);
                            dbUpdateTime += (System.currentTimeMillis() - startDbUpdateTime);
                            count++;
                            if (count%500 == 0) {
                                _log.info(String.format("%s bin adds...", count));
                            }
                        } else {
                            updateCount++;
                            if (updateCount%500 == 0) {
                                _log.info(String.format("%s bin updates...", updateCount));
                            }
                        }

                        long binaryId = binary.getId();
                        if (binaryId == 0) {
                            throw new RuntimeException("ID for binary wasnt set.");
                        }

                        for( MessagePart messagePart : message.getPartsMap().values()) {
                            long articleNumber = messagePart.getArticleNumber();
                            maxNum = (articleNumber > maxNum) ? articleNumber : maxNum;
                            partCount++;
                            // create part - its possible some bugs are happening here.
                            Part part = new Part();
                            part.setBinaryId(binaryId);
                            part.setMessageId(messagePart.getMessageId());
                            part.setNumber(messagePart.getArticleNumber());
                            part.setPartNumber(messagePart.getPartNumber());
                            part.setSize(messagePart.getSize());
                            part.setDateAdded(new Date());
                            try {
                                long startDbUpdateTime = System.currentTimeMillis();
                                partDAO.updatePart(part);
                                dbUpdateTime += (System.currentTimeMillis() - startDbUpdateTime);
                                messagesInserted.add(messagePart.getArticleNumber());
                            } catch (Exception e) {
                                _log.error(e.toString());
                                messagesNotInserted.add(messagePart.getArticleNumber());
                            }

                        }
                    }
                }
                //TODO: determine whether to add to missing articles if insert failed
                if (messagesNotInserted.size() > 0) {
                    _log.warn("WARNING: Parts failed to insert");
                    addMissingParts(messagesNotInserted, group);
                }
                Period dbUpdatePeriod = new Period(dbUpdateTime);
                _log.info("Spent " + _periodFormatter.print(dbUpdatePeriod) + " updating the db");
            }
            Period updateTime = new Period(startUpdateTime, System.currentTimeMillis());

            if (!type.equals("partrepair")) {
                _log.info(count + " new, " + updateCount + " updated, " + partCount + " parts.");
                _log.info(" " + _periodFormatter.print(headersTime) + " headers, " + _periodFormatter.print(updateTime) + " update.");
            }
            transactionManager.commit(transaction);
            return maxNum;
        } else {
            _log.error("Error: Can't get parts from server (msgs not array)\n Skipping group");
            return 0;
        }

    }

    /**
     * convenience wrapper.
     * @param missingMessages
     * @param group
     */
    private void addMissingParts(long[] missingMessages, Group group) {
        Set<Long> missingMessagesSet = new HashSet<>(ArrayUtil.asList(ArrayUtils.toObject(missingMessages)));
        addMissingParts(missingMessagesSet, group);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addMissingParts(Set<Long> missingMessages, Group group) {
        long groupId = group.getId();
        for (Long number : missingMessages) {
            PartRepair partRepair = partRepairDAO.findByArticleNumberAndGroupId(number, groupId);
            if (partRepair == null) {
                partRepair = new PartRepair();
                partRepair.setNumberId(number);
                partRepair.setGroupId(groupId);
                partRepair.setAttempts(0);
            } else {
                int attempts = partRepair.getAttempts();
                partRepair.setAttempts(attempts+1);
            }

            partRepairDAO.updatePartRepair(partRepair);
        }
    }

    private boolean isBlacklisted(Article article, Group group) {
        return blacklist.isBlackListed(article, group);
    }

    public Blacklist getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Blacklist blacklist) {
        this.blacklist = blacklist;
    }

    public BinaryDAO getBinaryDAO() {
        return binaryDAO;
    }

    public void setBinaryDAO(BinaryDAO binaryDAO) {
        this.binaryDAO = binaryDAO;
    }

    public PartDAO getPartDAO() {
        return partDAO;
    }

    public void setPartDAO(PartDAO partDAO) {
        this.partDAO = partDAO;
    }

    public PartRepairDAO getPartRepairDAO() {
        return partRepairDAO;
    }

    public void setPartRepairDAO(PartRepairDAO partRepairDAO) {
        this.partRepairDAO = partRepairDAO;
    }

    public NntpConnectionFactory getNntpConnectionFactory() {
        return nntpConnectionFactory;
    }

    public void setNntpConnectionFactory(NntpConnectionFactory nntpConnectionFactory) {
        this.nntpConnectionFactory = nntpConnectionFactory;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
