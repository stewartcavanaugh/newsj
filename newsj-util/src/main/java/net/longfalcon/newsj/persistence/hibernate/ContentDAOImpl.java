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

import net.longfalcon.newsj.model.Content;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 11/10/15
 * Time: 12:47 PM
 */
public class ContentDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.ContentDAO {
    @Override
    @Transactional
    public void update(Content content) {
        sessionFactory.getCurrentSession().saveOrUpdate(content);
    }

    @Override
    @Transactional
    public void delete(Content content) {
        sessionFactory.getCurrentSession().delete(content);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Content getById(long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Content c where c.id = :contentId");
        query.setParameter("contentId", id);

        return (Content) query.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Content> getAllContent() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Content c");

        return query.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Content> findByTypeAndRole(int type, int roleId, boolean isAdmin) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Content.class);
        criteria.add(Restrictions.eq("showInMenu", 1));
        criteria.add(Restrictions.eq("status", 1));
        criteria.add(Restrictions.eq("contentType", type));
        if (!isAdmin) {
            criteria.add(Restrictions.or(Restrictions.eq("role", roleId), Restrictions.eq("role", 0)));
        }

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Content> findByTypeAndRole(int type, int roleId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Content.class);
        criteria.add(Restrictions.eq("contentType", type));
        criteria.add(Restrictions.eq("role", roleId));

        return criteria.list();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from Content c where c.id = :id");
        query.setParameter("id", id);

        query.executeUpdate();
    }
}
