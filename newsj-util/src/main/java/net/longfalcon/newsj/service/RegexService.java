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
