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

    FsFile getFile(String fileName) throws IOException;

    File getTempFile(String name) throws IOException;

    String getName();

    void setName(String name);
}
