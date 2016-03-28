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
