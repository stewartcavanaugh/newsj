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

package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.ReleaseComment;
import net.longfalcon.newsj.persistence.ReleaseCommentDAO;
import net.longfalcon.newsj.test.BaseFsTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/9/16
 * Time: 4:50 PM
 */
@Sql("/sql/releasecomment/releasecomment-dao-test-data.sql")
public class ReleaseCommentDAOImplTest extends BaseFsTestSupport {

    @Autowired
    ReleaseCommentDAO releaseCommentDAO;

    @Test
    public void testGetReleaseComments() throws Exception {
        List<ReleaseComment> releaseComments = releaseCommentDAO.getReleaseComments(0,50);
        Assert.assertTrue(releaseComments.size() == 1);
        ReleaseComment releaseComment = releaseComments.get(0);
        Assert.assertEquals("testadmin", releaseComment.getUser().getUsername());
        Assert.assertEquals(2L, releaseComment.getRelease().getId());
    }

    @Test
    public void testFindByReleaseId() throws Exception {
        List<ReleaseComment> releaseComments = releaseCommentDAO.findByReleaseId(2L);
        Assert.assertTrue(releaseComments.size() == 1);
        ReleaseComment releaseComment = releaseComments.get(0);
        Assert.assertEquals("testadmin", releaseComment.getUser().getUsername());
        Assert.assertEquals(2L, releaseComment.getRelease().getId());
    }

    @Test
    public void testFindByReleaseCommentId() throws Exception {
        ReleaseComment releaseComment = releaseCommentDAO.findByReleaseCommentId(1);
        Assert.assertEquals("testadmin", releaseComment.getUser().getUsername());
        Assert.assertEquals(2L, releaseComment.getRelease().getId());
    }

    @Test
    public void testCountReleaseComments() throws Exception {
        long count = releaseCommentDAO.countReleaseComments();
        Assert.assertEquals(1, 1);
    }
}