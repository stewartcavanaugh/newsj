/*
 * Copyright (c) 2016. Sten Martinez
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

import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.test.BaseFsTestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

/**
 * User: Sten Martinez
 * Date: 4/30/16
 * Time: 5:40 PM
 */
public class BinaryDAOImplTest extends BaseFsTestSupport {

    @Autowired
    BinaryDAO binaryDAO;

    @Test
    @Sql("/sql/binaries/binaries-test-data.sql")
    public void testFindByBinaryHash() throws Exception {
        Binary binary = binaryDAO.findByBinaryHash("abcd1234");
        Assert.assertNotNull(binary);
    }
}