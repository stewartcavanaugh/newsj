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

/**
 * User: Sten Martinez
 * Date: 7/1/16
 * Time: 3:36 PM
 */
public class EditUserVO {
    private long userId = -1;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private boolean movieView;
    private boolean musicView;
    private boolean consoleView;
    private int grabs;
    private int invites;
    private int role;

    public EditUserVO() {
    }

    public EditUserVO(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.movieView = user.getMovieView() == 1;
        this.musicView = user.getMusicView() == 1;
        this.consoleView = user.getConsoleView() == 1;
        this.grabs = user.getGrabs();
        this.invites = user.getInvites();
        this.role = user.getRole();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isMovieView() {
        return movieView;
    }

    public void setMovieView(boolean movieView) {
        this.movieView = movieView;
    }

    public boolean isMusicView() {
        return musicView;
    }

    public void setMusicView(boolean musicView) {
        this.musicView = musicView;
    }

    public boolean isConsoleView() {
        return consoleView;
    }

    public void setConsoleView(boolean consoleView) {
        this.consoleView = consoleView;
    }

    public int getGrabs() {
        return grabs;
    }

    public void setGrabs(int grabs) {
        this.grabs = grabs;
    }

    public int getInvites() {
        return invites;
    }

    public void setInvites(int invites) {
        this.invites = invites;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
