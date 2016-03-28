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

package net.longfalcon.newsj.ws.google;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/21/15
 * Time: 12:22 PM
 */
public class GoogleSearchCursor {
    private List<GoogleSearchResultPage> pages;
    private Long estimatedResultCount;
    private Integer currentPageIndex;
    private String moreResultsUrl;

    public List<GoogleSearchResultPage> getPages() {
        return pages;
    }

    public void setPages(List<GoogleSearchResultPage> pages) {
        this.pages = pages;
    }

    public Long getEstimatedResultCount() {
        return estimatedResultCount;
    }

    public void setEstimatedResultCount(Long estimatedResultCount) {
        this.estimatedResultCount = estimatedResultCount;
    }

    public Integer getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(Integer currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public String getMoreResultsUrl() {
        return moreResultsUrl;
    }

    public void setMoreResultsUrl(String moreResultsUrl) {
        this.moreResultsUrl = moreResultsUrl;
    }
}
