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

package net.longfalcon.newsj.model;

import javax.persistence.Transient;
import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 10/13/15
 * Time: 5:39 PM
 */
public class Release {
    private long id;
    private String name;
    private String searchName;
    private Integer totalpart;
    private long groupId;
    private long size;
    private Date postDate;
    private Date addDate;
    private String guid;
    private String fromName;
    private float completion;
    private Category category;
    private Long regexId;
    private Long rageId;
    private String seriesFull;
    private String season;
    private String episode;
    private String tvTitle;
    private Date tvAirDate;
    private Integer imdbId;
    private Integer musicInfoId;
    private Integer consoleInfoId;
    private Integer reqId;
    private int grabs;
    private int comments;
    private int passwordStatus;
    private ReleaseNfo releaseNfo;

    private int categoryId;
    private String groupName;
    private String categoryDisplayName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Integer getTotalpart() {
        return totalpart;
    }

    public void setTotalpart(Integer totalpart) {
        this.totalpart = totalpart;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public float getCompletion() {
        return completion;
    }

    public void setCompletion(float completion) {
        this.completion = completion;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getRegexId() {
        return regexId;
    }

    public void setRegexId(Long regexId) {
        this.regexId = regexId;
    }

    public Long getRageId() {
        return rageId;
    }

    public void setRageId(Long rageId) {
        this.rageId = rageId;
    }

    public String getSeriesFull() {
        return seriesFull;
    }

    public void setSeriesFull(String seriesFull) {
        this.seriesFull = seriesFull;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public Date getTvAirDate() {
        return tvAirDate;
    }

    public void setTvAirDate(Date tvAirDate) {
        this.tvAirDate = tvAirDate;
    }

    public Integer getMusicInfoId() {
        return musicInfoId;
    }

    public void setMusicInfoId(Integer musicInfoId) {
        this.musicInfoId = musicInfoId;
    }

    public Integer getConsoleInfoId() {
        return consoleInfoId;
    }

    public void setConsoleInfoId(Integer consoleInfoId) {
        this.consoleInfoId = consoleInfoId;
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public int getGrabs() {
        return grabs;
    }

    public void setGrabs(int grabs) {
        this.grabs = grabs;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getPasswordStatus() {
        return passwordStatus;
    }

    public void setPasswordStatus(int passwordStatus) {
        this.passwordStatus = passwordStatus;
    }

    public Integer getImdbId() {
        return imdbId;
    }

    public void setImdbId(Integer imdbId) {
        this.imdbId = imdbId;
    }

    public ReleaseNfo getReleaseNfo() {
        return releaseNfo;
    }

    public void setReleaseNfo(ReleaseNfo releaseNfo) {
        this.releaseNfo = releaseNfo;
    }

    @Transient
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Transient
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Transient
    public String getCategoryDisplayName() {
        return categoryDisplayName;
    }

    public void setCategoryDisplayName(String categoryDisplayName) {
        this.categoryDisplayName = categoryDisplayName;
    }
}
