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
import net.longfalcon.newsj.fs.DefaultFileSystemServiceImpl;
import net.longfalcon.newsj.fs.FileSystemService;
import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.persistence.SiteDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Sten Martinez
 * Date: 12/15/15
 * Time: 9:55 PM
 */
@Service
public class InstallerService {

    private SiteDAO siteDAO;
    private FileSystemService fileSystemService;
    private Config config;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void initializeNzbStorage(String newNzbPath) {
        Site defaultSite = siteDAO.getDefaultSite();
        defaultSite.setNzbPath(newNzbPath);
        siteDAO.update(defaultSite);
        DefaultFileSystemServiceImpl fileSystemServiceImpl = (DefaultFileSystemServiceImpl) fileSystemService;
        config.init();
        fileSystemServiceImpl.init();
    }

    public SiteDAO getSiteDAO() {
        return siteDAO;
    }

    public void setSiteDAO(SiteDAO siteDAO) {
        this.siteDAO = siteDAO;
    }

    public FileSystemService getFileSystemService() {
        return fileSystemService;
    }

    public void setFileSystemService(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
