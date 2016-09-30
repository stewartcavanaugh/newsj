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

package net.longfalcon.newsj.ws.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/18/16
 * Time: 5:45 PM
 */
public class TmdbFindResults {
    @JsonProperty("movie_results")
    private List<TmdbMovieResults> movieResults;
    @JsonProperty("person_results")
    private java.lang.Object[] personResults;
    @JsonProperty("tv_episode_results")
    private java.lang.Object[] tvEpisodeResults;
    @JsonProperty("tv_results")
    private List<TmdbTvResults> tvResults;
    @JsonProperty("tv_season_results")
    private java.lang.Object[] tvSeasonResults;

    public List<TmdbMovieResults> getMovieResults() {
        return movieResults;
    }

    public void setMovieResults(List<TmdbMovieResults> movieResults) {
        this.movieResults = movieResults;
    }

    public Object[] getPersonResults() {
        return personResults;
    }

    public void setPersonResults(Object[] personResults) {
        this.personResults = personResults;
    }

    public Object[] getTvEpisodeResults() {
        return tvEpisodeResults;
    }

    public void setTvEpisodeResults(Object[] tvEpisodeResults) {
        this.tvEpisodeResults = tvEpisodeResults;
    }

    public List<TmdbTvResults> getTvResults() {
        return tvResults;
    }

    public void setTvResults(List<TmdbTvResults> tvResults) {
        this.tvResults = tvResults;
    }

    public Object[] getTvSeasonResults() {
        return tvSeasonResults;
    }

    public void setTvSeasonResults(Object[] tvSeasonResults) {
        this.tvSeasonResults = tvSeasonResults;
    }
}
