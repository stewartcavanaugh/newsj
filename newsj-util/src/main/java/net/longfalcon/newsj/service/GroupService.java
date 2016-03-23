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

import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Sten Martinez
 * Date: 12/18/15
 */
@Service
public class GroupService {
    private static final Log _log = LogFactory.getLog(GroupService.class);

    private GroupDAO groupDAO;
    private ReleaseDAO releaseDAO;
    private BinaryDAO binaryDAO;

    @Transactional
    public void delete(int groupId) {
        Group group = groupDAO.findGroupByGroupId(groupId);
        groupDAO.delete(group);
    }

    public BinaryDAO getBinaryDAO() {
        return binaryDAO;
    }

    public void setBinaryDAO(BinaryDAO binaryDAO) {
        this.binaryDAO = binaryDAO;
    }

    public Group getGroup(long groupId) {
        return getGroupDAO().findGroupByGroupId(groupId);
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public ReleaseDAO getReleaseDAO() {
        return releaseDAO;
    }

    public void setReleaseDAO(ReleaseDAO releaseDAO) {
        this.releaseDAO = releaseDAO;
    }

    public long getReleasesCount(Group group) {
        return releaseDAO.countByGroupId(group.getId());
    }

    @Transactional
    public void purge(int groupId) {
        releaseDAO.deleteByGroupId(groupId);
        binaryDAO.deleteByGroupId(groupId);
        reset(groupId);
    }

    @Transactional
    public void reset(int groupId) {
        Group group = groupDAO.findGroupByGroupId(groupId);
        group.setBackfillTarget(0);
        group.setFirstRecord(0);
        group.setFirstRecordPostdate(null);
        group.setLastRecord(0);
        group.setLastRecordPostdate(null);
        group.setLastUpdated(null);
        groupDAO.update(group);
    }

    @Transactional
    public void update(Group group) {
        groupDAO.update(group);
    }

    @Transactional
    public void updateGroupStatus(long groupId, boolean active) {
        Group group = groupDAO.findGroupByGroupId(groupId);
        group.setActive(active);
        groupDAO.update(group);
    }
}
