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

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 7/1/16
 * Time: 1:50 PM
 */
public class ProfileVO {
    private long userId;
    private String email;
    private String password;
    private String confirmPassword;
    private boolean movieView;
    private boolean musicView;
    private boolean consoleView;
    private List<Integer> exCatIds;

    public ProfileVO(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.movieView = user.getMovieView() == 1;
        this.musicView = user.getMusicView() == 1;
        this.consoleView = user.getConsoleView() == 1;
    }

    public long getUserId() {
        return userId;
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

    public List<Integer> getExCatIds() {
        return exCatIds;
    }

    public void setExCatIds(List<Integer> exCatIds) {
        this.exCatIds = exCatIds;
    }
}
