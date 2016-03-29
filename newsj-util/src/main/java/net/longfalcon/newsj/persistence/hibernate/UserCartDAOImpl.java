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

import net.longfalcon.newsj.model.UserCart;
import net.longfalcon.newsj.persistence.UserCartDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/29/16
 * Time: 3:57 PM
 */
public class UserCartDAOImpl extends HibernateDAOImpl implements UserCartDAO {
    @Override
    @Transactional
    public void update(UserCart userCart) {
        sessionFactory.getCurrentSession().saveOrUpdate(userCart);
    }

    @Override
    @Transactional
    public void delete(UserCart userCart) {
        sessionFactory.getCurrentSession().delete(userCart);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public UserCart findByUserIdAndReleaseId(long userId, long releaseId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserCart.class);
        criteria.add(Restrictions.eq("userId", userId)).add(Restrictions.eq("releaseId", releaseId));
        return (UserCart) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<UserCart> findByUserId(long userId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserCart.class);
        criteria.add(Restrictions.eq("userId", userId));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public UserCart findByCartIdAndUserId(long cartId, long userId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserCart.class);
        criteria.add(Restrictions.eq("userId", userId)).add(Restrictions.eq("id", cartId));
        return (UserCart) criteria.uniqueResult();
    }
}
