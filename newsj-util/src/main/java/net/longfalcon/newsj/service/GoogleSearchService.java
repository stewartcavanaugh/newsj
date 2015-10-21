package net.longfalcon.newsj.service;

import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.newsj.ws.GoogleSearchResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * User: Sten Martinez
 * Date: 10/20/15
 * Time: 11:42 AM
 */
@Component
public class GoogleSearchService {
    private static final Log _log = LogFactory.getLog(GoogleSearchService.class);

    private static final String _SEARCH_URL = "https://ajax.googleapis.com/ajax/services/search/web";

    private RestTemplate restTemplate;

    public GoogleSearchResponse search(String searchString, String referer) {
        try {
            if (ValidatorUtil.isNull(referer)) {
                referer = "http://longfalcon.net";
            }

            String v = "1.0";
            String userip = "192.168.0.1";
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Referer", referer);

            HttpEntity<?> requestEntity = new HttpEntity(httpHeaders);

            UriComponents uriComponents = UriComponentsBuilder.fromUriString(_SEARCH_URL)
                    .queryParam("v", v).queryParam("q", searchString).queryParam("userip", userip).build();
            ResponseEntity<GoogleSearchResponse> responseEntity = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, requestEntity, GoogleSearchResponse.class);
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode.is2xxSuccessful() || statusCode.is3xxRedirection()) {
                return responseEntity.getBody();
            } else {
                _log.error(String.format("Search request: \n%s\n failed with HTTP code %s : %s", uriComponents.toString(), statusCode.toString(), statusCode.getReasonPhrase()));
                return null;
            }
        } catch (Exception e) {
            _log.error(e);
        }

        return null;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
