package net.longfalcon.newsj;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

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

/*    private String nntpServer;
    private String nntpUserName;
    private String nntpPassword;
    private String nntpPort;
    private Boolean nntpSslEnabled;*/
    private Resource[] propertyLocations;
    Properties properties;

    public void init() {
        properties = new Properties();
        for (Resource resource : propertyLocations) {
            try {
                properties.load(resource.getInputStream());
            } catch (IOException e) {
                _log.error("Unable to read from resource " + resource.getFilename());
            }
        }
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
}
