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

import net.longfalcon.newsj.model.JobLog;
import net.longfalcon.newsj.persistence.JobLogDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 9/17/16
 * Time: 7:48 PM
 */
public class JobLogDAOImpl extends HibernateDAOImpl implements JobLogDAO {
    @Override
    @Transactional
    public void update(JobLog jobLog) {
        sessionFactory.getCurrentSession().saveOrUpdate(jobLog);
    }

    @Override
    @Transactional
    public void delete(JobLog jobLog) {
        sessionFactory.getCurrentSession().delete(jobLog);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<JobLog> getJobLogByJobName(String jobName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JobLog.class);
        criteria.add(Restrictions.eq("jobName", jobName));
        criteria.addOrder(Order.desc("endDate"));
        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<JobLog> getAllJobLogs() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JobLog.class);
        criteria.addOrder(Order.desc("endDate"));
        return criteria.list();
    }
}
