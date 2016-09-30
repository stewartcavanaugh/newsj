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
 * Time: 2:37 PM
 */
public class TraktResult {

    private String type;
    private float score;  // search score

    @JsonProperty("movie")
    private TraktMovieResult movieResult;

    @JsonProperty("show")
    private TraktShowResult showResult;

    @JsonProperty("season")
    private TraktSeasonResult seasonResult;

    @JsonProperty("episode")
    private TraktEpisodeResult episodeResult;

    @JsonProperty("person")
    private Object personResult;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public TraktMovieResult getMovieResult() {
        return movieResult;
    }

    public void setMovieResult(TraktMovieResult movieResult) {
        this.movieResult = movieResult;
    }

    public TraktShowResult getShowResult() {
        return showResult;
    }

    public void setShowResult(TraktShowResult showResult) {
        this.showResult = showResult;
    }

    public TraktSeasonResult getSeasonResult() {
        return seasonResult;
    }

    public void setSeasonResult(TraktSeasonResult seasonResult) {
        this.seasonResult = seasonResult;
    }

    public TraktEpisodeResult getEpisodeResult() {
        return episodeResult;
    }

    public void setEpisodeResult(TraktEpisodeResult episodeResult) {
        this.episodeResult = episodeResult;
    }
}
