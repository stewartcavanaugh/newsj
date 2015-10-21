package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.PartRepair;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/8/15
 * Time: 8:40 AM
 */
@Repository
public class PartRepairDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.PartRepairDAO {

    @Override
    @Transactional
    public void updatePartRepair(PartRepair partRepair) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(partRepair);
        //this.sessionFactory.getCurrentSession().flush();
    }

    @Override
    @Transactional
    public void deletePartRepair(PartRepair partRepair) {
        this.sessionFactory.getCurrentSession().delete(partRepair);
        //this.sessionFactory.getCurrentSession().flush();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public PartRepair findByArticleNumberAndGroupId(long articleNumber, long groupId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PartRepair.class)
                .add(Restrictions.eq("numberId", articleNumber))
                .add(Restrictions.eq("groupId", articleNumber));
        List results = criteria.list();
        return results.size() > 0 ? (PartRepair) results.get(0) : null;
    }

    /**
     * Find by group id and how many attempts its had
     * @param groupId groupid foreign key
     * @param attempts the number of attempts to look for
     * @param lessThan whether the comparison is less than the attempts or greater than or equal to
     * @return
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<PartRepair> findByGroupIdAndAttempts(long groupId, int attempts, boolean lessThan) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PartRepair.class)
                .add(Restrictions.eq("groupId", groupId))
                .add(lessThan ? Restrictions.lt("attempts", attempts) : Restrictions.ge("attempts", attempts))
                .addOrder(Order.asc("numberId"))
                .setMaxResults(30000);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<PartRepair> findByGroupIdAndNumbers(long groupId, Collection<Long> numbers) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PartRepair.class)
                .add(Restrictions.eq("groupId", groupId)).add(Restrictions.in("numberId", numbers))
                .addOrder(Order.asc("numberId"));
        return criteria.list();
    }

}
