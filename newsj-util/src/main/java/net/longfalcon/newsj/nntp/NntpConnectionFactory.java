package net.longfalcon.newsj.nntp;

import net.longfalcon.newsj.Config;
import net.longfalcon.newsj.nntp.client.CustomNNTPClient;
import net.longfalcon.newsj.nntp.client.NewsClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * User: Sten Martinez
 * Date: 10/6/15
 * Time: 8:11 AM
 */
@Service
public class NntpConnectionFactory {

    private static final Log _log = LogFactory.getLog(NntpConnectionFactory.class);

    @Autowired
    private Config config;

    public NewsClient getNntpClient() {
        NewsClient nntpClient = null;
        try {
            nntpClient = new CustomNNTPClient();
            int port = Integer.parseInt(config.getNntpPort());
            nntpClient.connect(config.getNntpServer(), port);
            nntpClient.authenticate(config.getNntpUserName(), config.getNntpPassword());
            nntpClient.setKeepAlive(true);
        } catch (IOException e) {
            _log.error(e.toString(), e);
        }

        return nntpClient;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
