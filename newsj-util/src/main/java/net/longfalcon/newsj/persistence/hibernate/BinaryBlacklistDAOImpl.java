package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.BinaryBlacklistEntry;
import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 4:45 PM
 */
@Repository
public class BinaryBlacklistDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.BinaryBlacklistDAO {

    @Override
    public List<BinaryBlacklistEntry> findAllBinaryBlacklistEntries(boolean activeOnly) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BinaryBlacklistEntry.class);
        if (activeOnly) {
            criteria.add(Restrictions.eq("status", 1));
        }
        criteria.addOrder(Order.asc("groupName").nulls(NullPrecedence.LAST));

        return criteria.list();
    }
}
