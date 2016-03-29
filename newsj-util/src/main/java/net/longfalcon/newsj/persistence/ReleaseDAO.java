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

    Long countByGroupId(long groupId);

    Long countReleasesByRegexId(long regexId);

    Long countByCategoriesMaxAgeAndGroup(Collection<Integer> categoryIds, Date maxAge,
                                         Collection<Integer> excludedCategoryIds, Long groupId);

    List<Release> findByCategoriesMaxAgeAndGroup(Collection<Integer> categoryIds, Date maxAge,
                                                 Collection<Integer> excludedCategoryIds, Long groupId,
                                                 String orderByField, boolean descending,
                                                 int offset, int pageSize);

    Long searchCountByCategoriesMaxAgeAndGroup(String[] searchTokens, Collection<Integer> categoryIds, Date maxAge,
                                               Collection<Integer> excludedCategoryIds, Long groupId);

    List<Release> searchByCategoriesMaxAgeAndGroup(String[] searchTokens, Collection<Integer> categoryIds, Date maxAge,
                                                   Collection<Integer> excludedCategoryIds, Long groupId,
                                                   String orderByField, boolean descending,
                                                   int offset, int pageSize);

    void deleteByGroupId(long groupId);

    void deleteRelease(Release release);

    Release findByGuid(String guid);

    Release findByReleaseId(long releaseId);

    List<Object[]> findRecentlyAddedReleaseCategories();

    List<Release> findByGuids(String[] guids);

    List<Release> findReleasesBeforeDate(Date before);

    List<Release> findReleasesByNameAndDateRange(String relName, Date startDate, Date endDate);

    List<Release> findReleasesByNoImdbIdAndCategoryId(Collection<Integer> categoryIds);

    List<Release> findReleasesByRageIdAndCategoryId(long rageId, Collection<Integer> categoryIds);

    List<Release> findTopCommentedReleases();

    List<Release> findTopDownloads();

    Date getLastReleaseDateByRegexId(long regexId);

    List<Release> getReleases(int offset, int pageSize);

    Long getReleasesCount();

    void resetReleaseTvRageId(long tvRageId);

    void updateRelease(Release release);

    List<Release> searchReleasesByNameExludingCats(List<String> searchTokens, int limit, Collection<Integer> excludedCategoryIds);

    List<Long> findReleaseGroupIds();
}
