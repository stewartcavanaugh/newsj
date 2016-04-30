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

package net.longfalcon.newsj.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.longfalcon.newsj.test.BaseFsTestSupport;
import net.longfalcon.newsj.ws.trakt.TraktEpisodeResult;
import net.longfalcon.newsj.ws.trakt.TraktResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

/**
 * User: Sten Martinez
 * Date: 4/28/16
 * Time: 5:27 PM
 */
public class TraktServiceTest extends BaseFsTestSupport {

    @Autowired
    TraktService traktService;

    @Test
    public void testGetResultsObject() throws Exception {
        InputStream testJsonFileStream = this.getClass().getClassLoader().getResourceAsStream("trakt-text-search-response.json");
        ObjectMapper mapper = new ObjectMapper();
        TraktResult[] results = mapper.readValue(testJsonFileStream, TraktResult[].class);
        Assert.assertTrue(results[0].getScore() > 50);
    }

    @Test
    public void testGetEpisodeObject() throws Exception {
        InputStream testJsonFileStream = this.getClass().getClassLoader().getResourceAsStream("trakt-get-episode-extended-response.json");
        ObjectMapper mapper = new ObjectMapper();
        TraktEpisodeResult result = mapper.readValue(testJsonFileStream, TraktEpisodeResult.class);
        Assert.assertEquals("Winter Is Coming", result.getTitle());
    }

    @Test
    public void testSearchByRageId() throws Exception {
        TraktResult[] traktResults = traktService.searchByRageId(3506);
        Assert.assertEquals("Family Guy", traktResults[0].getShowResult().getTitle());
    }

    @Test
    public void testSearchMovieByName() throws Exception {
        TraktResult[] traktResults = traktService.searchMovieByName("life of brian");
        Assert.assertEquals("Life of Brian", traktResults[0].getMovieResult().getTitle());
    }

    @Test
    public void testSearchTvShowByName() throws Exception {
        TraktResult[] traktResults = traktService.searchTvShowByName("family guy");
        Assert.assertEquals("Family Guy", traktResults[0].getShowResult().getTitle());
    }
}