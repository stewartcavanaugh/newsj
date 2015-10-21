package net.longfalcon.newsj.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: Sten Martinez
 * Date: 10/13/15
 * Time: 11:22 AM
 */
public class EncodingUtilTest {

    @Test
    public void testMd5Hash() throws Exception {
        String test1 = EncodingUtil.md5Hash("test");
        String test2 = EncodingUtil.md5Hash("test");
        Assert.assertEquals(test1, test2);
    }
}