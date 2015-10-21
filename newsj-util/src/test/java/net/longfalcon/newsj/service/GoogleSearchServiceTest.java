package net.longfalcon.newsj.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.longfalcon.newsj.ws.GoogleSearchResponse;
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
