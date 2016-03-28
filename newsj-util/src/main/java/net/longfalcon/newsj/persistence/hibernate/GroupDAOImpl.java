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

import net.longfalcon.newsj.model.Group;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/5/15
 * Time: 5:13 PM
 */
@Repository
public class GroupDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.GroupDAO {

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Group> getGroups() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("from Group group")
                .list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Group> getGroups(int start, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Group.class);
        criteria.setFirstResult(start).setMaxResults(pageSize);
        criteria.setFetchMode("releases", FetchMode.JOIN);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Group> getGroups(int start, int pageSize, String orderByField, boolean descending) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Group.class);
        criteria.setFirstResult(start).setMaxResults(pageSize);
        if (descending) {
            criteria.addOrder(Order.desc(orderByField));
        } else {
            criteria.addOrder(Order.asc(orderByField));
        }
        criteria.setFetchMode("releases", FetchMode.JOIN);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public Long getGroupsCount() {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Group.class);
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Group> getActiveGroups() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("from Group group where group.active = true")
                .list();
    }

    @Transactional
    public void update(Group group) {
        Session currentSession = this.sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(group);
        //currentSession.flush();
    }

    @Override
    @Transactional
    public void delete(Group group) {
        Session currentSession = this.sessionFactory.getCurrentSession();
        currentSession.delete(group);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public Group getGroupByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Group.class);
        criteria.add(Restrictions.eq("name", name));

        return (Group) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Group> findGroupsByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Group.class);
        criteria.add(Restrictions.like("name", name + "%"));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public Group findGroupByGroupId(long groupId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Group.class);
        criteria.add(Restrictions.eq("id", groupId));

        return (Group) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Group> findGroupsByIds(Collection<Long> ids) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Group.class);
        criteria.add(Restrictions.in("id", ids));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS )
    public List<String> getGroupsForSelect() {
        Query query = sessionFactory.getCurrentSession().createQuery("select distinct coalesce(rr.groupName,'all') as _groupname from ReleaseRegex rr order by rr.groupName");

        return query.list();
    }
}
