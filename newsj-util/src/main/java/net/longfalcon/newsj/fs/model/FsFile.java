package net.longfalcon.newsj.fs.model;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 9:27 PM
 */
public interface FsFile {
    InputStream getInputStream() throws FileNotFoundException;

    OutputStream getOutputStream() throws FileNotFoundException;

    String getName();

    void setName(String name);
}
