package net.longfalcon.newsj.util;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * User: Sten Martinez
 * Date: 10/8/15
 * Time: 4:57 PM
 */
public class DateUtilTest {

    @Test
    public void testParseNNTPDate() throws Exception {
        DateTime dateTime = DateUtil.parseNNTPDate("Tue, 06 Oct 2015 01:54:02 +0200 (UTC)");
    }
}