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

import net.longfalcon.newsj.model.Genre;
import net.longfalcon.newsj.persistence.GenreDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/10/16
 * Time: 5:24 PM
 */
public class GenreDAOImpl extends HibernateDAOImpl implements GenreDAO {
    @Override
    @Transactional
    public void updateGenre(Genre genre) {
        sessionFactory.getCurrentSession().saveOrUpdate(genre);
    }

    @Override
    @Transactional
    public void deleteGenre(Genre genre) {
        sessionFactory.getCurrentSession().delete(genre);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS)
    public List<Genre> getGenres(int type) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Genre.class);
        criteria.add(Restrictions.eq("type", type));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS)
    public Genre getGenre(long genreId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Genre.class);
        criteria.add(Restrictions.eq("id", genreId));

        return (Genre) criteria.uniqueResult();
    }
}
