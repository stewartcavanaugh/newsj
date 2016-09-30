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

import net.longfalcon.newsj.model.User;

import java.util.Date;

/**
 * User data view object
 * User: Sten Martinez
 * Date: 11/9/15
 * Time: 12:01 PM
 */
public class UserData {
    private long id;
    private String username;
    private String email;
    private int role;
    private String host;
    private int grabs;
    private String rssToken;
    private Date createDate;
    private String resetGuid;
    private Date lastLogin;
    private Date apiAccess;
    private int invites;
    private Long invitedBy;
    private int movieView;
    private int musicView;
    private int consoleView;
    private String userseed;

    public UserData(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.host = user.getHost();
        this.grabs = user.getGrabs();
        this.rssToken = user.getRssToken();
        this.createDate = user.getCreateDate();
        this.resetGuid = user.getResetGuid();
        this.lastLogin = user.getLastLogin();
        this.apiAccess = user.getApiAccess();
        this.invites = user.getInvites();
        this.invitedBy = user.getInvitedBy();
        this.movieView = user.getMovieView();
        this.musicView = user.getMusicView();
        this.consoleView = user.getConsoleView();
        this.userseed = user.getUserseed();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getGrabs() {
        return grabs;
    }

    public void setGrabs(int grabs) {
        this.grabs = grabs;
    }

    public String getRssToken() {
        return rssToken;
    }

    public void setRssToken(String rssToken) {
        this.rssToken = rssToken;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getResetGuid() {
        return resetGuid;
    }

    public void setResetGuid(String resetGuid) {
        this.resetGuid = resetGuid;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getApiAccess() {
        return apiAccess;
    }

    public void setApiAccess(Date apiAccess) {
        this.apiAccess = apiAccess;
    }

    public int getInvites() {
        return invites;
    }

    public void setInvites(int invites) {
        this.invites = invites;
    }

    public Long getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(Long invitedBy) {
        this.invitedBy = invitedBy;
    }

    public int getMovieView() {
        return movieView;
    }

    public void setMovieView(int movieView) {
        this.movieView = movieView;
    }

    public int getMusicView() {
        return musicView;
    }

    public void setMusicView(int musicView) {
        this.musicView = musicView;
    }

    public int getConsoleView() {
        return consoleView;
    }

    public void setConsoleView(int consoleView) {
        this.consoleView = consoleView;
    }

    public String getUserseed() {
        return userseed;
    }

    public void setUserseed(String userseed) {
        this.userseed = userseed;
    }
}
