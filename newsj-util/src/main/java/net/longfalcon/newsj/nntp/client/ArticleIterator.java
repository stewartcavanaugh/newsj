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
