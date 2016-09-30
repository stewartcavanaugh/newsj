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

package net.longfalcon.web.http.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User: Sten Martinez
 * Date: 3/28/16
 * Time: 5:57 PM
 */
public class CustomByteArrayHttpMessageConverter  extends AbstractHttpMessageConverter<byte[]> {

    /**
     * Creates a new instance of the {@code ByteArrayHttpMessageConverter}.
     */
    public CustomByteArrayHttpMessageConverter() {
        super(new MediaType("application", "x-nzb"), MediaType.ALL);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return byte[].class.equals(clazz);
    }

    @Override
    public byte[] readInternal(Class<? extends byte[]> clazz, HttpInputMessage inputMessage) throws IOException {
        long contentLength = inputMessage.getHeaders().getContentLength();
        ByteArrayOutputStream bos =
                new ByteArrayOutputStream(contentLength >= 0 ? (int) contentLength : StreamUtils.BUFFER_SIZE);
        StreamUtils.copy(inputMessage.getBody(), bos);
        return bos.toByteArray();
    }

    @Override
    protected Long getContentLength(byte[] bytes, MediaType contentType) {
        return (long) bytes.length;
    }

    @Override
    protected void writeInternal(byte[] bytes, HttpOutputMessage outputMessage) throws IOException {
        StreamUtils.copy(bytes, outputMessage.getBody());
    }
}
