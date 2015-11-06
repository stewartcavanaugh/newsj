package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.TvRage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Sten Martinez
 * Date: 11/5/15
 * Time: 10:26 PM
 */
public class TvRageDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.TvRageDAO {

    @Override
    @Transactional
    public void update(TvRage tvRage) {
        sessionFactory.getCurrentSession().saveOrUpdate(tvRage);
    }

    @Override
    @Transactional
    public void delete(TvRage tvRage) {
        sessionFactory.getCurrentSession().delete(tvRage);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public TvRage findByTvRageId(long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TvRage.class);
        criteria.add(Restrictions.eq("id", id));

        return (TvRage) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public TvRage findByReleaseTitle(String title) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TvRage.class);
        criteria.add(Restrictions.eq("releaseTitle", title.toLowerCase()));

        return (TvRage) criteria.uniqueResult();
    }
}
