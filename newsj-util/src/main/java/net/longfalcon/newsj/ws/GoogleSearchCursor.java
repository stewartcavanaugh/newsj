package net.longfalcon.newsj.ws;

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
