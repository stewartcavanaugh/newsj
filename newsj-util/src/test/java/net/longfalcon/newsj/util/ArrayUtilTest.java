package net.longfalcon.newsj.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 11:48 AM
 */
public class ArrayUtilTest {

    static long[] correctArray1 = {1,2,3,4,5};

    @Test
    public void testRange() throws Exception {
        long[] array = ArrayUtil.range(1, 5);
        Assert.assertArrayEquals(correctArray1, array);
    }
}