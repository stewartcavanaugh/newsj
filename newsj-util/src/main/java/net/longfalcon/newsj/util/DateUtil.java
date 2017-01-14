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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO: run through java DateFormat, then Joda, then use a natural language parser.
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 6:21 PM
 */
public class DateUtil {

    public static final DateTimeFormatter defaultDateFormat = DateTimeFormat.mediumDate();
    public static final DateTimeFormatter RFC_dateFormatter = DateTimeFormat.forPattern("dd MMM yyyy HH:mm:ss z");
    public static final DateTimeFormatter RFC_822_dateFormatter = DateTimeFormat.forPattern("E, dd MMM yyyy HH:mm:ss Z");
    public static final DateTimeFormatter displayDateFormatter = DateTimeFormat.forPattern("Y-M-d HH:mm:ss");
    public static final DateTimeFormatter airDateFormatter = DateTimeFormat.forPattern("Y-M-d");
    public static final DateTimeFormatter airDateFormatter_2 = DateTimeFormat.forPattern("Y-d-M");

    private static final Log _log = LogFactory.getLog(DateUtil.class);

    private static String[] _dateFormats = new String[]{
            "E, dd MMM yyyy HH:mm:ss Z",
            "E, dd MMM yyyy HH:mm:ss z",
            "dd MMM yyyy HH:mm:ss Z",
            "dd MMM yyyy HH:mm:ss z",
            "E, dd MMM yyyy HH:mm:ss Z (z)",
            "dd MMM yy HH:mm z",
            "dd MMM yy HH:mm Z"
    };

    public static DateTime parseNNTPDate(String dateString) {
        DateTime dateTime = null;

        try {
            dateTime = RFC_dateFormatter.parseDateTime(dateString);
        } catch (IllegalArgumentException e) {
            // do nothing
        }

        int i = 0;
        while(dateTime == null && i < _dateFormats.length) {
            try {
                DateTimeFormatter fmt = DateTimeFormat.forPattern(_dateFormats[i]);
                dateTime = fmt.parseDateTime(dateString);
            } catch (IllegalArgumentException e) {
                // do nothing
            }
            i++;
        }

        i = 0;
        while(dateTime == null && i < _dateFormats.length) {
            try {
                DateFormat javaFormatter = new SimpleDateFormat(_dateFormats[i]);
                Date date = javaFormatter.parse(dateString);
                dateTime = new DateTime(date);
            } catch (Exception e) {
                // do nothing
            }
            i++;
        }
        if (dateTime == null ) {
            _log.error(String.format("Unable to parse date string \'%s\'", dateString));
            dateTime = new DateTime();
        }
        return dateTime;
    }

    public static DateTime parseAirDate(String airDateString) {
        DateTime dateTime = null;

        try {
            dateTime = airDateFormatter.parseDateTime(airDateString);
        } catch (Exception e) {
            _log.debug(e.toString());
            try {
                dateTime = airDateFormatter_2.parseDateTime(airDateString);
            } catch (Exception e2) {
                _log.error(e2.toString());
            }
        }

        return dateTime;
    }

    public static String formatDefaultDate(Date date) {
        if (date == null) {
            return "Never";
        }
        return displayDateFormatter.print(new DateTime(date));
    }

    public static String formatNNTPDate(Date date) {
        return RFC_dateFormatter.print(date.getTime());
    }

    public static String formatDate(Date date, String format) {
        if (date == null) {
            return "Never";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
        return dateTimeFormatter.print(new DateTime(date));
    }
}
