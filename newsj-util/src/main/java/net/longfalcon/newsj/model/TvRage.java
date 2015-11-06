package net.longfalcon.newsj.model;

import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 11/5/15
 * Time: 10:13 PM
 */
public class TvRage {
    private long id;
    private long rageId;
    private long tvdbId;
    private String releaseTitle;
    private String description;
    private String genre;
    private String country;
    //private byte[] imgData;
    private Date createDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRageId() {
        return rageId;
    }

    public void setRageId(long rageId) {
        this.rageId = rageId;
    }

    public long getTvdbId() {
        return tvdbId;
    }

    public void setTvdbId(long tvdbId) {
        this.tvdbId = tvdbId;
    }

    public String getReleaseTitle() {
        return releaseTitle;
    }

    public void setReleaseTitle(String releaseTitle) {
        this.releaseTitle = releaseTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
