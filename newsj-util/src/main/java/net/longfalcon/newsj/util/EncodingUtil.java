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

    public static String bytesToStringUTFCustom(byte[] bytes) {
        char[] buffer = new char[bytes.length >> 1];
        for(int i = 0; i < buffer.length; i++) {
            int bpos = i << 1;
            char c = (char)(((bytes[bpos]&0x00FF)<<8) + (bytes[bpos+1]&0x00FF));
            buffer[i] = c;
        }
        return new String(buffer);
    }

    public static String cp437toUTF(byte[] input) {

       /* // Create the encoder and decoder for cp437
        Charset charset = Charset.forName("ISO-8859-1");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        String s = "";
        try {
            // Convert a string to cp437 bytes in a ByteBuffer
            // The new ByteBuffer is ready to be read.
            ByteBuffer bbuf = ByteBuffer.wrap(input);

            // Convert cp437 bytes in a ByteBuffer to a character ByteBuffer and then to a string.
            // The new ByteBuffer is ready to be read.
            CharBuffer cbuf = decoder.decode(bbuf);
            s = cbuf.toString();
        } catch (CharacterCodingException e) {
            _log.error("Error converting cp437 to UTF", e);
        }*/

        StringBuilder stringBuilder = new StringBuilder();


        for (int i = 0; i < input.length; i++) {
            int b = input[i];
            if (b < 0) {
                b = b + 256;
            }
            char ch = (char) b;
            //System.out.print(ch);
            switch(ch){
                case 128: stringBuilder.append('Ç');break;
                case 129: stringBuilder.append('ü');break;
                case 130: stringBuilder.append('é');break;
                case 131: stringBuilder.append('â');break;
                case 132: stringBuilder.append('ä');break;
                case 133: stringBuilder.append('à');break;
                case 134: stringBuilder.append('å');break;
                case 135: stringBuilder.append('ç');break;
                case 136: stringBuilder.append('ê');break;
                case 137: stringBuilder.append('ë');break;
                case 138: stringBuilder.append('è');break;
                case 139: stringBuilder.append('ï');break;
                case 140: stringBuilder.append('î');break;
                case 141: stringBuilder.append('ì');break;
                case 142: stringBuilder.append('Ä');break;
                case 143: stringBuilder.append('Å');break;
                case 144: stringBuilder.append('É');break;
                case 145: stringBuilder.append('æ');break;
                case 146: stringBuilder.append('Æ');break;
                case 147: stringBuilder.append('ô');break;
                case 148: stringBuilder.append('ö');break;
                case 149: stringBuilder.append('ò');break;
                case 150: stringBuilder.append('û');break;
                case 151: stringBuilder.append('ù');break;
                case 152: stringBuilder.append('ÿ');break;
                case 153: stringBuilder.append('Ö');break;
                case 154: stringBuilder.append('Ü');break;
                case 155: stringBuilder.append('¢');break;
                case 156: stringBuilder.append('£');break;
                case 157: stringBuilder.append('¥');break;
                case 158: stringBuilder.append('₧');break;
                case 159: stringBuilder.append('ƒ');break;
                case 160: stringBuilder.append('á');break;
                case 161: stringBuilder.append('í');break;
                case 162: stringBuilder.append('ó');break;
                case 163: stringBuilder.append('ú');break;
                case 164: stringBuilder.append('ñ');break;
                case 165: stringBuilder.append('Ñ');break;
                case 166: stringBuilder.append('ª');break;
                case 167: stringBuilder.append('º');break;
                case 168: stringBuilder.append('¿');break;
                case 169: stringBuilder.append('⌐');break;
                case 170: stringBuilder.append('¬');break;
                case 171: stringBuilder.append('½');break;
                case 172: stringBuilder.append('¼');break;
                case 173: stringBuilder.append('¡');break;
                case 174: stringBuilder.append('«');break;
                case 175: stringBuilder.append('»');break;
                case 176: stringBuilder.append('░');break;
                case 177: stringBuilder.append('▒');break;
                case 178: stringBuilder.append('▓');break;
                case 179: stringBuilder.append('│');break;
                case 180: stringBuilder.append('┤');break;
                case 181: stringBuilder.append('╡');break;
                case 182: stringBuilder.append('╢');break;
                case 183: stringBuilder.append('╖');break;
                case 184: stringBuilder.append('╕');break;
                case 185: stringBuilder.append('╣');break;
                case 186: stringBuilder.append('║');break;
                case 187: stringBuilder.append('╗');break;
                case 188: stringBuilder.append('╝');break;
                case 189: stringBuilder.append('╜');break;
                case 190: stringBuilder.append('╛');break;
                case 191: stringBuilder.append('┐');break;
                case 192: stringBuilder.append('└');break;
                case 193: stringBuilder.append('┴');break;
                case 194: stringBuilder.append('┬');break;
                case 195: stringBuilder.append('├');break;
                case 196: stringBuilder.append('─');break;
                case 197: stringBuilder.append('┼');break;
                case 198: stringBuilder.append('╞');break;
                case 199: stringBuilder.append('╟');break;
                case 200: stringBuilder.append('╚');break;
                case 201: stringBuilder.append('╔');break;
                case 202: stringBuilder.append('╩');break;
                case 203: stringBuilder.append('╦');break;
                case 204: stringBuilder.append('╠');break;
                case 205: stringBuilder.append('═');break;
                case 206: stringBuilder.append('╬');break;
                case 207: stringBuilder.append('╧');break;
                case 208: stringBuilder.append('╨');break;
                case 209: stringBuilder.append('╤');break;
                case 210: stringBuilder.append('╥');break;
                case 211: stringBuilder.append('╙');break;
                case 212: stringBuilder.append('╘');break;
                case 213: stringBuilder.append('╒');break;
                case 214: stringBuilder.append('╓');break;
                case 215: stringBuilder.append('╫');break;
                case 216: stringBuilder.append('╪');break;
                case 217: stringBuilder.append('┘');break;
                case 218: stringBuilder.append('┌');break;
                case 219: stringBuilder.append('█');break;
                case 220: stringBuilder.append('▄');break;
                case 221: stringBuilder.append('▌');break;
                case 222: stringBuilder.append('▐');break;
                case 223: stringBuilder.append('▀');break;
                case 224: stringBuilder.append('α');break;
                case 225: stringBuilder.append('ß');break;
                case 226: stringBuilder.append('Γ');break;
                case 227: stringBuilder.append('π');break;
                case 228: stringBuilder.append('Σ');break;
                case 229: stringBuilder.append('σ');break;
                case 230: stringBuilder.append('µ');break;
                case 231: stringBuilder.append('τ');break;
                case 232: stringBuilder.append('Φ');break;
                case 233: stringBuilder.append('Θ');break;
                case 234: stringBuilder.append('Ω');break;
                case 235: stringBuilder.append('δ');break;
                case 236: stringBuilder.append('∞');break;
                case 237: stringBuilder.append('φ');break;
                case 238: stringBuilder.append('ε');break;
                case 239: stringBuilder.append('∩');break;
                case 240: stringBuilder.append('≡');break;
                case 241: stringBuilder.append('±');break;
                case 242: stringBuilder.append('≥');break;
                case 243: stringBuilder.append('≤');break;
                case 244: stringBuilder.append('⌠');break;
                case 245: stringBuilder.append('⌡');break;
                case 246: stringBuilder.append('÷');break;
                case 247: stringBuilder.append('≈');break;
                case 248: stringBuilder.append('°');break;
                case 249: stringBuilder.append('∙');break;
                case 250: stringBuilder.append('·');break;
                case 251: stringBuilder.append('√');break;
                case 252: stringBuilder.append('ⁿ');break;
                case 253: stringBuilder.append('²');break;
                case 254: stringBuilder.append('■');break;
                case 255: stringBuilder.append(' ');break;
                default :
                    stringBuilder.append(ch);
            }
        }

        return stringBuilder.toString();
    }
}
