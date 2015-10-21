package net.longfalcon.newsj.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 10/21/15
 * Time: 1:48 PM
 */
public class ParseUtil {
    private static final Log _log = LogFactory.getLog(ParseUtil.class);
    private static final Pattern _imdbPattern;
    private static final Pattern _ragePattern;
    static {
        _imdbPattern = Pattern.compile("imdb.*?(tt|Title\\?)(\\d{7})", Pattern.CASE_INSENSITIVE);
        _ragePattern = Pattern.compile("tvrage\\.com\\/shows\\/id-(\\d{1,6})", Pattern.CASE_INSENSITIVE);
    }

    public static String parseImdb(String nfoText) {
        Matcher matcher = _imdbPattern.matcher(nfoText);
        if (matcher.find()) {
            try {
                String imdbString = matcher.group(2);
                return imdbString.trim();
            } catch (Exception e) {
                _log.debug(e);
            }
        }
        return null;
    }

    public static String parseRageId(String nfoText) {
        Matcher matcher = _ragePattern.matcher(nfoText);
        if (matcher.find()) {
            try {
                String rageIdString = matcher.group(1);
                return rageIdString.trim();
            } catch (Exception e) {
                _log.debug(e);
            }
        }
        return null;
    }
}
