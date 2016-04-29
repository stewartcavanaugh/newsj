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
import net.longfalcon.newsj.ws.trakt.TraktResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.InputStream;

/**
 * User: Sten Martinez
 * Date: 4/28/16
 * Time: 5:27 PM
 */
public class TraktServiceTest {

    private TraktService traktService;

    @Test
    public void testGetResultsObject() throws Exception {
        InputStream testJsonFileStream = this.getClass().getClassLoader().getResourceAsStream("trakt-text-search-response.json");
        ObjectMapper mapper = new ObjectMapper();
        TraktResult[] results = mapper.readValue(testJsonFileStream, TraktResult[].class);
        Assert.assertTrue(results[0].getScore() > 50);
    }

    //@Test
    public void testSearchByRageId() throws Exception {
        TraktResult[] traktResults = traktService.searchByRageId(3506);
        System.out.println(traktResults[0].getShowResult().getTitle());
    }

    //@Test
    public void testSearchMovieByName() throws Exception {

    }

    //@Test
    public void testSearchTvShowByName() throws Exception {

    }

    //@Before
    public void setUp() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        traktService = (TraktService) context.getBean("traktService");
    }
}