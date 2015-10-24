package net.longfalcon.newsj.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: Sten Martinez
 * Date: 10/23/15
 * Time: 10:36 PM
 */
public class ValidatorUtilTest {

    @Test
    public void testIsNumeric() {
        Assert.assertTrue(ValidatorUtil.isNumeric("123123"));
        Assert.assertTrue(ValidatorUtil.isNumeric("2"));
        Assert.assertTrue(ValidatorUtil.isNumeric("-1"));
        Assert.assertFalse(ValidatorUtil.isNumeric("123123s"));
        Assert.assertFalse(ValidatorUtil.isNumeric("test"));
    }

}