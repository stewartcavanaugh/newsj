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

import net.longfalcon.newsj.model.UserInvite;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 11/10/15
 * Time: 10:06 PM
 */
public class UserInviteDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.UserInviteDAO {
    @Override
    @Transactional
    public void update(UserInvite userInvite) {
        sessionFactory.getCurrentSession().saveOrUpdate(userInvite);
    }

    @Override
    @Transactional
    public void delete(UserInvite userInvite) {
        sessionFactory.getCurrentSession().delete(userInvite);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public void cleanOldInvites(Date expireDate) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from UserInvite ui where createDate < :expireDate");
        query.setParameter("expireDate", expireDate);

        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public UserInvite getInviteByGuid(String guid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserInvite.class);
        criteria.add(Restrictions.eq("guid", guid));

        return (UserInvite) criteria.uniqueResult();
    }
}
