/*
 * Copyright (c) 2015. Sten Martinez
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

package net.longfalcon.newsj.fs;

import net.longfalcon.newsj.fs.model.Directory;
import net.longfalcon.newsj.fs.model.DirectoryImpl;
import net.longfalcon.newsj.util.ValidatorUtil;

import java.io.File;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 5:36 PM
 */
public class DefaultFileSystemServiceImpl implements FileSystemService {

    private String baseDir = null;
    private Directory baseDirectory;

    public void init() {
        if (ValidatorUtil.isNull(baseDir)) {
            String userHome = System.getenv("HOME");
            baseDir = userHome + File.separator + ".newsj";
        }

        File baseDirFile = new File(baseDir);

        if (!baseDirFile.exists() ) {
            baseDirFile.mkdirs();
        }

        baseDirectory = new DirectoryImpl(baseDirFile);
    }

    @Override
    public Directory getDirectory(String relativePath) {
        return baseDirectory.getDirectory(relativePath);
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }
}
