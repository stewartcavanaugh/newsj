package net.longfalcon.newsj;

import net.longfalcon.newsj.util.ArrayUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * User: longfalcon
 * Date: 3/21/2016
 * Time: 5:38 PM
 */
public class ReleasesTest {

    String testReleaseName = "Regression.2015.German.AC3MD.HDRiP.XViD-HQX";

    Releases releases;

    @Before
    public void setUp() {
        releases = new Releases();
    }

    @Test
    public void testGetSimilarName() throws Exception {
        List<String> similarNameTokens = releases.getReleaseNameSearchTokens(testReleaseName);
        System.out.println("Search Similar Name : " + ArrayUtil.stringify(similarNameTokens, " "));
    }
}