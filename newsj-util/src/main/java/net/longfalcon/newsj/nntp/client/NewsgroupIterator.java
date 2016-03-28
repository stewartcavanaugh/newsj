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

import java.util.Iterator;

/**
 * Copied from commons-net class NewsgroupIterator v3.2
 * Class which wraps an {@code Iterable<String>} of raw newgroup information
 * to generate an {@code Iterable<NewsgroupInfo>} of the parsed information.
 * @since 3.0
 */
class NewsgroupIterator implements Iterator<NewsgroupInfo>, Iterable<NewsgroupInfo> {

    private  final Iterator<String> stringIterator;

    public NewsgroupIterator(Iterable<String> iterableString) {
        stringIterator = iterableString.iterator();
    }

    public boolean hasNext() {
        return stringIterator.hasNext();
    }

    public NewsgroupInfo next() {
        String line = stringIterator.next();
        return CustomNNTPClient.__parseNewsgroupListEntry(line);
    }

    public void remove() {
        stringIterator.remove();
    }

    public Iterator<NewsgroupInfo> iterator() {
        return this;
    }
}

