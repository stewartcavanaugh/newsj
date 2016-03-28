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

import net.longfalcon.newsj.util.ValidatorUtil;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 5:39 PM
 */
public class DirectoryImpl implements Directory {

    private String name;
    private File file;
    private Directory parent;
    private Map<String, Directory> childDirs = new LinkedHashMap<>();
    private Map<String, FsFile> childFiles = new LinkedHashMap<>();

    public DirectoryImpl(File file) {
        this.file = file;
        this.parent = null;
        if (!file.exists()) {
            file.mkdir();
        }
        name = file.getName();
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.isDirectory()) {
                    childDirs.put(child.getName(), new DirectoryImpl(child));
                } else {
                    childFiles.put(child.getName(), new FsFileImpl(child));
                }
            }
        }
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    @Override
    public Directory getDirectory(String relativePath) {
        return getDirectory(relativePath, true);
    }

    @Override
    public Directory getDirectory(String relativePath, boolean create) {
        if (ValidatorUtil.isNull(relativePath)) {
            return this;
        }
        String[] pathElements = relativePath.split("/");
        if (pathElements.length > 0) {
            String pathElement = pathElements[0];
            StringBuilder subPath = new StringBuilder();
            if (pathElements.length > 1) {
                for (int i = 1; i < pathElements.length; i++) {
                    subPath.append(pathElements[i]).append(File.separator);
                }
            }

            if (childFiles.containsKey(pathElement)) {
                return null;
            }

            if (childDirs.containsKey(pathElement)) {
                Directory child = childDirs.get(pathElement);
                return child.getDirectory(subPath.toString());
            }

            if (create) {
                String newDirectoryName = file.getAbsolutePath() + File.separator + pathElement;
                File newFile = new File(newDirectoryName);
                newFile.mkdirs();
                DirectoryImpl directory = new DirectoryImpl(newFile);
                directory.setParent(this);
                childDirs.put(newFile.getName(), directory);
                return directory.getDirectory(subPath.toString());
            } else {
                return null;
            }
        }
        return this;
    }

    @Override
    public FsFile getFile(String fileName) throws IOException {
        if (fileName.contains("/")) {
            throw new IllegalArgumentException("File name argument must be a relative file name");
        } else {
            if (childFiles.containsKey(fileName)) {
                return childFiles.get(fileName);
            }

            String newFileName = file.getAbsolutePath() + File.separator + fileName;
            File newFile = new File(newFileName);
            boolean created = newFile.createNewFile();
            if (created) {
                FsFileImpl fsFile = new FsFileImpl(newFile);
                fsFile.setParent(this);
                childFiles.put(fileName, fsFile);
                return fsFile;
            } else {
                throw new RuntimeException("Something went wrong. File " + newFileName + " may already exist or is unwritable");
            }

        }
    }

    @Override
    public boolean fileExists(String fileName) {
        if (fileName.contains("/")) {
            throw new IllegalArgumentException("File name argument must be a relative file name");
        } else {
            return childFiles.containsKey(fileName);
        }
    }

    @Override
    public File getTempFile(String name) throws IOException {
        File tempFile = new File(file.getAbsolutePath() + File.separator + name + ".tmp");
        tempFile.createNewFile();
        tempFile.deleteOnExit();

        return tempFile;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DirectoryImpl{" +
                "name='" + name + '\'' +
                '}';
    }
}
