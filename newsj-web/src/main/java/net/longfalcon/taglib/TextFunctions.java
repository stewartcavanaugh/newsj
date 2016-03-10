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

package net.longfalcon.taglib;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 * User: Sten Martinez
 * Date: 2/29/16
 * Time: 9:25 AM
 */
public class TextFunctions {

    public static String truncate(String s, int length) {
        return StringUtils.abbreviate(s, length);
    }

    public static String escapeHtml(String s) {
        return StringEscapeUtils.escapeHtml4(s);
    }

    public static String wordWrap(String s, int width) {
        return WordUtils.wrap(s,width);
    }

    public static String formatFileSize(long bytes, boolean si) {
        // thanks stackoverflow
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String nl2br(String s) {
        s = s.replace("\r\n", "<br/>");
        s = s.replace("\r", "<br/>");
        s = s.replace("\n", "<br/>");
        return s;
    }
}
