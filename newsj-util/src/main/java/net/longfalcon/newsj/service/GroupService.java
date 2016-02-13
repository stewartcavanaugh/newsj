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
