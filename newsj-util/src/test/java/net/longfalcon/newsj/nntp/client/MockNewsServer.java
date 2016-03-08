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

import net.longfalcon.newsj.mock.news.jaxb.GroupType;
import net.longfalcon.newsj.mock.news.jaxb.MessageType;
import net.longfalcon.newsj.mock.news.jaxb.NewsType;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 3/7/16
 * Time: 3:15 PM
 */
public class MockNewsServer {
    private Map<String,Map<Long,MessageType>> groupsData = new HashMap<>();
    private Resource[] dataFileLocations;
    private String currentNewsGroupName;

    public long getArticleCount(String newsGroupName) {
        Map<Long,MessageType> group = groupsData.get(newsGroupName);
        return group.size();
    }

    public long getFirstArticle(String newsGroupName) {
        Map<Long,MessageType> group = groupsData.get(newsGroupName);
        Object[] keyArray = group.keySet().toArray();
        return (Long) keyArray[0];
    }

    public long getLastArticle(String newsGroupName) {
        Map<Long,MessageType> group = groupsData.get(newsGroupName);
        Object[] keyArray = group.keySet().toArray();
        return (Long) keyArray[keyArray.length-1];
    }

    public boolean hasNewsgroup(String newsGroupName) {
        return groupsData.containsKey(newsGroupName);
    }

    public void init() throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(NewsType.class, GroupType.class, MessageType.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        for (Resource dataFileLocation : dataFileLocations) {

            NewsType newsType = (NewsType) unmarshaller.unmarshal(dataFileLocation.getFile());
            for (GroupType groupType : newsType.getGroup()) {
                String groupName = groupType.getName();
                Map<Long,MessageType> groupData;
                if (groupsData.containsKey(groupName)) {
                    groupData = groupsData.get(groupName);

                } else {
                    groupData = new HashMap<>();
                }
                for (MessageType messageType : groupType.getMessage()) {
                    if (ValidatorUtil.isNull(messageType.getId())) {
                        messageType.setId(String.format("%s@test.local", System.currentTimeMillis()));
                    }

                    if (ValidatorUtil.isNull(messageType.getFrom())) {
                        messageType.setFrom("testuser123@test.com");
                    }
                    groupData.put(messageType.getNumber(), messageType);
                }
                groupsData.put(groupName, groupData);
            }
        }

    }

    public Resource[] getDataFileLocations() {
        return dataFileLocations;
    }

    public Iterable<NewsArticle> iterateArticleInfo(long lowArticleNumber, long highArticleNumber) throws IOException {
        if (ValidatorUtil.isNull(currentNewsGroupName)) {
            throw new IOException("no newsgroup selected");
        }
        Map<Long,MessageType> groupData = groupsData.get(currentNewsGroupName);
        ArrayList<NewsArticle> returnIterable = new ArrayList<>();
        for (long i = lowArticleNumber; i <= highArticleNumber; i++) {
            if (groupData.containsKey(i)) {
                MessageType messageType = groupData.get(i);
                NewsArticle newsArticle = new NewsArticle();
                newsArticle.setArticleNumber(messageType.getNumber());
                newsArticle.setArticleId(messageType.getId());
                newsArticle.setDate(messageType.getDate());
                newsArticle.setFrom(messageType.getFrom());
                newsArticle.setSubject(messageType.getSubject());
                returnIterable.add(newsArticle);
            }
        }
        return returnIterable;
    }

    public void selectGroup(String newsgroup) {
        currentNewsGroupName = newsgroup;
    }

    public void setDataFileLocations(Resource[] dataFileLocations) {
        this.dataFileLocations = dataFileLocations;
    }
}
