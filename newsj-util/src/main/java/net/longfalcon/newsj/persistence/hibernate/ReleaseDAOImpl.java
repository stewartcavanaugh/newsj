/*
 * Copyright (c) 2015. Sten Martinez
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

import net.longfalcon.newsj.model.Release;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/13/15
 * Time: 9:39 PM
 */
@Repository
public class ReleaseDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.ReleaseDAO {

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> findTopCommentedReleases() {
        Query query = sessionFactory.getCurrentSession().createQuery("select r from Release r where comments > 0 order by comments desc");
        query.setMaxResults(10);
        return query.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> findTopDownloads() {
        Query query = sessionFactory.getCurrentSession().createQuery("select r from Release r where grabs > 0 order by grabs desc");
        query.setMaxResults(10);
        return query.list();
    }


    /**
     *
     * @return List of Object[] = {Category,long}
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Object[]> findRecentlyAddedReleaseCategories() {
        Date oneWeekAgo = DateTime.now().minusWeeks(1).toDate();
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.ge("addDate", oneWeekAgo));
        criteria.setProjection(Projections.projectionList()
                        .add(Projections.groupProperty("category").as("category"))
                        .add(Projections.count("id").as("count"))
        );
        criteria.addOrder(Order.desc("count"));
        criteria.setMaxResults(10);

        return criteria.list();
    }

    @Override
    @Transactional
    public void updateRelease(Release release) {
        sessionFactory.getCurrentSession().saveOrUpdate(release);
    }

    @Override
    @Transactional
    public void deleteRelease(Release release) {
        sessionFactory.getCurrentSession().delete(release);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Release findByReleaseId(long releaseId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.eq("id", releaseId));

        return (Release) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> findReleasesBeforeDate(Date before) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.lt("postDate", before));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> findReleasesByNameAndDateRange(String relName, Date startDate, Date endDate) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.eq("searchName", relName));
        criteria.add(Restrictions.between("postDate", startDate, endDate));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> findReleasesByNoImdbIdAndCategoryId(Collection<Integer> categoryIds) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.isNull("imdbId"));
        criteria.add(Restrictions.in("categoryId", categoryIds));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> findReleasesByRageIdAndCategoryId(long rageId, Collection<Integer> categoryIds) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.eq("rageId", rageId));
        criteria.add(Restrictions.in("categoryId", categoryIds));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Long countByGroupId(long groupId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.eq("groupId", groupId));
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional
    public void deleteByGroupId(long groupId) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from Release r where r.groupId = :group_id");
        query.setParameter("group_id", groupId);

        query.executeUpdate();
    }
}
