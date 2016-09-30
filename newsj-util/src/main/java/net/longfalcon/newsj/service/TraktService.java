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
import net.longfalcon.newsj.ws.trakt.TraktEpisodeResult;
import net.longfalcon.newsj.ws.trakt.TraktResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * User: Sten Martinez
 * Date: 4/28/16
 * Time: 2:25 PM
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class TraktService {

    private static final Log _log = LogFactory.getLog(TraktService.class);

    private Config config;

    private RestTemplate restTemplate;

    public TraktResult[] searchTvShowByName(String name) {
        return searchByName(name, "show");
    }

    public TraktResult[] searchMovieByName(String name) {
        return searchByName(name, "movie");
    }

    public TraktResult[] searchByRageId(long rageId) {
        return searchById(String.valueOf(rageId), "tvrage");
    }

    public TraktResult[] searchShowByTraktId(long traktId) {
        return searchById(String.valueOf(traktId), "trakt-show");
    }

    public TraktResult[] searchEpisodeByTraktId(long traktId) {
        return searchById(String.valueOf(traktId), "trakt-episode");
    }

    /**
     * call ID lookup web service
     * @param id     id to look up that matches the id type. String to allow imdb queries "ttxxxxxx"
     * @param idType Possible values:  trakt-movie , trakt-show , trakt-episode , imdb , tmdb , tvdb , tvrage .
     * @return
     */
    protected  TraktResult[] searchById(String id, String idType) {
        try {
            String traktApiUrl = config.getTraktApiUrl();
            String traktAppId = config.getTraktAppId();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-type", "application/json");
            httpHeaders.set("trakt-api-key", traktAppId);
            httpHeaders.set("trakt-api-version", "2");
            HttpEntity<?> requestEntity = new HttpEntity(httpHeaders);

            UriComponents uriComponents = UriComponentsBuilder.fromUriString(traktApiUrl + "/search")
                    .queryParam("id_type", idType).queryParam("id", id).build();
            ResponseEntity<TraktResult[]> responseEntity = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, requestEntity, TraktResult[].class);
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode.is2xxSuccessful() || statusCode.is3xxRedirection()) {
                return responseEntity.getBody();
            } else {
                _log.error(String.format("Trakt Search request: \n%s\n failed with HTTP code %s : %s", uriComponents.toString(), statusCode.toString(), statusCode.getReasonPhrase()));
                return null;
            }

        } catch (Exception e) {
            _log.error(e.toString(), e);
        }
        return null;
    }

    /**
     * call text search web service
     * @param name
     * @param type  Possible values:  movie , show , episode , person , list .
     * @return
     */
    protected TraktResult[] searchByName(String name, String type) {
        try {
            String traktApiUrl = config.getTraktApiUrl();
            String traktAppId = config.getTraktAppId();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-type", "application/json");
            httpHeaders.set("trakt-api-key", traktAppId);
            httpHeaders.set("trakt-api-version", "2");
            HttpEntity<?> requestEntity = new HttpEntity(httpHeaders);

            UriComponents uriComponents = UriComponentsBuilder.fromUriString(traktApiUrl + "/search")
                    .queryParam("query", name).queryParam("type", type).build();
            ResponseEntity<TraktResult[]> responseEntity = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, requestEntity, TraktResult[].class);
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode.is2xxSuccessful() || statusCode.is3xxRedirection()) {
                return responseEntity.getBody();
            } else {
                _log.error(String.format("Trakt Search request: \n%s\n failed with HTTP code %s : %s", uriComponents.toString(), statusCode.toString(), statusCode.getReasonPhrase()));
                return null;
            }

        } catch (Exception e) {
            _log.error(e.toString(), e);
        }
        return null;
    }

    public TraktEpisodeResult getEpisode(long traktId, int season, int episode) {
        try {
            String traktApiUrl = config.getTraktApiUrl();
            String traktAppId = config.getTraktAppId();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-type", "application/json");
            httpHeaders.set("trakt-api-key", traktAppId);
            httpHeaders.set("trakt-api-version", "2");
            HttpEntity<?> requestEntity = new HttpEntity(httpHeaders);

            UriComponents uriComponents = UriComponentsBuilder.fromUriString(traktApiUrl + "/shows/" + traktId + "/seasons/" + season + "/episodes/" + episode)
                    .queryParam("extended", "full").build();
            ResponseEntity<TraktEpisodeResult> responseEntity = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, requestEntity, TraktEpisodeResult.class);
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode.is2xxSuccessful() || statusCode.is3xxRedirection()) {
                return responseEntity.getBody();
            } else {
                _log.error(String.format("Trakt Search request: \n%s\n failed with HTTP code %s : %s", uriComponents.toString(), statusCode.toString(), statusCode.getReasonPhrase()));
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
