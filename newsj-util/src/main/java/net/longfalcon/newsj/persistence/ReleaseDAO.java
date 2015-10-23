package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.Release;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/13/15
 * Time: 9:43 PM
 */
public interface ReleaseDAO {
    void updateRelease(Release release);

    void deleteRelease(Release release);

    Release findByReleaseId(long releaseId);

    List<Release> findReleasesBeforeDate(Date before);

    List<Release> findReleasesByNameAndDateRange(String relName, Date startDate, Date endDate);

    List<Release> findReleasesByNoImdbIdAndCategoryId(Collection<Integer> categoryIds);

    List<Release> findReleasesByRageIdAndCategoryId(int rageId, Collection<Integer> categoryIds);
}
