package net.longfalcon.newsj.fs.model;

import com.amazonaws.services.s3.AmazonS3Client;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author longfalcon
 * @since 6/20/17
 */
public class S3DirectoryImpl implements Directory {

    private AmazonS3Client s3client;
    private String baseDir = null;
    private String bucketName = null;

    private static final Log _log = LogFactory.getLog(S3DirectoryImpl.class);

    public S3DirectoryImpl(AmazonS3Client s3client, String bucketName, String baseDir) {
        _log.info("Creating Directory Object for Directory "+baseDir);
        this.s3client = s3client;
        this.baseDir = baseDir;
        this.bucketName = bucketName;
    }

    @Override
    public Directory getDirectory(String relativePath) {
        return new S3DirectoryImpl(s3client, bucketName, baseDir + "/" +relativePath);
    }

    @Override
    public Directory getDirectory(String relativePath, boolean create) {
        return getDirectory(relativePath);
    }

    @Override
    public FsFile getFile(String fileName) throws IOException {
        _log.info("Getting File " + baseDir + "/" + fileName);

        return new S3FsFileImpl(s3client, bucketName, baseDir, fileName);
    }

    @Override
    public File getTempFile(String name) throws IOException {
        return File.createTempFile(name, "tmp");
    }

    @Override
    public boolean fileExists(String fileName) {
        return true;
    }

    @Override
    public String getName() {
        return baseDir;
    }

    @Override
    public void setName(String name) {

    }
}
