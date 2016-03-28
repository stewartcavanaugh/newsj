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

import net.longfalcon.newsj.model.Binary;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 11:17 PM
 */
public interface BinaryDAO {

    List<Binary> findByReleaseId(long releaseId);

    Binary findByBinaryHash(String binaryHash);

    List<Binary> findByGroupIdsAndProcStat(Collection<Long> groupIds, int procStat);

    List findBinariesByProcStatAndTotalParts(int procstat);

    void updateBinary(Binary binary);

    void deleteBinary(Binary binary);

    void deleteBinaryByDate(Date before);

    void updateBinaryIncrementProcAttempts(String relName, int procStat, long groupId, String fromName);

    void updateBinaryNameAndStatus(String newName, int newStatus, String relName, int procStat, long groupId, String fromName);

    void updateBinaryNameStatusReleaseID(String newName, int newStatus, long newReleaseId, String relName, int procStat, long groupId, String fromName);

    List<Binary> findBinariesByReleaseNameProcStatGroupIdFromName(String relName, int procStat, long groupId, String fromName);

    Timestamp findMaxDateAddedBinaryByReleaseNameProcStatGroupIdFromName(String relName, int procStat, long groupId, String fromName);

    void updateProcStatByProcStatAndDate(int newStatus, int procStat, Date before);

    void resetReleaseBinaries(long releaseId);

    List<Binary> findBinariesByReleaseId(long releaseId);

    void deleteByGroupId(long groupId);

    List<Long> findBinaryIdsByGroupId(long groupId);

    List<Binary> findByGroupIdProcStatsReleaseId(long groupId, List<Integer> procStats, Long releaseId);

    List<Binary> searchByNameAndExcludedCats(String[] searchTokens, int limit, Collection<Integer> excludedCategoryIds);
}
