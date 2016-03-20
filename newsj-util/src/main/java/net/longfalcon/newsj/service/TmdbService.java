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

import net.longfalcon.newsj.Config;
import net.longfalcon.newsj.ws.tmdb.TmdbFindResults;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * User: Sten Martinez
 * Date: 3/18/16
 * Time: 5:22 PM
 */
@Service
public class TmdbService {

    private static final Log _log = LogFactory.getLog(TmdbService.class);

    private Config config;

    private RestTemplate restTemplate;

    public TmdbFindResults findResultsByImdbId(int imdbId) {
        try {
            String tmdbUrlBase = config.getTmdbApiUrl();
            String apiKey = config.getDefaultSite().getTmdbKey();

            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<?> requestEntity = new HttpEntity(httpHeaders);

            UriComponents uriComponents = UriComponentsBuilder.fromUriString(tmdbUrlBase + "/find/tt" + String.format("%07d", imdbId))
                    .queryParam("external_source", "imdb_id").queryParam("api_key", apiKey).build();
            ResponseEntity<TmdbFindResults> responseEntity = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, requestEntity, TmdbFindResults.class);
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode.is2xxSuccessful() || statusCode.is3xxRedirection()) {
                return responseEntity.getBody();
            } else {
                _log.error(String.format("Search request: \n%s\n failed with HTTP code %s : %s", uriComponents.toString(), statusCode.toString(), statusCode.getReasonPhrase()));
                return null;
            }

        } catch (Exception e) {
            _log.error(e.toString(), e);
        }
        return null;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
