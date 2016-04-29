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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: Sten Martinez
 * Date: 4/28/16
 * Time: 3:07 PM
 */
public class TraktShowResult {
    private String title;
    private String overview;
    private int year;
    @JsonProperty("images")
    private TraktImages traktImages;
    private TraktIdSet ids;

    public TraktIdSet getIds() {
        return ids;
    }

    public void setIds(TraktIdSet ids) {
        this.ids = ids;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public TraktImages getTraktImages() {
        return traktImages;
    }

    public void setTraktImages(TraktImages traktImages) {
        this.traktImages = traktImages;
    }
}
