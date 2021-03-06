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

package net.longfalcon.newsj.ws.newznab.caps;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * User: longfalcon
 * Date: 3/1/16
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class SearchingType {

    @XmlElement(name = "search")
    protected SearchType searchType;

    @XmlElement(name = "tv-search")
    protected TvSearchType tvSearchType;

    @XmlElement(name = "movie-search")
    protected MovieSearchType movieSearchType;

    @XmlElement(name = "audio-search")
    protected AudioSearchType audioSearchType;

    public SearchingType() {
    }

    public SearchingType(boolean searchAvailable, boolean tvSearchAvailable, boolean movieSearchAvailable, boolean audioSearchAvailable) {
        searchType = new SearchType(searchAvailable);
        tvSearchType = new TvSearchType(tvSearchAvailable);
        movieSearchType = new MovieSearchType(movieSearchAvailable);
        audioSearchType = new AudioSearchType(audioSearchAvailable);
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public TvSearchType getTvSearchType() {
        return tvSearchType;
    }

    public void setTvSearchType(TvSearchType tvSearchType) {
        this.tvSearchType = tvSearchType;
    }

    public MovieSearchType getMovieSearchType() {
        return movieSearchType;
    }

    public void setMovieSearchType(MovieSearchType movieSearchType) {
        this.movieSearchType = movieSearchType;
    }

    public AudioSearchType getAudioSearchType() {
        return audioSearchType;
    }

    public void setAudioSearchType(AudioSearchType audioSearchType) {
        this.audioSearchType = audioSearchType;
    }
}
