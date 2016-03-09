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

package net.longfalcon.newsj;

import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.nntp.NntpConnectionFactory;
import net.longfalcon.newsj.nntp.client.NewsClient;
import net.longfalcon.newsj.nntp.client.NewsgroupInfo;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.persistence.PartDAO;
import net.longfalcon.newsj.persistence.PartRepairDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * User: Sten Martinez
 * Date: 2/29/16
 * Time: 3:52 PM
 */
@RunWith(MockitoJUnitRunner.class)
public class BinariesTest {
    private Binaries binaries;

    @Mock Backfill backfill;
    @Mock BinaryDAO binaryDAO;
    @Mock Blacklist blacklist;
    @Mock FetchBinaries fetchBinaries;
    @Mock GroupDAO groupDAO;
    @Mock NntpConnectionFactory nntpConnectionFactory;
    @Mock PlatformTransactionManager manager;
    @Mock PartRepairDAO partRepairDAO;
    @Mock PartDAO partDAO;

    @Mock
    NewsClient newsClient;
    @Mock
    Group group;
    String groupName = "test.group";

    @Test
    public void testUpdateGroup() throws Exception {

    }

    @Test
    public void testUpdateAllGroups() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
        binaries = new Binaries();
        binaries.setBackfill(backfill);
        binaries.setBinaryDAO(binaryDAO);
        binaries.setBlacklist(blacklist);
        binaries.setFetchBinaries(fetchBinaries);
        binaries.setGroupDAO(groupDAO);
        binaries.setNntpConnectionFactory(nntpConnectionFactory);
        binaries.setTransactionManager(manager);
        binaries.setPartRepairDAO(partRepairDAO);
        binaries.setPartDAO(partDAO);

        when(group.getName()).thenReturn(groupName);

        when(newsClient.selectNewsgroup(eq(groupName), any(NewsgroupInfo.class))).thenReturn(true);

    }
}