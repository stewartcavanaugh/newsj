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

package net.longfalcon.newsj.persistence.hibernate;

import net.longfalcon.newsj.model.Part;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Sten Martinez
 * Date: 1/31/17
 * Time: 7:03 PM
 */
@Repository
public class BulkInserter extends HibernateDAOImpl {
    public static final int BATCH_SIZE = 500;
    private List<Part> partList = new ArrayList<>(BATCH_SIZE);
    private static final Log _log = LogFactory.getLog(BulkInserter.class);

    public void addPart(Part part) throws BulkInsertException {
        partList.add(part);
        if (partList.size() >= BATCH_SIZE) {
            runBatch();
        }
    }

    public void runBatch() throws BulkInsertException {
        //TODO: custom implementation for oracle PLSQL
        if (!partList.isEmpty()) {
            Set<Long> messagesBatched = new HashSet<>(BATCH_SIZE);
            String sql = "INSERT INTO PARTS (ID, BINARYID, MESSAGEID, NUMBER_, PARTNUMBER, SIZE_, DATEADDED) VALUES";
            Dialect dialect = ((SessionFactoryImplementor) sessionFactory).getDialect();

            try {
                if (dialect instanceof Oracle10gDialect) {
                    sql = "INSERT ALL ";
                    for (int i = 0; i < partList.size(); i++) {
                        Part part = partList.get(i);
                        Timestamp dateAdded = new Timestamp(part.getDateAdded().getTime());
                        sql += "INTO PARTS (ID, BINARYID, MESSAGEID, NUMBER_, PARTNUMBER, SIZE_, DATEADDED) VALUES ";
                        sql += String.format("( %s, %s, '%s', %s, %s, %s, TO_TIMESTAMP('%s', 'YYYY-MM-DD HH24:MI:SS.FF')) ",
                                part.getId(), part.getBinaryId(), part.getMessageId(), part.getNumber(), part.getPartNumber(), part.getSize(), dateAdded);
                        messagesBatched.add(part.getNumber());
                    }
                    sql += "SELECT 1 FROM DUAL";
                } else if (dialect instanceof MySQL5Dialect )  {
                    sql = "INSERT INTO `parts` (ID, BINARYID, MESSAGEID, NUMBER_, PARTNUMBER, SIZE_, DATEADDED) VALUES";
                    for (int i = 0; i < partList.size(); i++) {
                        Part part = partList.get(i);
                        Timestamp dateAdded = new Timestamp(part.getDateAdded().getTime());
                        sql += String.format(" ( %s, %s, '%s', %s, %s, %s, '%s')",
                                part.getId(), part.getBinaryId(), part.getMessageId(), part.getNumber(), part.getPartNumber(), part.getSize(), dateAdded);
                        if (i < partList.size() - 1) {
                            sql += ",";
                        }
                        messagesBatched.add(part.getNumber());
                    }
                } else {
                    sql = "INSERT INTO PARTS (ID, BINARYID, MESSAGEID, NUMBER_, PARTNUMBER, SIZE_, DATEADDED) VALUES";
                    for (int i = 0; i < partList.size(); i++) {
                        Part part = partList.get(i);
                        Timestamp dateAdded = new Timestamp(part.getDateAdded().getTime());
                        sql += String.format(" ( %s, %s, '%s', %s, %s, %s, '%s')",
                                part.getId(), part.getBinaryId(), part.getMessageId(), part.getNumber(), part.getPartNumber(), part.getSize(), dateAdded);
                        if (i < partList.size() - 1) {
                            sql += ",";
                        }
                        messagesBatched.add(part.getNumber());
                    }
                }
                SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
                query.executeUpdate();
                sessionFactory.getCurrentSession().flush();
            } catch (Exception e) {
                _log.error("SQL: \n" + sql);
                throw new BulkInsertException(messagesBatched, e);
            }
            partList.clear();
        }
    }
}
