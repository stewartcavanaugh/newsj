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

import net.longfalcon.newsj.fs.FileSystemService;
import net.longfalcon.newsj.fs.model.Directory;
import net.longfalcon.newsj.fs.model.FsFile;
import net.longfalcon.newsj.model.ConsoleInfo;
import net.longfalcon.newsj.persistence.ConsoleInfoDAO;
import net.longfalcon.newsj.persistence.GenreDAO;
import net.longfalcon.newsj.util.StreamUtil;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 2:18 PM
 */
public class GameService {

    ConsoleInfoDAO consoleInfoDAO;

    GenreDAO genreDAO;

    FileSystemService fileSystemService;

    public void processConsoleReleases() {
        //TODO
    }

    @Transactional
    public void updateConsoleInfo(ConsoleInfo consoleInfo, InputStream coverStream) throws IOException {
        consoleInfoDAO.updateConsoleInfo(consoleInfo);

        if (coverStream != null) {
            Directory directory = fileSystemService.getDirectory("/images/covers/console", true);
            FsFile fsFile = directory.getFile(consoleInfo.getId() + ".jpg");
            StreamUtil.transferByteArray(coverStream, fsFile.getOutputStream(), 1024);
            consoleInfo.setCover(true);
            consoleInfoDAO.updateConsoleInfo(consoleInfo);
        }
    }

    public ConsoleInfoDAO getConsoleInfoDAO() {
        return consoleInfoDAO;
    }

    public void setConsoleInfoDAO(ConsoleInfoDAO consoleInfoDAO) {
        this.consoleInfoDAO = consoleInfoDAO;
    }

    public GenreDAO getGenreDAO() {
        return genreDAO;
    }

    public void setGenreDAO(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    public FileSystemService getFileSystemService() {
        return fileSystemService;
    }

    public void setFileSystemService(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }
}
