/*
 * Copyright (c) 2016. Sten Martinez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

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
    private static Pattern emailPattern = Pattern.compile("^([a-z0-9\\+_\\-]+)(\\.[a-z0-9\\+_\\-]+)*@([a-z0-9\\-]+\\.)+[a-z]{2,6}$", Pattern.CASE_INSENSITIVE);
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

    public static boolean isNull(Long l) {
        return !isNotNull(l);
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
