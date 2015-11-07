/*
 * Copyright (c) 2015. Sten Martinez
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

import net.longfalcon.newsj.model.Category;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
