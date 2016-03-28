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

import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.persistence.SiteDAO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Sten Martinez
 * Date: 10/5/15
 * Time: 11:12 PM
 */
@Repository
public class SiteDAOImpl extends HibernateDAOImpl implements SiteDAO {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void update(Site site) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(site);
        this.sessionFactory.getCurrentSession().flush();
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS)
    public Site getDefaultSite() {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Site.class);
        criteria.setMaxResults(1);
        return (Site) criteria.list().get(0);
    }
}
