/*
 * Copyright (c) 2015. Sten Martinez
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
import net.longfalcon.newsj.ws.google.GoogleSearchResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

/**
 * User: Sten Martinez
 * Date: 10/21/15
 * Time: 11:43 AM
 */
public class GoogleSearchServiceTest {
    private GoogleSearchService googleSearchService;

    @Test
    public void testGetResultsObject() throws Exception {
        InputStream testJsonFileStream = this.getClass().getClassLoader().getResourceAsStream("google-search-results.json");
        ObjectMapper mapper = new ObjectMapper();
        GoogleSearchResponse response = mapper.readValue(testJsonFileStream, GoogleSearchResponse.class);
        Assert.assertEquals("http://en.wikipedia.org/wiki/Paris_Hilton" ,response.getResponseData().getResults().get(0).getUrl());
    }
}
