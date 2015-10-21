package net.longfalcon.newsj.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 10/15/15
 * Time: 12:16 PM
 */
@Component
public class RegexService {

    private static final Log _log = LogFactory.getLog(RegexService.class);

    public void checkRegexesUptoDate(String latestRegexUrl, int latestRegexRevision) {
        RestTemplate restTemplate = new RestTemplate();

        String responseString = restTemplate.getForObject(latestRegexUrl, String.class);

        Pattern pattern = Pattern.compile("\\/\\*\\$Rev: (\\d{3,4})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(responseString);

        if (matcher.find()) {
            int revision = Integer.parseInt(matcher.group(1));
            if (revision > latestRegexRevision) {
                _log.info("URL " + latestRegexUrl + " reports new regexes. Run the following SQL at your own risk:");
                _log.info(responseString);
            }
        }
    }
}
