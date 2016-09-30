package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.test.BaseFsTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/14/16
 * Time: 10:48 PM
 */
public class ReleaseDAOTest extends BaseFsTestSupport {

    @Autowired
    ReleaseDAO releaseDAO;

    @Test
    @Sql("/sql/release/release-dao-test-data.sql")
    public void testGetReleases() {
        List<Release> releases = releaseDAO.getReleases(0,50);
        List<Integer> categoryIds = new ArrayList<>(1);
        categoryIds.add(2030);
        long releaseCount = releaseDAO.countByCategoriesMaxAgeAndGroup(categoryIds, null, null, null);

        Assert.assertTrue(releases.get(0).getReleaseNfo().getId() == 1);
        Assert.assertEquals(1, releaseCount);
    }
}
