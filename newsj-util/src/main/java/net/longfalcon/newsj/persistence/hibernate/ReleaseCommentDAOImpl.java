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
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/9/16
 * Time: 2:59 PM
 */
public class ReleaseCommentDAOImpl extends HibernateDAOImpl implements ReleaseCommentDAO {

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Long countReleaseComments() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReleaseComment.class);
        criteria.setProjection(Projections.count("id"));

        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional
    public void deleteReleaseComment(ReleaseComment releaseComment) {
        sessionFactory.getCurrentSession().delete(releaseComment);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public ReleaseComment findByReleaseCommentId(long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReleaseComment.class);
        criteria.add(Restrictions.eq("id", id));
        criteria.setFetchMode("user", FetchMode.JOIN);
        criteria.setFetchMode("release", FetchMode.JOIN);

        return (ReleaseComment) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<ReleaseComment> findByReleaseId(long releaseId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReleaseComment.class);
        criteria.add(Restrictions.eq("release.id", releaseId));
        criteria.setFetchMode("user", FetchMode.JOIN);
        criteria.setFetchMode("release", FetchMode.JOIN);
        criteria.addOrder(Order.desc("createDate"));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<ReleaseComment> getReleaseComments(int start, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReleaseComment.class);
        criteria.setFirstResult(start).setMaxResults(pageSize);
        criteria.setFetchMode("user",FetchMode.JOIN);
        criteria.setFetchMode("release", FetchMode.JOIN);

        return criteria.list();
    }

    @Override
    @Transactional
    public void updateReleaseComment(ReleaseComment releaseComment) {
        sessionFactory.getCurrentSession().saveOrUpdate(releaseComment);
    }
}
