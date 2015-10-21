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

    List<Binary> findBinariesByReleaseId(long releaseId);
}
