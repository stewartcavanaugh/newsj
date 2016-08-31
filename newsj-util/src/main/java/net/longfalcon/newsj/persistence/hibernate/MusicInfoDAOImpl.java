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

import net.longfalcon.newsj.model.MusicInfo;
import net.longfalcon.newsj.persistence.MusicInfoDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/18/16
 * Time: 2:40 PM
 */
public class MusicInfoDAOImpl extends HibernateDAOImpl implements MusicInfoDAO {
    @Override
    @Transactional
    public void update(MusicInfo musicInfo) {
        sessionFactory.getCurrentSession().saveOrUpdate(musicInfo);
    }

    @Override
    @Transactional
    public void delete(MusicInfo musicInfo) {
        sessionFactory.getCurrentSession().delete(musicInfo);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Long countMusicInfos() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MusicInfo.class);
        criteria.setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<MusicInfo> getMusicInfos(int offset, int pageSize) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MusicInfo.class);
        criteria.setFirstResult(offset).setMaxResults(pageSize);

        return criteria.list();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MusicInfo findByMusicInfoId(long id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MusicInfo.class);
        criteria.add(Restrictions.eq("id", id));

        return (MusicInfo) criteria.uniqueResult();
    }
}
