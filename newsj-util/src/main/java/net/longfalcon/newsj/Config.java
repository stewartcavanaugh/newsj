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

package net.longfalcon.newsj;

import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.persistence.SiteDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Properties;

/**
 * User: Sten Martinez
 * Date: 10/6/15
 * Time: 8:23 AM
 */
public class Config {
    private static final String _RELEASE_VERSION = "0.1";
    private static final Log _log = LogFactory.getLog(Config.class);

    private SiteDAO siteDAO;

    private Resource[] propertyLocations;
    Properties properties;
    private Site defaultSite;

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.REPEATABLE_READ)
    public void init() {
        properties = new Properties();
        for (Resource resource : propertyLocations) {
            try {
                properties.load(resource.getInputStream());
            } catch (IOException e) {
                _log.error("Unable to read from resource " + resource.getFilename());
            }
        }
        defaultSite = siteDAO.getDefaultSite();
    }

    public static String getReleaseVersion() {
        return _RELEASE_VERSION;
    }

    public void setPropertyLocations(Resource... propertyLocations) {
        this.propertyLocations = propertyLocations;
    }

    public String getNntpServer() {
        return properties.getProperty(PropsKeys.NNTP_SERVER);
    }

    public String getNntpUserName() {
        return properties.getProperty(PropsKeys.NNTP_USERNAME);
    }

    public String getNntpPassword() {
        return properties.getProperty(PropsKeys.NNTP_PASSWORD);
    }

    public String getNntpPort() {
        return properties.getProperty(PropsKeys.NNTP_PORT);
    }

    public Boolean getNntpSslEnabled() {
        return "true".equals(properties.getProperty(PropsKeys.NNTP_SSLENABLED));
    }

    public String getNzbFileLocation() {
        String fileLocation = null;

        try {
            fileLocation = getDefaultSite().getNzbPath();
        } catch (Exception e) {
            _log.error(e);
        }

        return fileLocation;
    }

    public String getTmdbApiUrl() {
        return properties.getProperty(PropsKeys.TMDB_API_KEY);
    }

    public int getYear() {
        DateTime dateTime = DateTime.now();
        return dateTime.getYear();
    }

    public Site getDefaultSite() {
        if (defaultSite == null) {
            defaultSite = siteDAO.getDefaultSite();
        }
        return defaultSite;
    }

    public SiteDAO getSiteDAO() {
        return siteDAO;
    }

    public void setSiteDAO(SiteDAO siteDAO) {
        this.siteDAO = siteDAO;
    }
}
