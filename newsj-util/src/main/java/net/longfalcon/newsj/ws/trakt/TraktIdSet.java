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
 * Time: 2:54 PM
 */
public class TraktIdSet {
    private long trakt;
    private long tmdb;
    private long tvdb;
    private long tvrage;
    private String imdb;
    private String slug;

    public long getTrakt() {
        return trakt;
    }

    public void setTrakt(long trakt) {
        this.trakt = trakt;
    }

    public long getTmdb() {
        return tmdb;
    }

    public void setTmdb(long tmdb) {
        this.tmdb = tmdb;
    }

    public long getTvdb() {
        return tvdb;
    }

    public void setTvdb(long tvdb) {
        this.tvdb = tvdb;
    }

    public long getTvrage() {
        return tvrage;
    }

    public void setTvrage(long tvrage) {
        this.tvrage = tvrage;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
