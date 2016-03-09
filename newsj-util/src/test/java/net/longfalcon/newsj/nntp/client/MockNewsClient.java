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
 * Date: 3/7/16
 * Time: 12:36 PM
 */
public class MockNewsClient implements NewsClient {

    private MockNewsServer newsServer;

    public MockNewsClient(MockNewsServer newsServer) {
        this.newsServer = newsServer;
    }

    @Override
    public BufferedReader retrieveArticle(String articleId, ArticleInfo pointer) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticle(String articleId) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticle() throws IOException {
        return null;
    }

    @Override
    public BufferedReader retrieveArticle(long articleNumber, ArticleInfo pointer) throws IOException {
        return null;
    }

    @Override
    public BufferedReader retrieveArticle(long articleNumber) throws IOException {
        return null;
    }

    @Override
    public BufferedReader retrieveArticleHeader(String articleId, ArticleInfo pointer) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticleHeader(String articleId) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticleHeader() throws IOException {
        return null;
    }

    @Override
    public BufferedReader retrieveArticleHeader(long articleNumber, ArticleInfo pointer) throws IOException {
        return null;
    }

    @Override
    public BufferedReader retrieveArticleHeader(long articleNumber) throws IOException {
        return null;
    }

    @Override
    public BufferedReader retrieveArticleBody(String articleId, ArticleInfo pointer) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticleBody(String articleId) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticleBody() throws IOException {
        return null;
    }

    @Override
    public BufferedReader retrieveArticleBody(long articleNumber, ArticleInfo pointer) throws IOException {
        return null;
    }

    @Override
    public BufferedReader retrieveArticleBody(long articleNumber) throws IOException {
        return null;
    }

    @Override
    public boolean selectNewsgroup(String newsgroup, NewsgroupInfo info) throws IOException {
        if (!newsServer.hasNewsgroup(newsgroup)) {
            return false;
        } else {
            info._setNewsgroup(newsgroup);
            info._setArticleCount(newsServer.getArticleCount(newsgroup));
            info._setFirstArticle(newsServer.getFirstArticle(newsgroup));
            info._setLastArticle(newsServer.getLastArticle(newsgroup));
            info._setPostingPermission(0);
            newsServer.selectGroup(newsgroup);
            return true;
        }

    }

    @Override
    public boolean selectNewsgroup(String newsgroup) throws IOException {
        return false;
    }

    @Override
    public String listHelp() throws IOException {
        return null;
    }

    @Override
    public String[] listOverviewFmt() throws IOException {
        return new String[0];
    }

    @Override
    public boolean selectArticle(String articleId, ArticleInfo pointer) throws IOException {
        return false;
    }

    @Override
    public boolean selectArticle(String articleId) throws IOException {
        return false;
    }

    @Override
    public boolean selectArticle(ArticleInfo pointer) throws IOException {
        return false;
    }

    @Override
    public boolean selectArticle(long articleNumber, ArticleInfo pointer) throws IOException {
        return false;
    }

    @Override
    public boolean selectArticle(long articleNumber) throws IOException {
        return false;
    }

    @Override
    public boolean selectPreviousArticle(ArticleInfo pointer) throws IOException {
        return false;
    }

    @Override
    public boolean selectPreviousArticle() throws IOException {
        return false;
    }

    @Override
    public boolean selectNextArticle(ArticleInfo pointer) throws IOException {
        return false;
    }

    @Override
    public boolean selectNextArticle() throws IOException {
        return false;
    }

    @Override
    public NewsgroupInfo[] listNewsgroups() throws IOException {
        return new NewsgroupInfo[0];
    }

    @Override
    public Iterable<String> iterateNewsgroupListing() throws IOException {
        return null;
    }

    @Override
    public Iterable<NewsgroupInfo> iterateNewsgroups() throws IOException {
        return null;
    }

    @Override
    public NewsgroupInfo[] listNewsgroups(String wildmat) throws IOException {
        return new NewsgroupInfo[0];
    }

    @Override
    public Iterable<String> iterateNewsgroupListing(String wildmat) throws IOException {
        return null;
    }

    @Override
    public Iterable<NewsgroupInfo> iterateNewsgroups(String wildmat) throws IOException {
        return null;
    }

    @Override
    public NewsgroupInfo[] listNewNewsgroups(NewGroupsOrNewsQuery query) throws IOException {
        return new NewsgroupInfo[0];
    }

    @Override
    public Iterable<String> iterateNewNewsgroupListing(NewGroupsOrNewsQuery query) throws IOException {
        return null;
    }

    @Override
    public Iterable<NewsgroupInfo> iterateNewNewsgroups(NewGroupsOrNewsQuery query) throws IOException {
        return null;
    }

    @Override
    public String[] listNewNews(NewGroupsOrNewsQuery query) throws IOException {
        return new String[0];
    }

    @Override
    public Iterable<String> iterateNewNews(NewGroupsOrNewsQuery query) throws IOException {
        return null;
    }

    @Override
    public boolean completePendingCommand() throws IOException {
        return false;
    }

    @Override
    public Writer postArticle() throws IOException {
        return null;
    }

    @Override
    public Writer forwardArticle(String articleId) throws IOException {
        return null;
    }

    @Override
    public boolean logout() throws IOException {
        return false;
    }

    @Override
    public boolean authenticate(String username, String password) throws IOException {
        return false;
    }

    @Override
    public BufferedReader retrieveArticleInfo(long articleNumber) throws IOException {
        return null;
    }

    @Override
    public BufferedReader retrieveArticleInfo(long lowArticleNumber, long highArticleNumber) throws IOException {
        return null;
    }

    @Override
    public Iterable<NewsArticle> iterateArticleInfo(long lowArticleNumber, long highArticleNumber) throws IOException {
        return newsServer.iterateArticleInfo(lowArticleNumber, highArticleNumber);
    }

    @Override
    public BufferedReader retrieveHeader(String header, long articleNumber) throws IOException {
        return null;
    }

    @Override
    public BufferedReader retrieveHeader(String header, long lowArticleNumber, long highArticleNumber) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveHeader(String s, int l, int h) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticleInfo(int a, int b) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveHeader(String a, int b) throws IOException {
        return null;
    }

    @Override
    public boolean selectArticle(int a, ArticlePointer ap) throws IOException {
        return false;
    }

    @Override
    public Reader retrieveArticleInfo(int a) throws IOException {
        return null;
    }

    @Override
    public boolean selectArticle(int a) throws IOException {
        return false;
    }

    @Override
    public Reader retrieveArticleHeader(int a) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticleHeader(int a, ArticlePointer ap) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticleBody(int a) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticle(int a, ArticlePointer ap) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticle(int a) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticleBody(int a, ArticlePointer ap) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticle(String a, ArticlePointer ap) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticleBody(String a, ArticlePointer ap) throws IOException {
        return null;
    }

    @Override
    public Reader retrieveArticleHeader(String a, ArticlePointer ap) throws IOException {
        return null;
    }

    @Override
    public boolean selectArticle(String a, ArticlePointer ap) throws IOException {
        return false;
    }

    @Override
    public boolean selectArticle(ArticlePointer ap) throws IOException {
        return false;
    }

    @Override
    public boolean selectNextArticle(ArticlePointer ap) throws IOException {
        return false;
    }

    @Override
    public boolean selectPreviousArticle(ArticlePointer ap) throws IOException {
        return false;
    }

    @Override
    public void connect(String nntpServer, int port) throws SocketException, IOException {

    }

    @Override
    public void setKeepAlive(boolean b) throws SocketException, IOException {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public void disconnect() throws IOException {

    }

    @Override
    public int getReplyCode() {
        return 0;
    }
}
