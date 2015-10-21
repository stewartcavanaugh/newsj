package net.longfalcon.newsj.util;

import org.apache.commons.net.nntp.Article;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * User: Sten Martinez
 * Date: 10/6/15
 * Time: 1:08 PM
 */
public class NntpUtil {

    /**
     * Parse a response line from {@link #retrieveArticleInfo(long, long)}.
     *
     * @param line a response line
     * @return the parsed {@link Article}, if unparseable then isDummy()
     * will be true, and the subject will contain the raw info.
     * @since 3.0
     */
    public static Article parseArticleEntry(String line) {
        // Extract the article information
        // Mandatory format (from NNTP RFC 2980) is :
        // articleNumber\tSubject\tAuthor\tDate\tID\tReference(s)\tByte Count\tLine Count

        Article article = new Article();
        article.setSubject(line); // in case parsing fails
        String parts[] = line.split("\t");
        if (parts.length > 6) {
            int i = 0;
            try {
                article.setArticleNumber(Long.parseLong(parts[i++]));
                article.setSubject(parts[i++]);
                article.setFrom(parts[i++]);
                article.setDate(parts[i++]);
                article.setArticleId(parts[i++]);
                article.addReference(parts[i++]);
            } catch (NumberFormatException e) {
                // ignored, already handled
            }
        }
        return article;
    }

}
