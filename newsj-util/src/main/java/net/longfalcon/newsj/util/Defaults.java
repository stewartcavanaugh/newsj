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

import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 6:17 PM
 */
public class Defaults {
    public static final Pattern PARTS_SUBJECT_REGEX = Pattern.compile("\\((\\d+)\\/(\\d+)\\)$");

    //
    // initial binary state after being added from usenet
    public static final int PROCSTAT_NEW = 0;

    //
    // after a binary has matched a releaseregex
    public static final int PROCSTAT_TITLEMATCHED = 5;

    //
    // after a binary has been confirmed as having the right number of parts
    public static final int PROCSTAT_READYTORELEASE = 1;

    //
    // after a binary has has been attempted to be matched for x days and 
    // still has the wrong number of parts
    public static final int PROCSTAT_WRONGPARTS = 2;

    //
    // binary that has finished and successfully made it into a release
    public static final int PROCSTAT_RELEASED = 4;

    //
    // binary that is identified as already being part of another release 
    //(with the same name posted in a similar date range)
    public static final int PROCSTAT_DUPLICATE = 6;

    //
    // after a series of attempts to lookup the allfilled style reqid
    // to get a name, its given up
    public static final int PROCSTAT_NOREQIDNAMELOOKUPFOUND = 7;

    //
    // passworded indicator
    //
    public static final int PASSWD_NONE = 0;
    public static final int PASSWD_RAR = 1;
    public static final int PASSWD_POTENTIAL = 2;

    //
    // Blacklist Field indexes
    //
    public static final int BLACKLIST_FIELD_SUBJECT = 1;
    public static final int BLACKLIST_FIELD_FROM = 2;
    public static final int BLACKLIST_FIELD_MESSAGEID = 3;
}
