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

import net.longfalcon.newsj.util.DateUtil;

import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 3/2/16
 * Time: 4:59 PM
 */
public class DateFunctions {

    private static final String N_A = "n/a";
    private static final String[] TIME_STRINGS = { "now",      // 0
                                                    "Sec", "Secs",    // 1,1
                                                    "Min","Mins",     // 3,3
                                                    "Hour", "Hrs",  // 5,5
                                                    "Day", "Days"};

    public static String timeAgo(Date date) {
        if (date == null) {
            return N_A;
        }

        long millis = System.currentTimeMillis() - date.getTime();
        long seconds = millis / 1000;

        if (seconds <= 0) {
            return TIME_STRINGS[0];
        }

        if (seconds < 2) {
            return seconds + " " + TIME_STRINGS[1];
        }

        if (seconds < 60) {
            return seconds + " " + TIME_STRINGS[2];
        }

        long minutes = seconds / 60;

        if ( minutes < 2) {
            return minutes + " " + TIME_STRINGS[3];
        }

        if ( minutes < 60) {
            return minutes + " " + TIME_STRINGS[4];
        }

        long hours = minutes / 60;

        if ( hours < 2) {
            return hours + " " + TIME_STRINGS[5];
        }

        if ( hours < 24) {
            return hours + " " + TIME_STRINGS[6];
        }

        long days = seconds / 60 / 60 / 24;

        if (days > 365) {
            return days/365 + " Yrs";
        } else if (days > 90) {
            return days/7 + " Wks";
        }

        return days + "d";
    }


    public static String dateFormat(Date date) {

        return DateUtil.formatDefaultDate(date);
    }
}
