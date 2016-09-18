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
