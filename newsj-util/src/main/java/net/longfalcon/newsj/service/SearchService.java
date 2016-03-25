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

package net.longfalcon.newsj.service;

import net.longfalcon.newsj.Releases;
import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.persistence.BinaryDAO;
import net.longfalcon.newsj.util.ValidatorUtil;

import java.util.List;

/**
 * wrapper in case we move to a better search impl than the db
 * User: Sten Martinez
 * Date: 3/24/16
 * Time: 1:44 PM
 */
public class SearchService {

    private Releases releases;
    private BinaryDAO binaryDAO;

    public List<Release> getSearchReleases(String search, List<Integer> categoryIds, int maxAgeDays,
                                           List<Integer> userExCatIds, long groupId, String orderByFieldName, boolean orderByDesc, int offset, int pageSize) {
        String[] searchTokens = splitSearchQueryString(search);
        return releases.getSearchReleases(searchTokens, categoryIds, maxAgeDays, userExCatIds, groupId, orderByFieldName, orderByDesc, offset, pageSize);
    }

    public long getSearchCount(String search, List<Integer> categoryIds, int maxAgeDays, List<Integer> userExCatIds, long groupId) {
        String[] searchTokens = splitSearchQueryString(search);
        return releases.getSearchCount(searchTokens, categoryIds, maxAgeDays, userExCatIds, groupId);
    }

    public List<Binary> searchBinaries(String search, List<Integer> userExCatIds) {
        String[] searchTokens = splitSearchQueryString(search);
        return binaryDAO.searchByNameAndExcludedCats(searchTokens, 1000, userExCatIds);
    }

    private String[] splitSearchQueryString(String queryString) {
        if (ValidatorUtil.isNotNull(queryString)) {
            return queryString.split(" ");
        } else {
            return new String[]{};
        }
    }

    public Releases getReleases() {
        return releases;
    }

    public void setReleases(Releases releases) {
        this.releases = releases;
    }

    public BinaryDAO getBinaryDAO() {
        return binaryDAO;
    }

    public void setBinaryDAO(BinaryDAO binaryDAO) {
        this.binaryDAO = binaryDAO;
    }
}
