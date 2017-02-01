/*
 * Copyright (c) 2017. Sten Martinez
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

package net.longfalcon.newsj.service;

import net.longfalcon.newsj.model.GeneratedId;
import net.longfalcon.newsj.persistence.hibernate.HibernateDAOImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 1/31/17
 * Time: 4:21 PM
 */
@Service
public class IdGeneratorService extends HibernateDAOImpl {
    private long currentCounter;
    private int counterIncrement;
    private long currentId;

    private static final int DEFAULT_INCREMENT = 1000;
    private static final String GENERATOR_NAME = "PARTS";
    private static final Log _log = LogFactory.getLog(IdGeneratorService.class);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long increment() {
        return _increment();
    }

    private Long _increment() {
        Long value;
        if (currentId == currentCounter) {
            _log.debug("updating IdGenerator");

            updateCounter();

        }
        value = currentId++;
        _log.debug("Increment returns " + String.valueOf(value));
        return value;
    }

    public void init() {
        counterIncrement = DEFAULT_INCREMENT;
        try {
            initializeCounter();
            updateCounter();
            _log.info("IdGenerator Initialized");
        } catch (Exception e) {
            _log.error(e.getLocalizedMessage(), e);
        }
    }

    private void updateCounter() {
        try {
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from GeneratedId g where g.name = :name");
            query.setString("name", GENERATOR_NAME);
            query.setLockOptions(LockOptions.UPGRADE);
            GeneratedId generatedId = (GeneratedId) query.uniqueResult();
            currentCounter = generatedId.getValue();

            currentId = currentCounter;
            currentCounter = currentCounter + counterIncrement;

            generatedId.setValue(currentCounter);
            session.saveOrUpdate(generatedId);
            session.flush();
            session.close();
            _log.debug("IdGenerator updated to " + currentCounter);
        } catch (Exception e) {
            _log.error(e.getMessage(), e);
        }

    }

    private void initializeCounter() throws RuntimeException {
        try {
            Session session = sessionFactory.openSession();
            Query query = session.createQuery("from GeneratedId g where g.name = :name");
            query.setString("name", GENERATOR_NAME);
            query.setLockOptions(LockOptions.READ);
            List<GeneratedId> generatedIds = query.list();
            if (generatedIds == null || generatedIds.isEmpty()) {
                GeneratedId generatedId = new GeneratedId();
                generatedId.setName(GENERATOR_NAME);
                generatedId.setValue(0);
                session.saveOrUpdate(generatedId);
                session.flush();
                session.close();
            }

        } catch (Exception e) {
            _log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

}
