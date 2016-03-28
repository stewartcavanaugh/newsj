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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: Sten Martinez
 * Date: 10/15/15
 * Time: 10:20 PM
 */
public class StreamUtil {
    public static void transferByteArray(InputStream inputStream, OutputStream outputStream, int bufferSize) throws IOException {
        byte[] bytes = new byte[bufferSize];

        int value = -1;

        while ((value = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, value);
        }
    }
}
