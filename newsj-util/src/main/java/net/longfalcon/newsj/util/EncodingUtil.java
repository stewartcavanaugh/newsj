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
    private static MessageDigest _sha1Digest = null;
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

    /**
     * TODO: move to better algorithm?
     * @param input
     * @return
     */
    public static String sha1Hash(String input) {
        if (_sha1Digest == null) {
            try {
                _sha1Digest = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException e) {
                // this will not fail.
                e.printStackTrace();
                return null;
            }
        }

        byte[] digest = _sha1Digest.digest(input.getBytes());

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
