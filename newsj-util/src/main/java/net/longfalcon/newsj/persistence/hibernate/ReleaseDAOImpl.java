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

import net.longfalcon.newsj.model.Release;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
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
    public Long countByGroupId(long groupId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.eq("groupId", groupId));
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Long countReleasesByRegexId(long regexId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.eq("regexId", regexId));
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Long countByCategoriesMaxAgeAndGroup(Collection<Integer> categoryIds, Date maxAge,
                                                Collection<Integer> excludedCategoryIds, Long groupId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        if (!categoryIds.isEmpty()) {
            criteria.add(Restrictions.in("category.id", categoryIds));
        }
        if (maxAge != null) {
            criteria.add(Restrictions.gt("postDate", maxAge));
        }
        if (excludedCategoryIds != null && !excludedCategoryIds.isEmpty()) {
            criteria.add(Restrictions.not(Restrictions.in("category.id", excludedCategoryIds)));
        }
        if (groupId != null) {
            criteria.add(Restrictions.eq("groupId",groupId));
        }
        criteria.setProjection(Projections.rowCount());


        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> findByCategoriesMaxAgeAndGroup(Collection<Integer> categoryIds, Date maxAge,
                                                        Collection<Integer> excludedCategoryIds, Long groupId,
                                                        String orderByField, boolean descending,
                                                        int offset, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        if (!categoryIds.isEmpty()) {
            criteria.add(Restrictions.in("category.id", categoryIds));
        }
        if (maxAge != null) {
            criteria.add(Restrictions.gt("postDate", maxAge));
        }
        if (excludedCategoryIds != null && !excludedCategoryIds.isEmpty()) {
            criteria.add(Restrictions.not(Restrictions.in("category.id", excludedCategoryIds)));
        }
        if (groupId != null) {
            criteria.add(Restrictions.eq("groupId",groupId));
        }
        if (descending) {
            criteria.addOrder(Order.desc(orderByField));
        } else {
            criteria.addOrder(Order.asc(orderByField));
        }
        criteria.setFetchMode("category", FetchMode.JOIN);
        criteria.setFirstResult(offset).setMaxResults(pageSize);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Long searchCountByCategoriesMaxAgeAndGroup(String[] searchTokens, Collection<Integer> categoryIds, Date maxAge,
                                                      Collection<Integer> excludedCategoryIds, Long groupId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);

        if (searchTokens != null && searchTokens.length > 0) {
            Conjunction searchTokensOr = Restrictions.conjunction();
            for (String searchToken : searchTokens) {
                searchTokensOr.add(Restrictions.like("searchName", searchToken.trim(), MatchMode.ANYWHERE));
            }
            criteria.add(searchTokensOr);
        }

        if (!categoryIds.isEmpty()) {
            criteria.add(Restrictions.in("category.id", categoryIds));
        }
        if (maxAge != null) {
            criteria.add(Restrictions.gt("postDate", maxAge));
        }
        if (excludedCategoryIds != null && !excludedCategoryIds.isEmpty()) {
            criteria.add(Restrictions.not(Restrictions.in("category.id", excludedCategoryIds)));
        }
        if (groupId != null) {
            criteria.add(Restrictions.eq("groupId",groupId));
        }
        criteria.setProjection(Projections.rowCount());


        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> searchByCategoriesMaxAgeAndGroup(String[] searchTokens, Collection<Integer> categoryIds, Date maxAge,
                                                          Collection<Integer> excludedCategoryIds, Long groupId,
                                                          String orderByField, boolean descending,
                                                          int offset, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);

        if (searchTokens != null && searchTokens.length > 0) {
            Conjunction searchTokensOr = Restrictions.conjunction();
            for (String searchToken : searchTokens) {
                searchTokensOr.add(Restrictions.like("searchName", searchToken.trim(), MatchMode.ANYWHERE));
            }
            criteria.add(searchTokensOr);
        }

        if (!categoryIds.isEmpty()) {
            criteria.add(Restrictions.in("category.id", categoryIds));
        }
        if (maxAge != null) {
            criteria.add(Restrictions.gt("postDate", maxAge));
        }
        if (excludedCategoryIds != null && !excludedCategoryIds.isEmpty()) {
            criteria.add(Restrictions.not(Restrictions.in("category.id", excludedCategoryIds)));
        }
        if (groupId != null) {
            criteria.add(Restrictions.eq("groupId",groupId));
        }
        if (descending) {
            criteria.addOrder(Order.desc(orderByField));
        } else {
            criteria.addOrder(Order.asc(orderByField));
        }
        criteria.setFetchMode("category", FetchMode.JOIN);
        criteria.setFirstResult(offset).setMaxResults(pageSize);

        return criteria.list();
    }

    @Override
    @Transactional
    public void deleteByGroupId(long groupId) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from Release r where r.groupId = :group_id");
        query.setParameter("group_id", groupId);

        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteRelease(Release release) {
        sessionFactory.getCurrentSession().delete(release);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Release findByGuid(String guid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.eq("guid", guid));
        criteria.setFetchMode("category", FetchMode.JOIN);

        return (Release) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Release findByReleaseId(long releaseId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.eq("id", releaseId));
        criteria.setFetchMode("category", FetchMode.JOIN);

        return (Release) criteria.uniqueResult();
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
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> findByGuids(String[] guids) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.in("guid", guids));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> findReleasesBeforeDate(Date before) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.lt("postDate", before));
        criteria.setFetchMode("category", FetchMode.JOIN);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> findReleasesByNameAndDateRange(String relName, Date startDate, Date endDate) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.eq("searchName", relName));
        criteria.add(Restrictions.between("postDate", startDate, endDate));
        criteria.setFetchMode("category", FetchMode.JOIN);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> findReleasesByNoImdbIdAndCategoryId(Collection<Integer> categoryIds) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.isNull("imdbId"));
        criteria.add(Restrictions.in("category.id", categoryIds));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> findReleasesByRageIdAndCategoryId(long rageId, Collection<Integer> categoryIds) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.eq("rageId", rageId));
        criteria.add(Restrictions.in("category.id", categoryIds));

        return criteria.list();
    }

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

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Date getLastReleaseDateByRegexId(long regexId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.add(Restrictions.eq("regexId", regexId));
        criteria.setProjection(Projections.max("addDate"));

        return (Date) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> getReleases(int offset, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.setFirstResult(offset).setMaxResults(pageSize);
        criteria.setFetchMode("category", FetchMode.JOIN);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Long getReleasesCount() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional
    public void resetReleaseTvRageId(long tvRageId) {
        Query query = sessionFactory.getCurrentSession().createQuery("update Release r set r.rageId = -1 where r.rageId = :tvRageId");
        query.setParameter("tvRageId", tvRageId);

        query.executeUpdate();
    }

    @Override
    @Transactional
    public void updateRelease(Release release) {
        sessionFactory.getCurrentSession().saveOrUpdate(release);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Release> searchReleasesByNameExludingCats(List<String> searchTokens, int limit, Collection<Integer> excludedCategoryIds) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        if (!searchTokens.isEmpty()) {
            Disjunction searchOr = Restrictions.disjunction();
            for (String searchToken : searchTokens) {
                searchOr.add(Restrictions.like("searchName", searchToken, MatchMode.ANYWHERE));
            }
            criteria.add(searchOr);
        }

        if (!excludedCategoryIds.isEmpty()) {
            criteria.add(Restrictions.not(Restrictions.in("category.id", excludedCategoryIds)));
        }

        criteria.setMaxResults(limit);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Long> findReleaseGroupIds() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Release.class);
        criteria.setProjection(Projections.distinct(Projections.property("groupId")));

        return criteria.list();
    }
}
