package net.longfalcon.newsj;

import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.nntp.client.NewsArticle;
import net.longfalcon.newsj.nntp.client.NewsClient;
import net.longfalcon.newsj.nntp.client.NewsgroupInfo;
import net.longfalcon.newsj.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * User: Sten Martinez
 * Date: 10/6/15
 * Time: 11:44 AM
 */
@Service
public class Backfill {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.mediumDateTime();
    private static final Log _log = LogFactory.getLog(Backfill.class);

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
            _log.info(String.format("Total Articles: %d\n Upper: %d\n Lower: %d\n Goal: %s", totalArticles, upperBound, lowerBound, goalDate.toString(dateTimeFormatter)));
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
            _log.warn("Starting from the first available article (" + dateTimeFormatter.print(firstArticle) + " or " + daysOld(firstDate) + " days).");
            return firstArticle;
        } else if (goalDate.isAfter(lastDate)) {
            _log.warn("ERROR: Backfill target of "+ days + " day(s) is newer than the last article stored on your news server.");
            _log.warn("To backfill this group you need to set Backfill Days to at least " + daysOld(lastDate)+1 + " days (" + dateTimeFormatter.print(lastDate.minusDays(1)) + ")");
            return 0;
        }
        if (debug) {
            _log.info(String.format("DEBUG: Searching for postdate \nGoaldate: %s\nFirstdate: %s\nLastdate: %s", dateTimeFormatter.print(goalDate), dateTimeFormatter.print(firstDate), dateTimeFormatter.print(lastDate)));
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

        _log.info("Determined to be article " + upperBound + " which is " + daysOld(dateOfNextOne) + " days old (" + dateTimeFormatter.print(dateOfNextOne) + ")");
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
}
