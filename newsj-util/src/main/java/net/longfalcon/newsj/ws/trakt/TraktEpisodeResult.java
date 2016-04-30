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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 4/28/16
 * Time: 3:13 PM
 */
public class TraktEpisodeResult {
    private String title;
    private String overview;
    private int season;
    private int number;
    @JsonProperty("number_abs")
    private int numberAbs;
    private float rating;
    private int votes;
    @JsonProperty("first_aired")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date firstAired;
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date updatedAt;
    @JsonProperty("available_translations")
    private Object availableTranslations;
    @JsonProperty("images")
    private TraktImages traktImages;
    private TraktIdSet ids;

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

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public TraktImages getTraktImages() {
        return traktImages;
    }

    public void setTraktImages(TraktImages traktImages) {
        this.traktImages = traktImages;
    }

    public TraktIdSet getIds() {
        return ids;
    }

    public void setIds(TraktIdSet ids) {
        this.ids = ids;
    }

    public int getNumberAbs() {
        return numberAbs;
    }

    public void setNumberAbs(int numberAbs) {
        this.numberAbs = numberAbs;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Date getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(Date firstAired) {
        this.firstAired = firstAired;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getAvailableTranslations() {
        return availableTranslations;
    }

    public void setAvailableTranslations(Object availableTranslations) {
        this.availableTranslations = availableTranslations;
    }
}
