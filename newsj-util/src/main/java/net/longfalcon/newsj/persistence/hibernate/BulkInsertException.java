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

import java.util.HashSet;
import java.util.Set;

/**
 * User: Sten Martinez
 * Date: 1/31/17
 * Time: 9:58 PM
 */
public class BulkInsertException extends Throwable {
    Set<Long> failedIds = new HashSet<>();
    /**
     *
     * @param failedIds the ids that failed the insert, so that the caller can retry or log
     * @param e
     */
    public BulkInsertException(Set<Long> failedIds, Exception e) {
        super("Batch Failed - " + e.toString(), e);
        this.failedIds = failedIds;
    }

    public Set<Long> getFailedIds() {
        return failedIds;
    }
}
