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

package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.PartRepair;

import java.util.Collection;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/8/15
 * Time: 10:08 AM
 */
public interface PartRepairDAO {
    void updatePartRepair(PartRepair partRepair);

    void deletePartRepair(PartRepair partRepair);

    PartRepair findByArticleNumberAndGroupId(long articleNumber, long groupId);

    List<PartRepair> findByGroupIdAndAttempts(long groupId, int attempts, boolean lessThan);

    List<PartRepair> findByGroupIdAndNumbers(long groupId, Collection<Long> numbers);
}
