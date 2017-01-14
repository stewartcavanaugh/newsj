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

import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.Part;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;
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
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Part> findPartsByBinaryId(long binaryId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Part.class);
        criteria.add(Restrictions.eq("binaryId", binaryId));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Long countPartsByBinaryId(long binaryId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Part.class);
        criteria.add(Restrictions.eq("binaryId", binaryId));
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Long sumPartsSizeByBinaryId(long binaryId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Part.class);
        criteria.add(Restrictions.eq("binaryId", binaryId));
        criteria.setProjection(Projections.sum("size"));

        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Part> findByNumberAndBinaryIds(long number, List<Long> binaryIds) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Part.class);
        criteria.add(Restrictions.eq("number", number));
        criteria.add(Restrictions.in("binaryId", binaryIds));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Part> findByNumberAndGroupId(long number, long groupId) {
        DetachedCriteria groupBinarySubQuery = DetachedCriteria.forClass(Binary.class, "bin")
                .add(Restrictions.eq("groupId", groupId))
                .setProjection( Projections.property("id") );

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Part.class);
        criteria.add(Restrictions.eq("number", number));
        criteria.add(Subqueries.propertyIn("binaryId", groupBinarySubQuery));

        return criteria.list();
    }

    /**
     * findDistinctMessageIdSizeAndPartNumberByBinaryId
     * @param binaryId
     * @return list of {String,Long,Integer}
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Object[]> findDistinctMessageIdSizeAndPartNumberByBinaryId(long binaryId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Part.class);
        criteria.add(Restrictions.eq("binaryId", binaryId));
        criteria.setProjection(Projections.projectionList()
                        .add(Projections.distinct(Projections.property("messageId")))
                        .add(Projections.property("size"))
                        .add(Projections.property("partNumber"))
        );
        criteria.addOrder(Order.desc("partNumber"));

        return criteria.list();
    }
}
