package net.longfalcon.newsj.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 10:41 PM
 */
public class EncodingUtil {
    public static final String BLANK_STRING = "";

    private static final String _UTF8 = "UTF-8";

    private static MessageDigest _md5Digest = null;
    private static final Log _log = LogFactory.getLog(EncodingUtil.class);

    public static String md5Hash(String input) {
        if (_md5Digest == null) {
            try {
                _md5Digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                // this will not fail.
                e.printStackTrace();
                return null;
            }
        }

        byte[] digest = _md5Digest.digest(input.getBytes());

        StringBuilder sb = new StringBuilder();

        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static String urlEncode(String input) {

        try {
            return URLEncoder.encode(input, _UTF8);
        } catch (UnsupportedEncodingException e) {
            _log.error(e);
        }
        return BLANK_STRING;
    }
}
