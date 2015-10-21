package net.longfalcon.newsj.util;

/**
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 12:25 PM
 */
public class ValidatorUtil {
    public static boolean isNull(String s) {
        return s == null || s.isEmpty() || s.equals("");
    }

    public static boolean isNotNull(String s) {
        return !isNull(s);
    }

    public static boolean isNumeric(String s) {
        try {
            long n = Long.decode(s);
        } catch (NumberFormatException e) {
            // do nothing
            return false;
        }

        return true;
    }

    public static boolean isNotNull(Integer integer) {
        return !(integer == null || integer == 0 || integer == -1);
    }

    public static boolean isNotNull(Long l) {
        return !(l == null || l == 0 || l == -1);
    }
}
