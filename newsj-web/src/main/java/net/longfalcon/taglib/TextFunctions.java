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

import net.longfalcon.newsj.util.EncodingUtil;
import net.longfalcon.newsj.util.FormatUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import javax.servlet.jsp.jstl.core.LoopTagStatus;

/**
 * User: Sten Martinez
 * Date: 2/29/16
 * Time: 9:25 AM
 */
public class TextFunctions {

    public static String cycle(LoopTagStatus loopTagStatus, String s1, String s2) {
        String[] strings = new String[] {s1,s2};
        return cycle(loopTagStatus, strings);
    }

    public static String cycle(LoopTagStatus loopTagStatus, String ... strings) {
        int cycle = loopTagStatus.getCount() % strings.length;
        return (strings[cycle]);
    }

    public static String escapeHtml(String s) {
        return StringEscapeUtils.escapeHtml4(s);
    }

    public static String formatFileSize(long bytes, boolean si) {
        return FormatUtil.formatFileSize(bytes, si);
    }

    public static boolean isNull(String s) {
        return ValidatorUtil.isNull(s);
    }

    public static String nl2br(String s) {
        s = s.replace("\r\n", "<br/>");
        s = s.replace("\r", "<br/>");
        s = s.replace("\n", "<br/>");
        return s;
    }

    public static String replace(String s, String oldStr, String newStr) {
        return StringUtils.replace(s, oldStr, newStr);
    }

    public static String truncate(String s, int length) {
        return StringUtils.abbreviate(s, length);
    }

    public static String urlEncode(String s) {
        return EncodingUtil.urlEncode(s);
    }

    public static String wordWrap(String s, int width) {
        return WordUtils.wrap(s, width);
    }

    public static String formatImdbId(Integer imdbId) {
        return String.format("%07d", imdbId);
    }
}
