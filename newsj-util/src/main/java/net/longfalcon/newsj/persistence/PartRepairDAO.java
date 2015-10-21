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
