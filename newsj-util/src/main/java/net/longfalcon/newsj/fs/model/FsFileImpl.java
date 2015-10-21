package net.longfalcon.newsj.fs.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 9:32 PM
 */
public class FsFileImpl implements FsFile {
    private String name;
    private File file;
    private Directory parent;

    public FsFileImpl(File file) {
        this.file = file;
        name = file.getName();
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public OutputStream getOutputStream() throws FileNotFoundException {
        return new FileOutputStream(file);
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
        return "FsFileImpl{" +
                "name='" + name + '\'' +
                '}';
    }
}
