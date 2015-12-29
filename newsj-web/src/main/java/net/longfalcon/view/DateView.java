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
