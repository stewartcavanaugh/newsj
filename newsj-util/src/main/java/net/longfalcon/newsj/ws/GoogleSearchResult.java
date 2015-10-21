package net.longfalcon.newsj.ws;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: Sten Martinez
 * Date: 10/21/15
 * Time: 12:20 PM
 */
public class GoogleSearchResult {
    private String GsearchResultClass;
    private String unescapedUrl;
    private String url;
    private String visibleUrl;
    private String cacheUrl;
    private String title;
    private String titleNoFormatting;
    private String content;

    @JsonProperty("GsearchResultClass")
    public String getGsearchResultClass() {
        return GsearchResultClass;
    }

    public void setGsearchResultClass(String gsearchResultClass) {
        GsearchResultClass = gsearchResultClass;
    }

    public String getUnescapedUrl() {
        return unescapedUrl;
    }

    public void setUnescapedUrl(String unescapedUrl) {
        this.unescapedUrl = unescapedUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVisibleUrl() {
        return visibleUrl;
    }

    public void setVisibleUrl(String visibleUrl) {
        this.visibleUrl = visibleUrl;
    }

    public String getCacheUrl() {
        return cacheUrl;
    }

    public void setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleNoFormatting() {
        return titleNoFormatting;
    }

    public void setTitleNoFormatting(String titleNoFormatting) {
        this.titleNoFormatting = titleNoFormatting;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
