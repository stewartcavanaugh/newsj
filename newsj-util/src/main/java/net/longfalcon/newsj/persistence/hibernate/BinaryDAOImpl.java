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

import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.MatchedReleaseQuery;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 11:08 PM
 */
@Repository
public class BinaryDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.BinaryDAO {

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Binary> findByReleaseId(long releaseId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("releaseId", releaseId));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public Binary findByBinaryHash(String binaryHash) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("binaryHash", binaryHash));

        return (Binary) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Binary> findByGroupIdsAndProcStat(Collection<Long> groupIds, int procStat) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.in("groupId", groupIds));
        criteria.add(Restrictions.eq("procStat", procStat));
        criteria.addOrder(Order.asc("date"));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<MatchedReleaseQuery> findBinariesByProcStatAndTotalParts(int procstat) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("procStat", procstat));
        criteria.setProjection(Projections.projectionList()
                        .add(Projections.rowCount(), "numberOfBinaries")
                        .add(Projections.groupProperty("groupId").as("group"))
                        .add(Projections.groupProperty("reqId").as("reqId"))
                        .add(Projections.groupProperty("relName").as("releaseName"))
                        .add(Projections.groupProperty("relTotalPart").as("releaseTotalParts"))
                        .add(Projections.groupProperty("fromName").as("fromName"))
        );
        criteria.setResultTransformer(Transformers.aliasToBean(MatchedReleaseQuery.class));
        return criteria.list();
    }

    @Override
    @Transactional
    public void updateBinary(Binary binary) {
        sessionFactory.getCurrentSession().saveOrUpdate(binary);
        //sessionFactory.getCurrentSession().flush();
    }

    @Override
    @Transactional
    public void deleteBinary(Binary binary) {
        sessionFactory.getCurrentSession().delete(binary);
    }

    @Override
    @Transactional
    public void deleteBinaryByDate(Date before) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete Binary b where b.dateAdded < :before");
        query.setParameter("before", before);

        query.executeUpdate();
    }

    @Override
    @Transactional
    public void updateBinaryIncrementProcAttempts(String relName, int procStat, long groupId, String fromName) {
        Query query =
                sessionFactory.getCurrentSession().createQuery("update Binary b set b.procAttempts = b.procAttempts + 1 " +
                        "where b.relName = :name and b.procStat = :procstat and b.groupId = :groupId and b.fromName = :fromname");
        query.setParameter("name", relName);
        query.setParameter("procstat", procStat);
        query.setParameter("groupId", groupId);
        query.setParameter("fromname", fromName);

        query.executeUpdate();
    }


    @Override
    @Transactional
    public void updateBinaryNameAndStatus(String newName, int newStatus, String relName, int procStat, long groupId, String fromName) {
        Query query =
                sessionFactory.getCurrentSession().createQuery("update Binary b set b.relName = :newname, b.procStat = :newstatus " +
                        "where b.relName = :name and b.procStat = :procstat and b.groupId = :groupId and b.fromName = :fromname");
        query.setParameter("newname", newName);
        query.setParameter("newstatus", newStatus);
        query.setParameter("name", relName);
        query.setParameter("procstat", procStat);
        query.setParameter("groupId", groupId);
        query.setParameter("fromname", fromName);

        query.executeUpdate();
    }

    @Override
    @Transactional
    public void updateBinaryNameStatusReleaseID(String newName, int newStatus, long newReleaseId, String relName, int procStat, long groupId, String fromName) {
        Query query =
                sessionFactory.getCurrentSession().createQuery("update Binary b set b.relName = :newname, b.procStat = :newstatus, b.releaseId = :newrelid " +
                        "where b.relName = :name and b.procStat = :procstat and b.groupId = :groupId and b.fromName = :fromname");
        query.setParameter("newname", newName);
        query.setParameter("newstatus", newStatus);
        query.setParameter("newrelid", newReleaseId);
        query.setParameter("name", relName);
        query.setParameter("procstat", procStat);
        query.setParameter("groupId", groupId);
        query.setParameter("fromname", fromName);

        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Binary> findBinariesByReleaseNameProcStatGroupIdFromName(String relName, int procStat, long groupId, String fromName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("relName", relName));
        criteria.add(Restrictions.eq("procStat", procStat));
        criteria.add(Restrictions.eq("groupId", groupId));
        criteria.add(Restrictions.eq("fromName", fromName));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public Timestamp findMaxDateAddedBinaryByReleaseNameProcStatGroupIdFromName(String relName, int procStat, long groupId, String fromName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("relName", relName));
        criteria.add(Restrictions.eq("procStat", procStat));
        criteria.add(Restrictions.eq("groupId", groupId));
        criteria.add(Restrictions.eq("fromName", fromName));
        criteria.setProjection(Projections.max("dateAdded"));

        return (Timestamp) criteria.uniqueResult();
    }

    @Override
    @Transactional
    public void updateProcStatByProcStatAndDate(int newStatus, int procStat, Date before) {
        Query query =
                sessionFactory.getCurrentSession().createQuery("update Binary b set b.procStat = :newstatus " +
                        "where b.procStat = :procstat and b.dateAdded < :beforeDate");
        query.setParameter("newstatus", newStatus);
        query.setParameter("procstat", procStat);
        query.setParameter("beforeDate", before);

        query.executeUpdate();
    }

    @Override
    @Transactional
    public void resetReleaseBinaries(long releaseId) {
        Query query =
                sessionFactory.getCurrentSession().createQuery("update Binary b set b.procStat = :procstat, " +
                        "b.procAttempts = :procAttempts, b.categoryId = :categoryId, b.regexId = :regexId, " +
                        "b.reqId = :reqId, b.relPart = :relPart, b.relTotalPart = :relTotalPart, b.relName = :relName, " +
                        "b.releaseId = :newReleaseId " +
                        "where b.releaseId = :releaseId");
        query.setParameter("releaseId", releaseId);
        query.setParameter("procstat", 0);
        query.setParameter("procAttempts", 0);
        query.setParameter("categoryId", null);
        query.setParameter("regexId", null);
        query.setParameter("reqId", null);
        query.setParameter("relPart", null);
        query.setParameter("relTotalPart", null);
        query.setParameter("relName", null);
        query.setParameter("newReleaseId", null);

        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Binary> findBinariesByReleaseId(long releaseId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("releaseId", releaseId));
        criteria.addOrder(Order.asc("name"));
        criteria.addOrder(Order.asc("relPart"));

        return criteria.list();
    }

    @Override
    @Transactional
    public void deleteByGroupId(long groupId) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from Binary b where b.groupId = :group_id");
        query.setParameter("group_id", groupId);

        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Long> findBinaryIdsByGroupId(long groupId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("groupId", groupId));
        criteria.setProjection(Projections.property("id"));

        return criteria.list();
    }

    /**
     * Find groups based on groupid, optional list of procstats and a required release id
     * @param groupId required group id
     * @param procStats optional procstats that the binary could have, leave null for all
     * @param releaseId required releaseid, null means the releaseid must be null!, 0 for all
     * @return
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Binary> findByGroupIdProcStatsReleaseId(long groupId, List<Integer> procStats, Long releaseId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("groupId", groupId));
        if (procStats != null && !procStats.isEmpty()) {
            criteria.add(Restrictions.in("procStat", procStats));
        }
        if (releaseId == null) {
            criteria.add(Restrictions.isNull("releaseId"));
        } else if (releaseId > 0) {
            criteria.add(Restrictions.eq("releaseId", releaseId));
        }
        criteria.addOrder(Order.asc("date"));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Binary> searchByNameAndExcludedCats(String[] searchTokens, int limit, Collection<Integer> excludedCategoryIds) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);

        if (searchTokens != null && searchTokens.length > 0) {
            Conjunction searchTokensOr = Restrictions.conjunction();
            for (String searchToken : searchTokens) {
                searchTokensOr.add(Restrictions.like("name", searchToken.trim(), MatchMode.ANYWHERE));
            }
            criteria.add(searchTokensOr);
        }

        if (excludedCategoryIds != null && !excludedCategoryIds.isEmpty()) {
            criteria.add(Restrictions.not(Restrictions.in("categoryId", excludedCategoryIds)));
        }

        criteria.setMaxResults(limit);
        criteria.setFetchMode("releaseGuid", FetchMode.EAGER);
        criteria.setFetchMode("numberParts", FetchMode.EAGER);
        criteria.setFetchMode("groupName", FetchMode.EAGER);

        return criteria.list();
    }
}
