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

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * User: Sten Martinez
 * Date: 3/2/16
 * Time: 5:15 PM
 */
public class DateFunctionsTest {

    @Test
    public void testTimeAgo() throws Exception {
        DateTime now = new DateTime();
        DateTime oneHourAgo = now.minusHours(1);
        DateTime threeDaysAgo = now.minusDays(3);
        DateTime eightDaysAgo = now.minusDays(8);
        DateTime fourMonthsAgo = now.minusMonths(4);

        Assert.assertEquals("now",DateFunctions.timeAgo(now.toDate()));
        Assert.assertEquals("1 Hour" , DateFunctions.timeAgo(oneHourAgo.toDate()));
        Assert.assertEquals("3d" , DateFunctions.timeAgo(threeDaysAgo.toDate()));
        Assert.assertEquals("8d" , DateFunctions.timeAgo(eightDaysAgo.toDate()));
        Assert.assertEquals("17 Wks" , DateFunctions.timeAgo(fourMonthsAgo.toDate()));
    }
}