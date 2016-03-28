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

import net.longfalcon.newsj.model.MenuItem;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 11/9/15
 * Time: 2:59 PM
 */
public class MenuItemDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.MenuItemDAO {
    @Override
    @Transactional
    public void update(MenuItem menuItem) {
        sessionFactory.getCurrentSession().saveOrUpdate(menuItem);
    }

    @Override
    @Transactional
    public void delete(MenuItem menuItem) {
        sessionFactory.getCurrentSession().delete(menuItem);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<MenuItem> getMenuItemsByRole(int roleId, boolean noGuestRole) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MenuItem.class);
        criteria.add(Restrictions.le("role", roleId));
        if (noGuestRole) {
            criteria.add(Restrictions.not(Restrictions.eq("role", 0)));
        }
        criteria.addOrder(Order.asc("ordinal"));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<MenuItem> getMenuItems() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MenuItem.class);
        criteria.addOrder(Order.asc("role"));
        criteria.addOrder(Order.asc("ordinal"));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public MenuItem findByMenuItemId(long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MenuItem.class);
        criteria.add(Restrictions.eq("id", id));

        return (MenuItem) criteria.uniqueResult();
    }
}
