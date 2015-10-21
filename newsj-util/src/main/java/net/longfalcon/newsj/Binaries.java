package net.longfalcon.newsj;

import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.model.Message;
import net.longfalcon.newsj.model.MessagePart;
import net.longfalcon.newsj.model.Part;
import net.longfalcon.newsj.model.PartRepair;
import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.nntp.NntpConnectionFactory;
import net.longfalcon.newsj.nntp.client.NewsArticle;
import net.longfalcon.newsj.nntp.client.NewsClient;
import net.longfalcon.newsj.nntp.client.NewsgroupInfo;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.persistence.PartDAO;
import net.longfalcon.newsj.persistence.PartRepairDAO;
import net.longfalcon.newsj.persistence.SiteDAO;
import net.longfalcon.newsj.util.ArrayUtil;
import net.longfalcon.newsj.util.Defaults;
import net.longfalcon.newsj.util.EncodingUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.nntp.Article;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 10/5/15
 * Time: 5:07 PM
 */
@Service
public class Binaries {

    private static PeriodFormatter _periodFormatter = PeriodFormat.wordBased();
    private static final Log _log = LogFactory.getLog(Binaries.class);

    private Backfill backfill;
    private Blacklist blacklist;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private SiteDAO siteDAO;

    private BinaryDAO binaryDAO;

    private PartDAO partDAO;

    private PartRepairDAO partRepairDAO;

    private PlatformTransactionManager transactionManager;

    @Autowired
    private NntpConnectionFactory nntpConnectionFactory;

    private boolean newGroupScanByDays = false;
    private boolean compressedHeaders;
    private int messageBuffer = 20000;
    private int newGroupMsgsToScan = 50000;
    private int newGroupDaysToScan = 3;

    public void updateAllGroups() {
        // get all groups
        Collection<Group> groups = groupDAO.getActiveGroups();

        // get site config TODO: use config service
        Site site = siteDAO.getDefaultSite();

        newGroupScanByDays = site.getNewGroupsScanMethod() == 1; // TODO: create constants/enum
        compressedHeaders = site.isCompressedHeaders();
        messageBuffer = site.getMaxMessages();
        newGroupDaysToScan = site.getNewGroupDaysToScan();
        newGroupMsgsToScan = site.getNewGroupMsgsToScan();

        if (!groups.isEmpty()) {
            long startTime = System.currentTimeMillis();

            System.out.printf("Updating: %s groups - Using compression? %s%n", groups.size(), compressedHeaders ? "Yes" : "No");

            //get nntp client
            NewsClient nntpClient = nntpConnectionFactory.getNntpClient();
            if (nntpClient == null) {
                throw new RuntimeException("Connection error. Please check NNTP settings and try again.");
            }

            for (Group group : groups) {
                updateGroup(nntpClient, group);
                if (nntpClient == null) {
                    nntpClient = nntpConnectionFactory.getNntpClient();
                } else if (!nntpClient.isConnected() || !nntpClient.isAvailable()) {
                    try {
                        nntpClient.disconnect();
                    } catch (IOException e) {
                        _log.error(e.toString(), e);
                    }
                    nntpClient = nntpConnectionFactory.getNntpClient();
                }
            }
            try {
                nntpClient.logout();
                nntpClient.disconnect();
            } catch (IOException e) {
                _log.error(e.toString(), e);
            }
            long endTime = System.currentTimeMillis();

            String duration = _periodFormatter.print(new Period(endTime - startTime));
            System.out.println("Updating completed in " + duration);
        } else {
            System.out.println("No groups specified. Ensure groups are added to newznab's database for updating.");
        }
    }

    public void updateGroup(NewsClient nntpClient, Group group) {
        /*
        * */
        System.out.println("Processing  " + group.getName());
        try {
            NewsgroupInfo newsgroupInfo = new NewsgroupInfo();
            boolean exists = nntpClient.selectNewsgroup(group.getName(), newsgroupInfo);
            if (!exists) {
                System.out.println("Could not select group (bad name?): " + group.getName());
                return;
            }

            //Attempt to repair any missing parts before grabbing new ones
            partRepair(nntpClient, group);

            //Get first and last part numbers from newsgroup
            long firstArticle = newsgroupInfo.getFirstArticleLong();
            long lastArticle = newsgroupInfo.getLastArticleLong();
            long groupLastArticle = lastArticle; // this is to hold the true last article in the group

            // For new newsgroups - determine here how far you want to go back.
            if (group.getLastRecord() == 0) {
                if (newGroupScanByDays) {
                    firstArticle = backfill.dayToPost(nntpClient, group, newGroupDaysToScan, true);
                    if ( firstArticle == 0) {
                        _log.warn("Skipping group: " + group.getName());
                        return;
                    }
                } else {
                    if (firstArticle > (lastArticle - newGroupMsgsToScan)) {
                        firstArticle = firstArticle;
                    } else {
                        firstArticle = lastArticle - newGroupMsgsToScan;
                    }
                }
                DateTime firstRecordPostDate = backfill.postDate(nntpClient, firstArticle, false);
                // update group record
                group.setFirstRecord(firstArticle);
                group.setFirstRecordPostdate(firstRecordPostDate.toDate());
                updateGroupModel(group);
            } else {
                firstArticle = group.getLastRecord() + 1;
            }

            // Generate postdates for first and last records, for those that upgraded
            if ( (group.getFirstRecordPostdate() == null || group.getLastRecordPostdate() == null) && (group.getLastRecord() != 0 && group.getFirstRecord() != 0)) {
                DateTime groupFirstPostDate = backfill.postDate(nntpClient, group.getFirstRecord(), false);
                group.setFirstRecordPostdate(groupFirstPostDate.toDate());
                DateTime groupLastPostDate = backfill.postDate(nntpClient, group.getLastRecord(), false);
                group.setLastRecordPostdate(groupLastPostDate.toDate());
                updateGroupModel(group);
            }

            // Deactivate empty groups
            if ((lastArticle - firstArticle) <= 5) {
                group.setActive(false);
                group.setLastUpdated(new Date());
                updateGroupModel(group);
            }

            // Calculate total number of parts
            long totalParts = groupLastArticle - firstArticle + 1;

            // If total is bigger than 0 it means we have new parts in the newsgroup
            if(totalParts > 0) {
                _log.info(String.format("Group %s has %d new parts.", group.getName(), totalParts));
                _log.info(String.format("First: %d Last: %d Local last: %d", firstArticle, lastArticle, group.getLastRecord()));
                if (group.getLastRecord() == 0) {
                    _log.info("New group starting with " + (newGroupScanByDays ? newGroupDaysToScan + " days" : newGroupMsgsToScan + " messages") + " worth.");
                }

                boolean done = false;

                long startLoopTime = System.currentTimeMillis();
                while (!done) {

                    if (totalParts > messageBuffer) {
                        if (firstArticle + messageBuffer > groupLastArticle) {
                            lastArticle = groupLastArticle;
                        } else {
                            lastArticle = firstArticle + messageBuffer;
                        }
                    }

                    _log.info(String.format("Getting %d parts (%d to %d) - %d in queue", lastArticle - firstArticle + 1, firstArticle, lastArticle, groupLastArticle - lastArticle));

                    //get headers from newsgroup
                    long lastId = 0;
                    try {
                        lastId = scan(nntpClient, group, firstArticle, lastArticle, "update"); // magic string
                    } catch (Exception e) {
                        _log.error(e.toString(), e);
                    }
                    if (lastId == 0) {
                        // scan failed - skip group
                        return;
                    }

                    group.setLastRecord(lastArticle);
                    group.setLastUpdated(new Date());
                    updateGroupModel(group);

                    if (lastArticle == groupLastArticle) {
                        done = true;
                    } else {
                        lastArticle = lastId;
                        firstArticle = lastArticle + 1;
                    }

                }

                DateTime lastRecordPostDate = backfill.postDate(nntpClient, lastArticle, false);
                group.setLastRecordPostdate(lastRecordPostDate.toDate());
                group.setLastUpdated(new Date());
                updateGroupModel(group);
                Period loopTime = new Period(startLoopTime, System.currentTimeMillis());
                _log.info(String.format("Group processed in %s seconds", _periodFormatter.print(loopTime)));
            }
        } catch (IOException e) {
            _log.error(e.toString(), e);
        }

    }

    // This method should be an atomic transaction
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public long scan(NewsClient nntpClient, Group group, long firstArticle, long lastArticle, String type) throws IOException {
        // this is a hack - tx is not working ATM
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));

        long startHeadersTime = System.currentTimeMillis();

        long maxNum = 0;
        Map<String, Message> messages = new LinkedHashMap<>(messageBuffer + 1);

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

        //TODO: ACK. this part needs to move to HashSets once the first pass is done.
        long[] rangeRequested = ArrayUtil.range(firstArticle, lastArticle);
        long[] messagesReceived = new long[]{};
        long[] messagesBlacklisted = new long[]{};
        long[] messagesIgnored = new long[]{};
        long[] messagesInserted = new long[]{};
        long[] messagesNotInserted = new long[]{};

        // check error codes?

        long startUpdateTime = System.currentTimeMillis();

        if (articlesIterable != null) {
            for( NewsArticle article : articlesIterable) {
                long articleNumber = article.getArticleNumberLong();

                if (articleNumber == 0) {
                    continue;
                }

                messagesReceived = ArrayUtil.append(messagesReceived, articleNumber);

                Pattern pattern = Defaults.PARTS_SUBJECT_REGEX;
                String subject = article.getSubject();
                Matcher matcher = pattern.matcher(subject);
                if (ValidatorUtil.isNull(subject) || !matcher.find()) {
                    // not a binary post most likely.. continue
                    messagesIgnored = ArrayUtil.append(messagesIgnored, articleNumber);
                    if (_log.isDebugEnabled()) {
                        _log.debug(String.format("Skipping message no# %s : %s", articleNumber, subject));
                    }
                    continue;
                }

                //Filter binaries based on black/white list
                if (isBlacklisted(article, group)) {
                    messagesBlacklisted = ArrayUtil.append(messagesBlacklisted, articleNumber);
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

            // this sucks.
            Set<Long> messagesReceivedSet = new HashSet<Long>(ArrayUtil.asList(ArrayUtils.toObject(messagesReceived)));

            Set<Long> rangeNotRecieved = new HashSet<Long>(ArrayUtil.asList(ArrayUtils.toObject(rangeRequested)));
            rangeNotRecieved.removeAll(messagesReceivedSet);

            if (!type.equals("partrepair")) {
                _log.info(String.format("Received %d articles of %d requested, %d blacklisted, %d not binaries", messagesReceived.length, lastArticle - firstArticle + 1, messagesBlacklisted.length, messagesIgnored.length));
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
                        String binaryHash = EncodingUtil.md5Hash(subject+message.getFrom()+String.valueOf(group.getId()));
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
                                messagesInserted = ArrayUtil.append(messagesInserted, messagePart.getArticleNumber());
                            } catch (Exception e) {
                                _log.error(e.toString());
                                messagesNotInserted = ArrayUtil.append(messagesNotInserted, messagePart.getArticleNumber());
                            }

                        }
                    }
                }
                //TODO: determine whether to add to missing articles if insert failed
                if (messagesNotInserted.length > 0) {
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void partRepair(NewsClient nntpClient, Group group) throws IOException {
        List<PartRepair> partRepairList = partRepairDAO.findByGroupIdAndAttempts(group.getId(), 5, true);
        long partsRepaired = 0;
        long partsFailed = 0;

        int partRepairListSize = partRepairList.size();
        if (partRepairListSize > 0) {
            _log.info("Attempting to repair " + partRepairListSize + " parts...");

            //loop through each part to group into ranges
            Map<Long,Long> ranges = new LinkedHashMap<>();
            long lastNum = 0;
            long lastPart = 0;
            for(PartRepair partRepair : partRepairList) {
                long partRepairNumberId = partRepair.getNumberId();
                if ((lastNum+1) == partRepairNumberId) {
                    ranges.put(lastPart, partRepairNumberId);
                } else {
                    lastPart = partRepairNumberId;
                    ranges.put(lastPart, partRepairNumberId);
                }
                lastNum = partRepairNumberId;
            }

            //download missing parts in ranges
            long startLoopTime = System.currentTimeMillis();
            for(Map.Entry<Long,Long> entry : ranges.entrySet()) {

                long partFrom = entry.getKey();
                long partTo = entry.getValue();

                _log.info("repairing " + partFrom + " to " + partTo );

                //get article from newsgroup
                scan(nntpClient, group, partFrom, partTo, "partrepair");

                //check if the articles were added
                List<Long> articlesRange = ArrayUtil.rangeList(partFrom, partTo);
                // This might fail. may have to do two queries, one for the partrepairs and the other from parts
                List<PartRepair> partRepairs = partRepairDAO.findByGroupIdAndNumbers(group.getId(), articlesRange);
                for (PartRepair partRepair : partRepairs) {
                    if (partRepair.getPart() != null && partRepair.getNumberId() == partRepair.getPart().getNumber()) {
                        partsRepaired++;

                        //article was added, delete from partrepair
                        // May need to be stored for later to prevent modification
                        partRepairDAO.deletePartRepair(partRepair);
                    } else {
                        partsFailed++;

                        //article was not added, increment attempts
                        int attempts = partRepair.getAttempts();
                        partRepair.setAttempts(attempts+1);
                        partRepairDAO.updatePartRepair(partRepair);
                    }
                }
            }
            Period repairLoopTime = new Period(startLoopTime, System.currentTimeMillis());
            _log.info(partsRepaired + " parts repaired.");
            _log.info("repair took " + _periodFormatter.print(repairLoopTime));
        }

        //remove articles that we cant fetch after 5 attempts
        //change to HQL Delete?
        List<PartRepair> partRepairsToDelete = partRepairDAO.findByGroupIdAndAttempts(group.getId(), 5, false);
        for (PartRepair partRepair : partRepairsToDelete) {
            partRepairDAO.deletePartRepair(partRepair);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.NOT_SUPPORTED)
    public void updateGroupModel(Group group) {
        groupDAO.update(group);
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public SiteDAO getSiteDAO() {
        return siteDAO;
    }

    public void setSiteDAO(SiteDAO siteDAO) {
        this.siteDAO = siteDAO;
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

    public Backfill getBackfill() {
        return backfill;
    }

    public void setBackfill(Backfill backfill) {
        this.backfill = backfill;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Blacklist getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Blacklist blacklist) {
        this.blacklist = blacklist;
    }
}
