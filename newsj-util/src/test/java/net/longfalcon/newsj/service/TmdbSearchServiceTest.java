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
import net.longfalcon.newsj.ws.tmdb.TmdbFindResults;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 3/18/16
 * Time: 6:04 PM
 */
public class TmdbSearchServiceTest {

    @Test
    public void testGetResultsObject() throws Exception {
        InputStream testJsonFileStream = this.getClass().getClassLoader().getResourceAsStream("tmdb-movie-find-results.json");
        ObjectMapper mapper = new ObjectMapper();
        TmdbFindResults response = mapper.readValue(testJsonFileStream, TmdbFindResults.class);
        Assert.assertEquals("Finding Nemo", response.getMovieResults().get(0).getTitle());
        Date releaseDate = new DateTime(2003,5,30,0,0, DateTimeZone.UTC).toDate();
        Assert.assertEquals(releaseDate, response.getMovieResults().get(0).getReleaseDate() );
    }
}
