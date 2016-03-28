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
import net.longfalcon.newsj.model.MusicInfo;
import net.longfalcon.newsj.persistence.MusicInfoDAO;
import net.longfalcon.newsj.util.StreamUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 2:18 PM
 */
public class MusicService {

    MusicInfoDAO musicInfoDAO;

    FileSystemService fileSystemService;

    public void processMusicReleases() {
        // TODO
    }

    public void updateMusicInfo(MusicInfo musicInfo, InputStream coverInputStream) throws IOException {

        musicInfoDAO.update(musicInfo);

        if (coverInputStream != null) {
            Directory directory = fileSystemService.getDirectory("/images/covers/console");
            FsFile fsFile = directory.getFile(musicInfo.getId() + ".jpg");
            StreamUtil.transferByteArray(coverInputStream, fsFile.getOutputStream(), 1024);
            musicInfo.setCover(true);
            musicInfoDAO.update(musicInfo);
        }
    }

    public FileSystemService getFileSystemService() {
        return fileSystemService;
    }

    public void setFileSystemService(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    public MusicInfoDAO getMusicInfoDAO() {
        return musicInfoDAO;
    }

    public void setMusicInfoDAO(MusicInfoDAO musicInfoDAO) {
        this.musicInfoDAO = musicInfoDAO;
    }
}
