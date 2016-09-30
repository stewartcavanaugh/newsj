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

package net.longfalcon.newsj.fs.model;

import java.io.File;
import java.io.IOException;

/**
 * User: Sten Martinez
 * Date: 10/9/15
 * Time: 8:56 AM
 */
public interface Directory {
    Directory getDirectory(String relativePath);

    Directory getDirectory(String relativePath, boolean create);

    FsFile getFile(String fileName) throws IOException;

    File getTempFile(String name) throws IOException;

    boolean fileExists(String fileName);

    String getName();

    void setName(String name);
}
