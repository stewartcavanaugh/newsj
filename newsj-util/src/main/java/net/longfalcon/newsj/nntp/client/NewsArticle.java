package net.longfalcon.newsj.nntp.client;

import org.apache.commons.net.nntp.Article;

/**
 * User: Sten Martinez
 * Date: 10/15/15
 * Time: 2:31 PM
 */
public class NewsArticle extends Article {
    private int size = 0;
    private int lines = 0;

    public int getSize() {
        return size;
    }

    void setSize(int size) {
        this.size = size;
    }

    public int getLines() {
        return lines;
    }

    void setLines(int lines) {
        this.lines = lines;
    }
}
