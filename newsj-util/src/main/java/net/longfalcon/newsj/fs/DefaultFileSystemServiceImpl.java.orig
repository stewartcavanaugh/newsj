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
