package net.longfalcon.newsj.fs;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import net.longfalcon.newsj.Config;
import net.longfalcon.newsj.fs.model.Directory;
import net.longfalcon.newsj.fs.model.S3DirectoryImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author longfalcon
 * @since 6/20/17
 */
public class S3FileSystemServiceImpl implements FileSystemService {

    private AmazonS3Client s3client;
    private String baseDir = null;
    private Config config;
    private Directory baseDirectory;

    private static final Log _log = LogFactory.getLog(S3FileSystemServiceImpl.class);

    public void init() {
        String bucketName = config.getAwsBucketName();
        baseDir = config.getNzbFileLocation();
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        s3client = new AmazonS3Client(credentials);
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        s3client.setRegion(usWest2);

        baseDirectory = new S3DirectoryImpl(s3client, bucketName, baseDir);
        _log.info("created new S3 storage in bucket " + bucketName + " with base folder " + baseDir);
    }

    @Override
    public Directory getDirectory(String relativePath) {
        return baseDirectory.getDirectory(relativePath);
    }

    @Override
    public Directory getDirectory(String relativePath, boolean create) {
        return baseDirectory.getDirectory(relativePath, create);
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
