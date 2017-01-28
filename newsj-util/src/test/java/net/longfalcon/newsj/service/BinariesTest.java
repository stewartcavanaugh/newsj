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

package net.longfalcon.newsj.service;

import net.longfalcon.newsj.Binaries;
import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.PartRepair;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.persistence.PartDAO;
import net.longfalcon.newsj.persistence.PartRepairDAO;
import net.longfalcon.newsj.test.BaseFsTestSupport;
import net.longfalcon.newsj.util.Defaults;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/8/16
 * Time: 12:32 PM
 */
public class BinariesTest extends BaseFsTestSupport {
    @Autowired
    Binaries binaries;

    @Autowired
    BinaryDAO binaryDAO;

    @Autowired
    PartDAO partDAO;

    @Autowired
    PartRepairDAO partRepairDAO;

    @Test
    @Ignore
    public void testUpdateGroups() {
        binaries.updateAllGroups();

        Collection<Long> groupMatch = new ArrayList<>();
        groupMatch.add(0L);
        List<Binary> binaries = binaryDAO.findByGroupIdsAndProcStat(groupMatch, Defaults.PROCSTAT_NEW);
        Assert.assertEquals(2, binaries.size());
    }

    @Test
    @Ignore
    @Sql("/sql/partrepair/binaries-partrepair-test-data.sql")
    public void testPartRepair() {
        binaries.updateAllGroups();

        Collection<Long> groupMatch = new ArrayList<>();
        groupMatch.add(0L);
        List<Binary> binaries = binaryDAO.findByGroupIdsAndProcStat(groupMatch, Defaults.PROCSTAT_NEW);
        Assert.assertEquals(1, binaries.size());

        List<PartRepair> partRepairs = partRepairDAO.findByGroupIdAndAttempts(0,5,true);
        Assert.assertEquals(0, partRepairs.size());

        long parts = partDAO.countPartsByBinaryId(1);
        Assert.assertEquals(1, parts);
    }
}
