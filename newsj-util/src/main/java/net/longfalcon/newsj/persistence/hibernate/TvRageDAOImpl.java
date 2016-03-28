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

import net.longfalcon.newsj.model.TvRage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 11/5/15
 * Time: 10:26 PM
 */
public class TvRageDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.TvRageDAO {

    @Override
    @Transactional
    public void update(TvRage tvRage) {
        sessionFactory.getCurrentSession().saveOrUpdate(tvRage);
    }

    @Override
    @Transactional
    public void delete(TvRage tvRage) {
        sessionFactory.getCurrentSession().delete(tvRage);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public TvRage findByTvRageId(long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TvRage.class);
        criteria.add(Restrictions.eq("id", id));

        return (TvRage) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public TvRage findByReleaseTitle(String title) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TvRage.class);
        criteria.add(Restrictions.eq("releaseTitle", title.toLowerCase()));

        return (TvRage) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Long countTvRage() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TvRage.class);
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<TvRage> getTvRage(int offset, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TvRage.class);
        criteria.setFirstResult(offset).setMaxResults(pageSize);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<TvRage> searchTvRage(int offset, int pageSize, String titleSearch) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TvRage.class);
        criteria.add(Restrictions.like("releaseTitle", "%" + titleSearch + "%"));
        criteria.setFirstResult(offset).setMaxResults(pageSize);

        return criteria.list();
    }
}
