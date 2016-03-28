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

import net.longfalcon.newsj.model.ReleaseNfo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/15/15
 * Time: 4:43 PM
 */
@Repository
public class ReleaseNfoDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.ReleaseNfoDAO {

    @Override
    @Transactional
    public void updateReleaseNfo(ReleaseNfo releaseNfo) {
        sessionFactory.getCurrentSession().saveOrUpdate(releaseNfo);
    }

    @Override
    @Transactional
    public void deleteReleaseNfo(ReleaseNfo releaseNfo) {
        sessionFactory.getCurrentSession().delete(releaseNfo);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<ReleaseNfo> findReleaseNfoWithNullNfoByAttempts(int attempts) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReleaseNfo.class);
        criteria.add(Restrictions.isNull("nfo"));
        criteria.add(Restrictions.lt("attemtps", attempts));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public ReleaseNfo findByReleaseId(long releaseId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReleaseNfo.class);
        criteria.add(Restrictions.eq("release.id", releaseId));

        return (ReleaseNfo) criteria.uniqueResult();
    }
}
