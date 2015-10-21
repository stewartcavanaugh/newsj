package net.longfalcon.newsj.fs;

import net.longfalcon.newsj.fs.model.Directory;

/**
 * User: Sten Martinez
 * Date: 10/9/15
 * Time: 8:53 AM
 */
public interface FileSystemService {
    Directory getDirectory(String path);
}
