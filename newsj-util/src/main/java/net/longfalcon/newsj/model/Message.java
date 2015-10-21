package net.longfalcon.newsj.model;

import net.longfalcon.newsj.nntp.client.NewsArticle;
import net.longfalcon.newsj.util.ArrayUtil;
import net.longfalcon.newsj.util.DateUtil;
import net.longfalcon.newsj.util.Defaults;
import org.joda.time.DateTime;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Nonpersistent
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 5:56 PM
 */
public class Message {

    private String subject;
    private DateTime date;
    private String from;
    private String xRef;
    private int maxParts;
    private Map<Integer,MessagePart> partsMap;

    public Message(NewsArticle article, int currentPart, int maxParts) {
        this.partsMap = new LinkedHashMap<Integer, MessagePart>(maxParts);

        Matcher matcher = Defaults.PARTS_SUBJECT_REGEX.matcher(article.getSubject());
        this.subject = (matcher.replaceAll("")).trim();
        this.date = DateUtil.parseNNTPDate(article.getDate());
        long articleNumber = article.getArticleNumberLong();
        this.from = article.getFrom();
        this.maxParts = maxParts;
        this.xRef = ArrayUtil.stringify(article.getReferences(), " ");
        String articleId = article.getArticleId();
        String messageId = articleId.substring(1, articleId.length()-1);
        int size = article.getSize();
        if (currentPart > 0) {
            addPart(currentPart, messageId, articleNumber, size);
        }
    }

    public void addPart(int partNo, String messageId, long articleNumber, int size) {
        MessagePart messagePart = new MessagePart(partNo, articleNumber, messageId, size);
        partsMap.put(partNo, messagePart);
    }

    public String getSubject() {
        return subject;
    }

    public DateTime getDate() {
        return date;
    }

    public int getMaxParts() {
        return maxParts;
    }

    public Map<Integer, MessagePart> getPartsMap() {
        return partsMap;
    }

    public String getFrom() {
        return from;
    }

    public String getxRef() {
        return xRef;
    }
}
