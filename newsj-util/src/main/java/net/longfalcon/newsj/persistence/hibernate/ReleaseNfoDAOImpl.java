package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.ReleaseNfo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/15/15
 * Time: 4:43 PM
 */
@Repository
public class ReleaseNfoDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.ReleaseNfoDAO {

    @Override
    @Transactional
    public void updateReleaseNfo(ReleaseNfo releaseNfo) {
        sessionFactory.getCurrentSession().saveOrUpdate(releaseNfo);
    }

    @Override
    @Transactional
    public void deleteReleaseNfo(ReleaseNfo releaseNfo) {
        sessionFactory.getCurrentSession().delete(releaseNfo);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<ReleaseNfo> findReleaseNfoWithNullNfoByAttempts(int attempts) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReleaseNfo.class);
        criteria.add(Restrictions.isNull("nfo"));
        criteria.add(Restrictions.lt("attemtps", attempts));

        return criteria.list();
    }
}
