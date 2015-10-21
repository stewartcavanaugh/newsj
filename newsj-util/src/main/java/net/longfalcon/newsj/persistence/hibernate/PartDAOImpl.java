package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.Part;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/8/15
 * Time: 7:11 AM
 */
@Repository
public class PartDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.PartDAO {

    @Override
    @Transactional
    public void updatePart(Part part) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(part);
        //currentSession.flush();
    }

    @Override
    @Transactional
    public void deletePart(Part part) {
        sessionFactory.getCurrentSession().delete(part);
    }

    @Override
    @Transactional
    public void deletePartByDate(Date before) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete Part p where p.dateAdded < :before");
        query.setParameter("before", before);

        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Part> findPartsByBinaryId(long binaryId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Part.class);
        criteria.add(Restrictions.eq("binaryId", binaryId));

        return criteria.list();
    }
}
