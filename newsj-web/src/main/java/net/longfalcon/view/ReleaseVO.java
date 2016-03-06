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

package net.longfalcon.view;

import net.longfalcon.newsj.model.Release;

/**
 * User: Sten Martinez
 * Date: 3/4/16
 * Time: 11:19 PM
 */
public class ReleaseVO {
/*    private long id;
    private String name;
    private String searchName;
    private Integer totalpart;
    private String groupName;
    private long groupId;
    private long size;
    private Date postDate;
    private Date addDate;
    private String guid;
    private String fromName;
    private float completion;
    private int categoryId;
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

    public ReleaseVO(Release release) {
        this.id = release.getId();
        this.name = release.getName();
        this.searchName = release.getSearchName();
        this.totalpart = release.getTotalpart();
        this.groupId = release.getGroupId();
        this.size = release.getSize();
        this.postDate = release.getPostDate();
        this.addDate = release.getAddDate();
        this.guid = release.getGuid();
        this.fromName = release.getFromName();
        this.completion = release.getCompletion();
        this.category = release.getCategory();
        this.categoryId = category.getId();
        this.regexId = release.getRegexId();
        this.rageId = release.getRageId();
        this.seriesFull = release.getSeriesFull();
        this.season = release.getSeason();
        this.episode = release.getEpisode();
        this.tvTitle = release.getTvTitle();
        this.tvAirDate = release.getTvAirDate();
        this.imdbId = release.getImdbId();
        this.musicInfoId = release.getMusicInfoId();
        this.consoleInfoId = release.getConsoleInfoId();
        this.reqId = release.getReqId();
        this.grabs = release.getGrabs();
        this.comments = release.getComments();
        this.passwordStatus = release.getPasswordStatus();
    }*/

    private Release release;
    private int categoryId;
    private String groupName;

    public ReleaseVO(Release release) {
        this.release = release;
        this.categoryId = release.getCategory().getId();
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


}
