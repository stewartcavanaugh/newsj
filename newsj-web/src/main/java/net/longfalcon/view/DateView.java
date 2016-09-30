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

package net.longfalcon.view;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.util.Date;

/**
 * Created by longfalcon on 12/20/15.
 */
public class DateView {

    private static PeriodFormatter _periodFormatter;

    static {
        _periodFormatter = PeriodFormat.wordBased();
    }

    public String timeAgo(Date date) {
        if (date != null) {
            Date now = new Date();
            Period period = new Period(date.getTime(), now.getTime());
            period = roundPeriod(period);

            return _periodFormatter.print(period);
        } else {
            return "never";
        }
    }

    public Period roundPeriod(Period period) {
        int fieldCount = 0;
        int years = period.getYears();
        int months = period.getMonths();
        int weeks = period.getWeeks();
        int days = period.getDays();
        int hours = period.getHours();
        int minutes = period.getMinutes();
        int seconds = period.getSeconds();
        if (years > 0) {
            fieldCount++;
        }
        if (months > 0) {
            fieldCount++;
        }

        if (fieldCount > 1) {
            return new Period(years, months, 0, 0, 0, 0, 0, 0);
        }

        if (weeks > 0) {
            fieldCount++;
        }

        if (fieldCount > 1) {
            return new Period(0, months, weeks, 0, 0, 0, 0, 0);
        }

        if (days > 0) {
            fieldCount++;
        }

        if (fieldCount > 1) {
            return new Period(0, 0, weeks, days, 0, 0, 0, 0);
        }

        if (hours > 0) {
            fieldCount++;
        }

        if (fieldCount > 1) {
            return new Period(0, 0, 0, days, hours, 0, 0, 0);
        }

        if (minutes > 0) {
            fieldCount++;
        }

        if (fieldCount > 1) {
            return new Period(0, 0, 0, 0, hours, minutes, 0, 0);
        }

        return new Period(0, 0, 0, 0, 0, minutes, seconds, 0);
    }
}
