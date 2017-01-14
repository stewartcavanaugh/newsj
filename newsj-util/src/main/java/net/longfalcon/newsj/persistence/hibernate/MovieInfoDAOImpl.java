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
import net.longfalcon.newsj.util.ValidatorUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
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
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS )
    public Long countMovieInfos() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MovieInfo.class);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS )
    public List<MovieInfo> getMovieInfos(int offset, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MovieInfo.class);
        criteria.setFirstResult(offset).setMaxResults(pageSize);
        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS )
    public MovieInfo findByMovieInfoId(long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MovieInfo.class);
        criteria.add(Restrictions.eq("id", id));

        return (MovieInfo) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS )
    public MovieInfo findByImdbId(int imdbId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MovieInfo.class);
        criteria.add(Restrictions.eq("imdbId", imdbId));

        return (MovieInfo) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS )
    public List<MovieInfo> findByImdbId(List<Long> imdbIds) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MovieInfo.class);
        if (imdbIds != null && !imdbIds.isEmpty()) {
            criteria.add(Restrictions.in("imdbId",imdbIds));
        }

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Long getMovieCount(List<Long> imdbIds, String titleSearch, String genreSearch, String actorsSearch,
                              String directorSearch, String yearSearch) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MovieInfo.class);

        if (imdbIds != null && !imdbIds.isEmpty()) {
            criteria.add(Restrictions.in("imdbId", imdbIds));
        }

        if (ValidatorUtil.isNotNull(titleSearch)) {
            criteria.add(Restrictions.ilike("title", titleSearch, MatchMode.ANYWHERE));
        }

        if (ValidatorUtil.isNotNull(genreSearch)) {
            criteria.add(Restrictions.ilike("genre",genreSearch, MatchMode.ANYWHERE));
        }

        if (ValidatorUtil.isNotNull(actorsSearch)) {
            criteria.add(Restrictions.ilike("actors",actorsSearch, MatchMode.ANYWHERE));
        }

        if (ValidatorUtil.isNotNull(directorSearch)) {
            criteria.add(Restrictions.ilike("director",directorSearch, MatchMode.ANYWHERE));
        }

        if (ValidatorUtil.isNotNull(yearSearch)) {
            criteria.add(Restrictions.like("year", yearSearch, MatchMode.EXACT));
        }

        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<MovieInfo> getMovies(List<Long> imdbIds, String titleSearch, String genreSearch, String actorsSearch,
                                         String directorSearch, String yearSearch,
                                         int offset, int pageSize, String orderByField, boolean descending) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MovieInfo.class);

        if (imdbIds != null && !imdbIds.isEmpty()) {
            criteria.add(Restrictions.in("imdbId", imdbIds));
        }

        if (ValidatorUtil.isNotNull(titleSearch)) {
            criteria.add(Restrictions.ilike("title", titleSearch, MatchMode.ANYWHERE));
        }

        if (ValidatorUtil.isNotNull(genreSearch)) {
            criteria.add(Restrictions.ilike("genre",genreSearch, MatchMode.ANYWHERE));
        }

        if (ValidatorUtil.isNotNull(actorsSearch)) {
            criteria.add(Restrictions.ilike("actors",actorsSearch, MatchMode.ANYWHERE));
        }

        if (ValidatorUtil.isNotNull(directorSearch)) {
            criteria.add(Restrictions.ilike("director",directorSearch, MatchMode.ANYWHERE));
        }

        if (ValidatorUtil.isNotNull(yearSearch)) {
            criteria.add(Restrictions.like("year", yearSearch, MatchMode.EXACT));
        }

        if (ValidatorUtil.isNotNull(orderByField)) {
            if (descending) {
                criteria.addOrder(Order.desc(orderByField));
            } else {
                criteria.addOrder(Order.asc(orderByField));
            }
        }

        criteria.setFirstResult(offset).setMaxResults(pageSize);

        return criteria.list();
    }
}
