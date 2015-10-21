package net.longfalcon.newsj.nntp.client;

import org.apache.commons.net.nntp.Article;

import java.util.Iterator;

/**
 * copied from commons-net class ArticleIterator v3.2
 * Class which wraps an {@code Iterable<String>} of raw article information
 * to generate an {@code Iterable<Article>} of the parsed information.
 * @since 3.0
 */
class ArticleIterator implements Iterator<NewsArticle>, Iterable<NewsArticle> {

    private  final Iterator<String> stringIterator;

    public ArticleIterator(Iterable<String> iterableString) {
        stringIterator = iterableString.iterator();
    }

    public boolean hasNext() {
        return stringIterator.hasNext();
    }

    /**
     * Get the next Article
     * @return the next {@link Article}, never {@code null}, if unparseable then isDummy()
     * will be true, and the subject will contain the raw info.
     */
    public NewsArticle next() {
        String line = stringIterator.next();
        return CustomNNTPClient.__parseArticleEntry(line);
    }

    public void remove() {
        stringIterator.remove();
    }
    public Iterator<NewsArticle> iterator() {
        return this;
    }
}
