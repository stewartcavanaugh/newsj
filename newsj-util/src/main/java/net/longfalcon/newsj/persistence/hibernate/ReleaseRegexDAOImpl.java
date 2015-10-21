package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.ReleaseRegex;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO: lots of magic numbers and un-obvious behavior
 * User: Sten Martinez
 * Date: 10/9/15
 * Time: 10:17 AM
 */
@Repository
public class ReleaseRegexDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.ReleaseRegexDAO {
    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS )
    public List<ReleaseRegex> getRegexes(boolean activeOnly, String groupName, boolean userReleaseRegexes) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReleaseRegex.class);

        if (activeOnly) {
            criteria.add(Restrictions.eq("status", 1));
        }
        if (!ValidatorUtil.isNull(groupName)) {
            if (groupName.equals("all")) {
               criteria.add(Restrictions.isNull("groupName"));
            } else if (!groupName.equals("-1")) {
                criteria.add(Restrictions.eq("groupName", groupName));
            }
        }

        if (userReleaseRegexes) {
            criteria.add(Restrictions.ge("id", 100000));
        }

        // rough approximation of original order by:
        // " order by (rr.groupName like '%*') asc , coalesce(rr.groupName, 'zzz') desc , rr.ordinal asc"
        criteria.addOrder(Order.desc("groupName").nulls(NullPrecedence.FIRST));
        criteria.addOrder(Order.asc("ordinal"));
        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS )
    public ReleaseRegex findById(long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReleaseRegex.class);
        criteria.add(Restrictions.eq("id", id));

        return (ReleaseRegex) criteria.uniqueResult();
    }

    @Override
    @Transactional
    public void updateReleaseRegex(ReleaseRegex releaseRegex) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(releaseRegex);
        //session.flush();
    }

    @Override
    @Transactional
    public void deleteReleaseRegex(ReleaseRegex releaseRegex) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(releaseRegex);
        //session.flush();
    }
}
