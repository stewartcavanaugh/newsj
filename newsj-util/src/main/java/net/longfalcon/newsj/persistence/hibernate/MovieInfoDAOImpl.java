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

import net.longfalcon.newsj.model.MovieInfo;
import net.longfalcon.newsj.persistence.MovieInfoDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/16/16
 * Time: 1:58 PM
 */
public class MovieInfoDAOImpl extends HibernateDAOImpl implements MovieInfoDAO {
    @Override
    @Transactional
    public void update(MovieInfo movieInfo) {
        sessionFactory.getCurrentSession().saveOrUpdate(movieInfo);
    }

    @Override
    @Transactional
    public void delete(MovieInfo movieInfo) {
        sessionFactory.getCurrentSession().delete(movieInfo);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS )
    public Long countMovieInfos() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MovieInfo.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS )
    public List<MovieInfo> getMovieInfos(int offset, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MovieInfo.class);
        criteria.setFirstResult(offset).setMaxResults(pageSize);
        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS )
    public MovieInfo findByMovieInfoId(long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MovieInfo.class);
        criteria.add(Restrictions.eq("id", id));

        return (MovieInfo) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS )
    public MovieInfo findByImdbId(long imdbId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MovieInfo.class);
        criteria.add(Restrictions.eq("imdbId", imdbId));

        return (MovieInfo) criteria.uniqueResult();
    }
}
