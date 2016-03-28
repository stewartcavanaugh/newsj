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

import net.longfalcon.newsj.model.BinaryBlacklistEntry;
import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 4:45 PM
 */
@Repository
public class BinaryBlacklistDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.BinaryBlacklistDAO {

    @Override
    @Transactional
    public void update(BinaryBlacklistEntry binaryBlacklistEntry) {
        sessionFactory.getCurrentSession().saveOrUpdate(binaryBlacklistEntry);
    }

    @Override
    @Transactional
    public void delete(BinaryBlacklistEntry binaryBlacklistEntry) {
        sessionFactory.getCurrentSession().delete(binaryBlacklistEntry);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<BinaryBlacklistEntry> findAllBinaryBlacklistEntries(boolean activeOnly) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BinaryBlacklistEntry.class);
        if (activeOnly) {
            criteria.add(Restrictions.eq("status", 1));
        }
        criteria.addOrder(Order.asc("groupName").nulls(NullPrecedence.LAST));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public BinaryBlacklistEntry findByBinaryBlacklistId(long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BinaryBlacklistEntry.class);
        criteria.add(Restrictions.eq("id",id));

        return (BinaryBlacklistEntry) criteria.uniqueResult();
    }
}
