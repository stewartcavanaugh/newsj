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

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Wrap array tools here in case library methods suck
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 11:28 AM
 */
public class ArrayUtil {
    public static long[] range(int from, int to) {
        if ( to < from ) {
            throw new IllegalArgumentException(String.format("to argument %s is less than from argument %s", to, from));
        }
        long [] array = new long[to - from + 1];
        int i = 0;
        for (int j = from; j <= to; j++) {
            array[i] = j;
            i++;
        }

        return array;
    }

    public static long[] range(long from, long to) {
        if ( to < from ) {
            throw new IllegalArgumentException(String.format("to argument %s is less than from argument %s", to, from));
        }
        long [] array = new long[(int) (to - from + 1)];
        int i = 0;
        for (long j = from; j <= to; j++) {
            array[i] = j;
            i++;
        }

        return array;
    }

    public static List<Long> rangeList(long from, long to) {
        if ( to < from ) {
            throw new IllegalArgumentException(String.format("to argument %s is less than from argument %s", to, from));
        }
        List<Long> array = new ArrayList<Long>((int) (to - from + 1));
        for (long j = from; j <= to; j++) {
            array.add( j);
        }

        return array;
    }

    public static List<Long> rangeList(int from, int to) {
        if ( to < from ) {
            throw new IllegalArgumentException(String.format("to argument %s is less than from argument %s", to, from));
        }
        List<Long> array = new ArrayList<>(to - from + 1);
        for (int j = from; j <= to; j++) {
            array.add((long) j);
        }

        return array;
    }

    public static Set<Long> rangeSet(long from, long to) {
        if ( to < from ) {
            throw new IllegalArgumentException(String.format("to argument %s is less than from argument %s", to, from));
        }
        Set<Long> hashSet = new HashSet<>((int) (to - from + 1));
        for (long j = from; j <= to; j++) {
            hashSet.add(j);
        }

        return hashSet;
    }

    public static long[] append(long[] array, long element) {
        return ArrayUtils.add(array, element);

    }

    public static <T> List<T> asList(T[] array) {
        ArrayList<T> list = new ArrayList<T>();
        Collections.addAll(list, array);

        return list;
    }

    public static <T> String stringify(Collection<T> collection) {
        return stringify(collection, ",");
    }

    public static <T> String stringify(Collection<T> collection, String delimiter) {
        StringBuilder sb = new StringBuilder();
        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next instanceof Object[]) {
                Object[] nextArr = (Object[]) next;
                for (Object o : nextArr) {
                    sb.append(o.toString()).append(",");
                }
                sb.append(delimiter);
            } else {
                sb.append(next.toString()).append(delimiter);
            }
        }
        return sb.toString();
    }

    public static String stringify(String[] array, String delimiter) {
        StringBuilder sb = new StringBuilder();
        if (array != null) {
            for (String s : array) {
                sb.append(s).append(delimiter);
            }
        }
        return sb.toString();
    }

    public static <T> List<T> paginate(List<T> list, int offset, int pageSize) {
        int arraySize = list.size();
        if (offset > arraySize) {
            return list;
        }

        int endIndex = offset + pageSize;
        if (endIndex >= arraySize) {
            return list.subList(offset, arraySize);
        } else {
            return list.subList(offset, endIndex);
        }
    }
}
