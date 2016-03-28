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

import net.longfalcon.newsj.CategoryService;
import net.longfalcon.newsj.model.Category;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * User: Sten Martinez
 * Date: 10/16/15
 * Time: 4:45 PM
 */
@Repository
public class CategoryDAOImpl extends HibernateDAOImpl implements net.longfalcon.newsj.persistence.CategoryDAO {

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public Category findByCategoryId(int categoryId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
        criteria.add(Restrictions.eq("id", categoryId));

        return (Category) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Category> findByParentId(int parentId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
        criteria.add(Restrictions.eq("parentId", parentId));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Category> getForMenu(Set<Integer> userExcludedCategoryIds, Integer parentId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
        if (!userExcludedCategoryIds.isEmpty()) {
            criteria.add(Restrictions.not(Restrictions.in("id", userExcludedCategoryIds)));
        }
        criteria.add(parentId == null ? Restrictions.isNull("parentId") : Restrictions.eq("parentId", parentId));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Category> getForMenu(Set<Integer> userExcludedCategoryIds) {
        return getForMenu(userExcludedCategoryIds, null);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Category> getParentCategories() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
        criteria.add(Restrictions.isNull("parentId"));
        criteria.addOrder(Order.asc("id"));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Category> getChildCategories() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Category c where c.parentId != 0 order by c.id");

        return query.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Integer> getCategoryChildrenIds(int categoryParentId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
        criteria.add(Restrictions.eq("parentId", categoryParentId));
        criteria.setProjection(Projections.property("id"));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.SUPPORTS)
    public List<Category> getAllCategories(boolean activeOnly) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
        if (activeOnly) {
            criteria.add(Restrictions.eq("status", CategoryService.STATUS_ACTIVE));
        }
        criteria.addOrder(Order.asc("id"));

        return criteria.list();
    }

    @Override
    @Transactional
    public void updateCategory(Category category) {
        sessionFactory.getCurrentSession().saveOrUpdate(category);
    }
}
