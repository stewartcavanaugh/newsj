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

import net.longfalcon.newsj.model.UserExCat;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 11/9/15
 * Time: 9:30 AM
 */
public class UserExCatDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.UserExCatDAO {

    @Override
    @Transactional
    public void update(UserExCat userExCat) {
        sessionFactory.getCurrentSession().saveOrUpdate(userExCat);
    }

    @Override
    @Transactional
    public void delete(UserExCat userExCat) {
        sessionFactory.getCurrentSession().delete(userExCat);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Integer> getUserExCatIds(long userId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserExCat.class);
        criteria.add(Restrictions.eq("userId", userId));
        criteria.setProjection(Projections.property("categoryId"));

        return criteria.list();
    }
}
