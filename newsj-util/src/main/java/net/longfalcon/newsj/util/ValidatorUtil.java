package net.longfalcon.newsj.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 12:25 PM
 */
public class ValidatorUtil {
    private static Pattern numericPattern = Pattern.compile("^[-]?[0-9]+$");
    public static boolean isNull(String s) {
        return s == null || s.isEmpty() || s.equals("");
    }

    public static boolean isNotNull(String s) {
        return !isNull(s);
    }

    public static boolean isNumeric(String s) {
        Matcher matcher = numericPattern.matcher(s);

        return matcher.matches();
    }

    public static boolean isNotNull(Integer integer) {
        return !(integer == null || integer == 0 || integer == -1);
    }

    public static boolean isNotNull(Long l) {
        return !(l == null || l == 0 || l == -1);
    }
}
