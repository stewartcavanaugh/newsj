package net.longfalcon.newsj.ws;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/21/15
 * Time: 12:16 PM
 */
public class GoogleSearchResponseData {
    private List<GoogleSearchResult> results;
    private GoogleSearchCursor cursor;

    public List<GoogleSearchResult> getResults() {
        return results;
    }

    public void setResults(List<GoogleSearchResult> results) {
        this.results = results;
    }

    public GoogleSearchCursor getCursor() {
        return cursor;
    }

    public void setCursor(GoogleSearchCursor cursor) {
        this.cursor = cursor;
    }
}
