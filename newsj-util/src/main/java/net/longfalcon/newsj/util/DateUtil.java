package net.longfalcon.newsj.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * TODO: run through java DateFormat, then Joda, then use a natural language parser.
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 6:21 PM
 */
public class DateUtil {

    public static final DateTimeFormatter RFC_dateFormatter = DateTimeFormat.forPattern("dd MMM yyyy HH:mm:ss z");
    public static final DateTimeFormatter displayDateFormatter = DateTimeFormat.forPattern("Y-M-d H:m:s");
    public static final DateTimeFormatter airDateFormatter = DateTimeFormat.forPattern("Y-M-d");
    public static final DateTimeFormatter airDateFormatter_2 = DateTimeFormat.forPattern("Y-d-M");

    private static final Log _log = LogFactory.getLog(DateUtil.class);

    private static String[] _dateFormats = new String[]{
            "E, dd MMM yyyy HH:mm:ss Z",
            "E, dd MMM yyyy HH:mm:ss z",
            "dd MMM yyyy HH:mm:ss Z",
            "dd MMM yyyy HH:mm:ss z",
            "E, dd MMM yyyy HH:mm:ss Z (z)"
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
        if (dateTime == null ) {
            _log.error(String.format("Unable to parse date string \'%s\'", dateString));
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
}
