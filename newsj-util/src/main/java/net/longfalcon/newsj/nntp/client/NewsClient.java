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

import org.apache.commons.net.nntp.ArticleInfo;
import org.apache.commons.net.nntp.ArticlePointer;
import org.apache.commons.net.nntp.NewGroupsOrNewsQuery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.SocketException;

/**
 * User: Sten Martinez
 * Date: 10/15/15
 * Time: 1:46 PM
 */
public interface NewsClient {


    BufferedReader retrieveArticle(String articleId, ArticleInfo pointer)
            throws IOException;

    Reader retrieveArticle(String articleId) throws IOException;

    Reader retrieveArticle() throws IOException;

    BufferedReader retrieveArticle(long articleNumber, ArticleInfo pointer)
            throws IOException;

    BufferedReader retrieveArticle(long articleNumber) throws IOException;

    BufferedReader retrieveArticleHeader(String articleId, ArticleInfo pointer)
            throws IOException;

    Reader retrieveArticleHeader(String articleId) throws IOException;

    Reader retrieveArticleHeader() throws IOException;

    BufferedReader retrieveArticleHeader(long articleNumber,
                                         ArticleInfo pointer)
            throws IOException;

    BufferedReader retrieveArticleHeader(long articleNumber) throws IOException;

    BufferedReader retrieveArticleBody(String articleId, ArticleInfo pointer)
            throws IOException;

    Reader retrieveArticleBody(String articleId) throws IOException;

    Reader retrieveArticleBody() throws IOException;

    BufferedReader retrieveArticleBody(long articleNumber,
                                       ArticleInfo pointer)
            throws IOException;

    BufferedReader retrieveArticleBody(long articleNumber) throws IOException;

    boolean selectNewsgroup(String newsgroup, NewsgroupInfo info)
            throws IOException;

    boolean selectNewsgroup(String newsgroup) throws IOException;

    String listHelp() throws IOException;

    String[] listOverviewFmt() throws IOException;

    boolean selectArticle(String articleId, ArticleInfo pointer)
            throws IOException;

    boolean selectArticle(String articleId) throws IOException;

    boolean selectArticle(ArticleInfo pointer) throws IOException;

    boolean selectArticle(long articleNumber, ArticleInfo pointer)
            throws IOException;

    boolean selectArticle(long articleNumber) throws IOException;

    boolean selectPreviousArticle(ArticleInfo pointer)
            throws IOException;

    boolean selectPreviousArticle() throws IOException;

    boolean selectNextArticle(ArticleInfo pointer) throws IOException;

    boolean selectNextArticle() throws IOException;

    NewsgroupInfo[] listNewsgroups() throws IOException;

    Iterable<String> iterateNewsgroupListing() throws IOException;

    Iterable<NewsgroupInfo> iterateNewsgroups() throws IOException;

    NewsgroupInfo[] listNewsgroups(String wildmat) throws IOException;

    Iterable<String> iterateNewsgroupListing(String wildmat) throws IOException;

    Iterable<NewsgroupInfo> iterateNewsgroups(String wildmat) throws IOException;

    NewsgroupInfo[] listNewNewsgroups(NewGroupsOrNewsQuery query)
            throws IOException;

    Iterable<String> iterateNewNewsgroupListing(NewGroupsOrNewsQuery query) throws IOException;

    Iterable<NewsgroupInfo> iterateNewNewsgroups(NewGroupsOrNewsQuery query) throws IOException;

    String[] listNewNews(NewGroupsOrNewsQuery query)
            throws IOException;

    Iterable<String> iterateNewNews(NewGroupsOrNewsQuery query) throws IOException;

    boolean completePendingCommand() throws IOException;

    Writer postArticle() throws IOException;

    Writer forwardArticle(String articleId) throws IOException;

    boolean logout() throws IOException;

    boolean authenticate(String username, String password)
            throws IOException;

    BufferedReader retrieveArticleInfo(long articleNumber) throws IOException;

    BufferedReader retrieveArticleInfo(long lowArticleNumber,
                                       long highArticleNumber)
            throws IOException;

    Iterable<NewsArticle> iterateArticleInfo(long lowArticleNumber, long highArticleNumber)
                    throws IOException;

    BufferedReader retrieveHeader(String header, long articleNumber)
                            throws IOException;

    BufferedReader retrieveHeader(String header, long lowArticleNumber,
                                  long highArticleNumber)
                                    throws IOException;

    @Deprecated
    Reader retrieveHeader(String s, int l, int h)
                                            throws IOException;

    @Deprecated
    Reader retrieveArticleInfo(int a, int b) throws IOException;

    @Deprecated
    Reader retrieveHeader(String a, int b) throws IOException;

    @Deprecated
    boolean selectArticle(int a, ArticlePointer ap) throws IOException;

    @Deprecated
    Reader retrieveArticleInfo(int a) throws IOException;

    @Deprecated
    boolean selectArticle(int a) throws IOException;

    @Deprecated
    Reader retrieveArticleHeader(int a) throws IOException;

    @Deprecated
    Reader retrieveArticleHeader(int a, ArticlePointer ap) throws IOException;

    @Deprecated
    Reader retrieveArticleBody(int a) throws IOException;

    @Deprecated
    Reader retrieveArticle(int a, ArticlePointer ap) throws IOException;

    @Deprecated
    Reader retrieveArticle(int a) throws IOException;

    @Deprecated
    Reader retrieveArticleBody(int a, ArticlePointer ap) throws IOException;

    @Deprecated
    Reader retrieveArticle(String a, ArticlePointer ap) throws IOException;

    @Deprecated
    Reader retrieveArticleBody(String a, ArticlePointer ap) throws IOException;

    @Deprecated
    Reader retrieveArticleHeader(String a, ArticlePointer ap) throws IOException;

    @Deprecated
    boolean selectArticle(String a, ArticlePointer ap) throws IOException;

    @Deprecated
    boolean selectArticle(ArticlePointer ap) throws IOException;

    @Deprecated
    boolean selectNextArticle(ArticlePointer ap) throws IOException;

    @Deprecated
    boolean selectPreviousArticle(ArticlePointer ap) throws IOException;

    void connect(String nntpServer, int port) throws SocketException, IOException;

    void setKeepAlive(boolean b) throws SocketException, IOException;

    boolean isConnected();

    boolean isAvailable();

    void disconnect() throws IOException;

    int getReplyCode();
}
