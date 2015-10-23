package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.MatchedReleaseQuery;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 11:08 PM
 */
@Repository
public class BinaryDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.BinaryDAO {

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public Binary findByBinaryHash(String binaryHash) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("binaryHash", binaryHash));

        return (Binary) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Binary> findByGroupIdsAndProcStat(Collection<Long> groupIds, int procStat) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.in("groupId", groupIds));
        criteria.add(Restrictions.eq("procStat", procStat));
        criteria.addOrder(Order.asc("date"));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<MatchedReleaseQuery> findBinariesByProcStatAndTotalParts(int procstat) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("procStat", procstat));
        criteria.setProjection(Projections.projectionList()
                        .add(Projections.rowCount(), "numberOfBinaries")
                        .add(Projections.property("groupId").as("group"))
                        .add(Projections.property("reqId").as("reqId"))
                        .add(Projections.groupProperty("relName").as("releaseName"))
                        .add(Projections.groupProperty("relTotalPart").as("releaseTotalParts"))
                        .add(Projections.groupProperty("fromName").as("fromName"))
        );
        criteria.setResultTransformer(Transformers.aliasToBean(MatchedReleaseQuery.class));
        return criteria.list();
    }

    @Override
    @Transactional
    public void updateBinary(Binary binary) {
        sessionFactory.getCurrentSession().saveOrUpdate(binary);
        //sessionFactory.getCurrentSession().flush();
    }

    @Override
    @Transactional
    public void deleteBinary(Binary binary) {
        sessionFactory.getCurrentSession().delete(binary);
    }

    @Override
    @Transactional
    public void deleteBinaryByDate(Date before) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete Binary b where b.dateAdded < :before");
        query.setParameter("before", before);

        query.executeUpdate();
    }

    @Override
    @Transactional
    public void updateBinaryIncrementProcAttempts(String relName, int procStat, long groupId, String fromName) {
        Query query =
                sessionFactory.getCurrentSession().createQuery("update Binary b set b.procAttempts = b.procAttempts + 1 " +
                        "where b.relName = :name and b.procStat = :procstat and b.groupId = :groupId and b.fromName = :fromname");
        query.setParameter("name", relName);
        query.setParameter("procstat", procStat);
        query.setParameter("groupId", groupId);
        query.setParameter("fromname", fromName);

        query.executeUpdate();
    }


    @Override
    @Transactional
    public void updateBinaryNameAndStatus(String newName, int newStatus, String relName, int procStat, long groupId, String fromName) {
        Query query =
                sessionFactory.getCurrentSession().createQuery("update Binary b set b.relName = :newname, b.procStat = :newstatus " +
                        "where b.relName = :name and b.procStat = :procstat and b.groupId = :groupId and b.fromName = :fromname");
        query.setParameter("newname", newName);
        query.setParameter("newstatus", newStatus);
        query.setParameter("name", relName);
        query.setParameter("procstat", procStat);
        query.setParameter("groupId", groupId);
        query.setParameter("fromname", fromName);

        query.executeUpdate();
    }

    @Override
    @Transactional
    public void updateBinaryNameStatusReleaseID(String newName, int newStatus, long newReleaseId, String relName, int procStat, long groupId, String fromName) {
        Query query =
                sessionFactory.getCurrentSession().createQuery("update Binary b set b.relName = :newname, b.procStat = :newstatus, b.releaseId = :newrelid " +
                        "where b.relName = :name and b.procStat = :procstat and b.groupId = :groupId and b.fromName = :fromname");
        query.setParameter("newname", newName);
        query.setParameter("newstatus", newStatus);
        query.setParameter("newrelid", newReleaseId);
        query.setParameter("name", relName);
        query.setParameter("procstat", procStat);
        query.setParameter("groupId", groupId);
        query.setParameter("fromname", fromName);

        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Binary> findBinariesByReleaseNameProcStatGroupIdFromName(String relName, int procStat, long groupId, String fromName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("relName", relName));
        criteria.add(Restrictions.eq("procStat", procStat));
        criteria.add(Restrictions.eq("groupId", groupId));
        criteria.add(Restrictions.eq("fromName", fromName));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public Timestamp findMaxDateAddedBinaryByReleaseNameProcStatGroupIdFromName(String relName, int procStat, long groupId, String fromName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("relName", relName));
        criteria.add(Restrictions.eq("procStat", procStat));
        criteria.add(Restrictions.eq("groupId", groupId));
        criteria.add(Restrictions.eq("fromName", fromName));
        criteria.setProjection(Projections.max("dateAdded"));

        return (Timestamp) criteria.uniqueResult();
    }

    @Override
    @Transactional
    public void updateProcStatByProcStatAndDate(int newStatus, int procStat, Date before) {
        Query query =
                sessionFactory.getCurrentSession().createQuery("update Binary b set b.procStat = :newstatus " +
                        "where b.procStat = :procstat and b.dateAdded < :beforeDate");
        query.setParameter("newstatus", newStatus);
        query.setParameter("procstat", procStat);
        query.setParameter("beforeDate", before);

        query.executeUpdate();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public List<Binary> findBinariesByReleaseId(long releaseId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Binary.class);
        criteria.add(Restrictions.eq("releaseId", releaseId));
        criteria.addOrder(Order.asc("name"));
        criteria.addOrder(Order.asc("relPart"));

        return criteria.list();
    }
}
