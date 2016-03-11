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

import net.longfalcon.newsj.model.ConsoleInfo;
import net.longfalcon.newsj.persistence.ConsoleInfoDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/10/16
 * Time: 2:29 PM
 */
public class ConsoleInfoDAOImpl extends HibernateDAOImpl implements ConsoleInfoDAO {
    @Override
    @Transactional
    public void updateConsoleInfo(ConsoleInfo consoleInfo) {
        sessionFactory.getCurrentSession().saveOrUpdate(consoleInfo);
    }

    @Override
    @Transactional
    public void deleteConsoleInfo(ConsoleInfo consoleInfo) {
        sessionFactory.getCurrentSession().delete(consoleInfo);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<ConsoleInfo> getConsoleInfos(int start, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ConsoleInfo.class);
        criteria.setFirstResult(start).setMaxResults(pageSize);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public Long countConsoleInfos() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ConsoleInfo.class);
        criteria.setProjection(Projections.count("id"));

        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public ConsoleInfo findByConsoleInfoId(long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ConsoleInfo.class);
        criteria.add(Restrictions.eq("id", id));

        return (ConsoleInfo) criteria.uniqueResult();
    }
}
