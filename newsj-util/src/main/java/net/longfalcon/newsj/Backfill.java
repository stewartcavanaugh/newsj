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

import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.nntp.NntpConnectionFactory;
import net.longfalcon.newsj.nntp.client.NewsArticle;
import net.longfalcon.newsj.nntp.client.NewsClient;
import net.longfalcon.newsj.nntp.client.NewsgroupInfo;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.util.DateUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * User: Sten Martinez
 * Date: 10/6/15
 * Time: 11:44 AM
 */
@Service
public class Backfill {
    private static PeriodFormatter _periodFormatter = PeriodFormat.getDefault();
    private static DateTimeFormatter _dateTimeFormatter = DateTimeFormat.mediumDateTime();
    private static final Log _log = LogFactory.getLog(Backfill.class);

    private GroupDAO groupDAO;
    private NntpConnectionFactory nntpConnectionFactory;
    private FetchBinaries fetchBinaries;
    private PlatformTransactionManager transactionManager;

    @Transactional
    public void backfillAllGroups() {
        backfillSelectedGroup("");
    }

    @Transactional
    public void backfillSelectedGroup(String groupName) {
        List<Group> groupList = new ArrayList<>();
        if (ValidatorUtil.isNotNull(groupName)) {
            Group group = groupDAO.getGroupByName(groupName);
            if (group != null) {
                groupList.add(group);
            }
        } else {
            groupList = groupDAO.getActiveGroups();
        }

        if (!groupList.isEmpty()) {
            NewsClient nntpClient = nntpConnectionFactory.getNntpClient();

            for (Group group : groupList) {
                backfillGroup(nntpClient, group);
            }

            try {
                nntpClient.logout();
                nntpClient.disconnect();
            } catch (IOException e) {
                _log.error(e.toString());
            }
        }
    }

    private void backfillGroup(NewsClient nntpClient, Group group) {
        System.out.println("Processing  " + group.getName());
        try {
            long startLoop = System.currentTimeMillis();
            NewsgroupInfo newsgroupInfo = new NewsgroupInfo();
            boolean exists = nntpClient.selectNewsgroup(group.getName(), newsgroupInfo);
            if (!exists) {
                System.out.println("Could not select group (bad name?): " + group.getName());
                return;
            }

            int backfillTarget = group.getBackfillTarget();
            long targetPost = dayToPost(nntpClient, group, backfillTarget, true);
            long localFirstRecord = group.getFirstRecord();
            long localLastRecord = group.getLastRecord();
            if (localFirstRecord == 0 || localLastRecord == 0) {
                _log.warn("Group " + group.getName() + " has invalid numbers.  Have you run update on it?  Have you set the backfill days amount?");
                return;
            }
            Period daysServerHasPeriod = new Period( postDate(nntpClient, newsgroupInfo.getFirstArticleLong(), false), postDate(nntpClient, newsgroupInfo.getLastArticleLong(), false));
            Period localDaysPeriod = new Period( postDate(nntpClient, localFirstRecord, false), new DateTime());
            _log.info(String.format("Group %s: server has %s - %s, or %s.\nLocal first = %s (%s). Backfill target of %s days is post %s",
                    newsgroupInfo.getNewsgroup(), newsgroupInfo.getFirstArticleLong(), newsgroupInfo.getLastArticleLong(), _periodFormatter.print(daysServerHasPeriod), localFirstRecord, _periodFormatter.print(localDaysPeriod), backfillTarget, targetPost));

            if (targetPost >= localFirstRecord) { //if our estimate comes back with stuff we already have, finish
                _log.info("Nothing to do, we already have the target post.");
                return;
            }
            //get first and last part numbers from newsgroup
            if (targetPost < newsgroupInfo.getFirstArticleLong()) {
                _log.warn("WARNING: Backfill came back as before server's first.  Setting targetpost to server first.");
                targetPost = newsgroupInfo.getFirstArticleLong();
            }
            //calculate total number of parts
            long total = localFirstRecord - targetPost;
            boolean done = false;
            //set first and last, moving the window by maxxMssgs
            long last = localFirstRecord - 1L;
            long first = last - FetchBinaries.MESSAGE_BUFFER + 1L; //set initial "chunk"
            if (targetPost > first) {
                first = targetPost;
            }
            while (!done) {
                TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
                _log.info(String.format("Getting %s parts (%s in queue)", last - first + 1, first - targetPost));
                fetchBinaries.scan(nntpClient, group, first, last, "backfill", false); // TODO add support for compressed headers

                group.setFirstRecord(first);
                group.setLastUpdated(new Date());
                groupDAO.update(group);
                if (first == targetPost) {
                    done = true;
                } else {
                    //Keep going: set new last, new first, check for last chunk.
                    last = first -1;
                    first = last - FetchBinaries.MESSAGE_BUFFER + 1;
                    if (targetPost > first) {
                        first = targetPost;
                    }
                }
                transactionManager.commit(transaction);
            }
            DateTime firstRecordPostDate = postDate(nntpClient, first, false);
            group.setFirstRecordPostdate(firstRecordPostDate.toDate());
            group.setLastUpdated(new Date());
            groupDAO.update(group);

            Period groupTime = new Period(startLoop, System.currentTimeMillis());
            _log.info("Group processed in " + _periodFormatter.print(groupTime));
        } catch (Exception e) {
            _log.error(e, e);
        }
    }

    public long dayToPost(NewsClient nntpClient, Group group, int days, boolean debug) throws IOException {
        if (debug) {
            _log.info(String.format("dayToPost finding post for %s %s days back.", group.getName(), days));
        }
        NewsgroupInfo newsgroupInfo = new NewsgroupInfo();
        boolean exists = nntpClient.selectNewsgroup(group.getName(), newsgroupInfo);
        if (!exists) {
            System.out.println("Could not select group (bad name?): " + group.getName());
            _log.error("Returning from dayToPost");
            return 0;
        }
        DateTime goalDate = new DateTime();
        goalDate = goalDate.minusDays(days);
        long firstArticle = newsgroupInfo.getFirstArticleLong();
        long lastArticle = newsgroupInfo.getLastArticleLong();
        long totalArticles = lastArticle - firstArticle;
        long upperBound = lastArticle;
        long lowerBound = firstArticle;

        if (debug) {
            _log.info(String.format("Total Articles: %d\n Upper: %d\n Lower: %d\n Goal: %s", totalArticles, upperBound, lowerBound, goalDate.toString(_dateTimeFormatter)));
        }

        DateTime firstDate = postDate(nntpClient, firstArticle, true);
        int offset = 1;
        while (firstDate == null ) {
            firstArticle = firstArticle + (offset * 4);
            _log.info("Unable to locate canonical first post, trying " + firstArticle);
            firstDate = postDate(nntpClient, firstArticle, true);
            offset++;
            if (offset == 3) {
                newsgroupInfo = new NewsgroupInfo();
                exists = nntpClient.selectNewsgroup(group.getName(), newsgroupInfo);
                if (!exists) {
                    System.out.println("Could not select group (bad name?): " + group.getName());
                    _log.error("Returning from dayToPost");
                    return 0;
                }
                firstArticle = newsgroupInfo.getFirstArticleLong();
            }
        }
        DateTime lastDate = postDate(nntpClient, lastArticle, true);

        if (goalDate.isBefore(firstDate)) {
            _log.warn("WARNING: Backfill target of "+ days + " day(s) is older than the first article stored on your news server.");
            _log.warn("Starting from the first available article (" + _dateTimeFormatter.print(firstArticle) + " or " + daysOld(firstDate) + " days).");
            return firstArticle;
        } else if (goalDate.isAfter(lastDate)) {
            _log.warn("ERROR: Backfill target of "+ days + " day(s) is newer than the last article stored on your news server.");
            _log.warn("To backfill this group you need to set Backfill Days to at least " + daysOld(lastDate)+1 + " days (" + _dateTimeFormatter.print(lastDate.minusDays(1)) + ")");
            return 0;
        }
        if (debug) {
            _log.info(String.format("DEBUG: Searching for postdate \nGoaldate: %s\nFirstdate: %s\nLastdate: %s", _dateTimeFormatter.print(goalDate), _dateTimeFormatter.print(firstDate), _dateTimeFormatter.print(lastDate)));
        }

        long interval = Math.round( Math.floor((upperBound - lowerBound) * 0.5d) );
        DateTime dateOfNextOne;

        if (debug) {
            _log.info(String.format("Start: %s\nEnd: %s\nInterval: %s", firstArticle, lastArticle, interval));
        }

        dateOfNextOne = lastDate;

        while (daysOld(dateOfNextOne) < days) {
            DateTime tempDate = postDate(nntpClient, (upperBound-interval), true);
            while (tempDate.isAfter(goalDate)) {
                upperBound = upperBound - interval;
                if (debug) {
                    _log.info(String.format("New upperbound (%s) is %s days old.", upperBound, daysOld(tempDate)));
                }

                tempDate = postDate(nntpClient, (upperBound-interval), true);
            }

            interval = Math.round(Math.ceil(interval/2d));
            if(debug) {
                _log.info("Set interval to " + interval + " articles.");
            }

            dateOfNextOne = postDate(nntpClient, upperBound-1, true);
            while (dateOfNextOne == null) {
                dateOfNextOne = postDate(nntpClient, upperBound--, true);
            }
        }

        _log.info("Determined to be article " + upperBound + " which is " + daysOld(dateOfNextOne) + " days old (" + _dateTimeFormatter.print(dateOfNextOne) + ")");
        return upperBound;
    }

    /**
     * Returns single timestamp from a local article number
     * @param nntpClient
     * @param articleNumber
     * @param debug
     * @return
     */
    public DateTime postDate(NewsClient nntpClient, long articleNumber, boolean debug) throws IOException {
        int attempts = 0;
        boolean success = false;
        String dateString = "";
        do {
            try {
                Iterable<NewsArticle> articleIterable = nntpClient.iterateArticleInfo(articleNumber, articleNumber);
                NewsArticle article = articleIterable.iterator().next();
                if (article.getDate() != null) {
                    dateString = article.getDate();
                    success = true;
                } else {
                    success = false;
                }
            } catch (NoSuchElementException e) {
                if(debug) {
                    _log.debug(e.toString());
                }
                success = false;
            } catch (Exception e) {
                _log.error(e.toString(), e);
                success = false;
            }
            if (debug && attempts > 0) {
                _log.info(String.format("retried %d time(s)", attempts));
            }
            attempts++;

        } while (attempts <= 3 && !success);

        if (!success) {
            return null;
        }
        DateTime postDate = DateUtil.parseNNTPDate(dateString);
        return postDate;
    }

    private int daysOld(DateTime then) {
        DateTime now = DateTime.now();
        if (then.isAfter(now)) {
            return 0;
        } else {
            Period period = new Period(then, now);
            return period.getDays();
        }
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public NntpConnectionFactory getNntpConnectionFactory() {
        return nntpConnectionFactory;
    }

    public void setNntpConnectionFactory(NntpConnectionFactory nntpConnectionFactory) {
        this.nntpConnectionFactory = nntpConnectionFactory;
    }

    public FetchBinaries getFetchBinaries() {
        return fetchBinaries;
    }

    public void setFetchBinaries(FetchBinaries fetchBinaries) {
        this.fetchBinaries = fetchBinaries;
    }

    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
