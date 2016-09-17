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

import net.longfalcon.newsj.model.JobConfig;
import net.longfalcon.newsj.persistence.JobConfigDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 9/14/16
 * Time: 4:38 PM
 */
public class JobConfigDAOImpl extends HibernateDAOImpl implements JobConfigDAO {

    @Transactional
    public void update(JobConfig jobConfig) {
        sessionFactory.getCurrentSession().saveOrUpdate(jobConfig);
    }

    @Transactional
    public void delete(JobConfig jobConfig) {
        sessionFactory.getCurrentSession().delete(jobConfig);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public JobConfig getJobConfigByJobName(String jobName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JobConfig.class);
        criteria.add(Restrictions.eq("jobName", jobName));

        return (JobConfig) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<JobConfig> getAllJobConfig() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(JobConfig.class);

        return criteria.list();
    }
}
