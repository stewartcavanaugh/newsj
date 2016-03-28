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

package net.longfalcon.newsj.nntp.client;

import org.apache.commons.net.io.DotTerminatedMessageReader;
import org.apache.commons.net.io.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Wraps a {@link BufferedReader} and returns an {@code Iterable<String>}
 * which returns the individual lines from the reader.
 * @since 3.0
 */
class ReplyIterator implements Iterator<String>, Iterable<String> {

    private final BufferedReader reader;

    private String line;

    private Exception savedException;

    /**
     *
     * @param _reader the reader to wrap
     * @param addDotReader whether to additionally wrap the reader in a DotTerminatedMessageReader
     * @throws IOException
     */
    ReplyIterator(BufferedReader _reader, boolean addDotReader) throws IOException {
        reader = addDotReader ? new DotTerminatedMessageReader(_reader) : _reader;
        line = reader.readLine(); // prime the iterator
        if (line == null) {
            Util.closeQuietly(reader);
        }
    }

    ReplyIterator(BufferedReader _reader) throws IOException {
        this(_reader, true);
    }

    public boolean hasNext() {
        if (savedException != null){
            throw new NoSuchElementException(savedException.toString());
        }
        return line != null;
    }

    public String next() throws NoSuchElementException {
        if (savedException != null){
            throw new NoSuchElementException(savedException.toString());
        }
        String prev = line;
        if (prev == null) {
            throw new NoSuchElementException();
        }
        try {
            line = reader.readLine(); // save next line
            if (line == null) {
                Util.closeQuietly(reader);
            }
        } catch (IOException ex) {
            savedException = ex; // if it fails, save the exception, as it does not apply to this call
            Util.closeQuietly(reader);
        }
        return prev;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public Iterator<String> iterator() {
        return this;
    }
}
