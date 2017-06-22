package net.longfalcon.newsj.fs.model;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author longfalcon
 * @since 6/21/17
 */
public class S3FsFileImpl implements FsFile {
    private AmazonS3Client s3client;
    private String baseDir = null;
    private String bucketName = null;
    private String filename;
    private S3Object s3Object;
    private File tempFile;
    private boolean updateFile = false;
    private static final Log _log = LogFactory.getLog(S3FsFileImpl.class);

    public S3FsFileImpl(AmazonS3Client s3client, String bucketName, String baseDir, String filename) throws IOException {
        this.s3client = s3client;
        this.baseDir = baseDir;
        this.bucketName = bucketName;
        this.filename = filename;
        tempFile = File.createTempFile(filename, "tmp");
        s3Object = s3client.getObject(bucketName, baseDir + "/" + filename);
        if (s3Object == null) {
            // object does not exist
            updateFile = true;
        }
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        if (s3Object != null) {
            return s3Object.getObjectContent();
        } else return new FileInputStream(tempFile);
    }

    @Override
    public OutputStream getOutputStream() throws FileNotFoundException {
        updateFile = true;
        return new FileOutputStream(tempFile);
    }

    @Override
    public void close() {
        if (updateFile) {
            s3client.putObject(new PutObjectRequest(bucketName, baseDir + "/" + filename, tempFile));
        }
        else {
            if (s3Object != null) {
                try {
                    s3Object.close();
                } catch (IOException e) {
                    _log.error(e.toString(), e);
                }
            }
        }
        tempFile.deleteOnExit();
    }

    @Override
    public String getName() {
        return filename;
    }

    @Override
    public void setName(String name) {
        this.filename = name;
    }
}
