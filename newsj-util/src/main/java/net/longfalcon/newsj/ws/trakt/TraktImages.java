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

package net.longfalcon.newsj.ws.trakt;

/**
 * User: Sten Martinez
 * Date: 4/28/16
 * Time: 5:40 PM
 */
public class TraktImages {
    private TraktImage poster;
    private TraktImage fanart;
    private TraktImage screenshot;

    public TraktImage getPoster() {
        return poster;
    }

    public void setPoster(TraktImage poster) {
        this.poster = poster;
    }

    public TraktImage getFanart() {
        return fanart;
    }

    public void setFanart(TraktImage fanart) {
        this.fanart = fanart;
    }

    public TraktImage getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(TraktImage screenshot) {
        this.screenshot = screenshot;
    }
}
