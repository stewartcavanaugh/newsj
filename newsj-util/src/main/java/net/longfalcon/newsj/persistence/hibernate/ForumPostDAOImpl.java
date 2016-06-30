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

import net.longfalcon.newsj.model.ForumPost;
import net.longfalcon.newsj.persistence.ForumPostDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 6/29/16
 * Time: 4:38 PM
 */
@Repository
public class ForumPostDAOImpl extends HibernateDAOImpl implements ForumPostDAO {

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS)
    public List<ForumPost> getForumPostsByParent(long parentId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ForumPost.class);

        criteria.add(Restrictions.eq("parentId", parentId));

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS)
    public List<ForumPost> getForumPosts(int offset, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ForumPost.class);
        criteria.add(Restrictions.eq("parentId", 0L));
        criteria.setFirstResult(offset).setMaxResults(pageSize);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS)
    public Long getForumPostCount() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ForumPost.class);
        criteria.add(Restrictions.eq("parentId", 0L));
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS)
    public ForumPost getForumPost(long forumPostId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ForumPost.class);

        criteria.add(Restrictions.eq("id", forumPostId));

        return (ForumPost) criteria.uniqueResult();
    }

    @Override
    @Transactional
    public void updateForumPost(ForumPost forumPost) {
        sessionFactory.getCurrentSession().saveOrUpdate(forumPost);
    }

    @Override
    @Transactional
    public void deleteForumPost(ForumPost forumPost) {
        sessionFactory.getCurrentSession().delete(forumPost);
    }
}
