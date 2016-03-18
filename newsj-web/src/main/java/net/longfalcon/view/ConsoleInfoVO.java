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

package net.longfalcon.view;

import net.longfalcon.newsj.model.ConsoleInfo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 3/10/16
 * Time: 9:34 PM
 */
public class ConsoleInfoVO {
    private long id;
    private String title;
    private String asin;
    private String url;
    private Integer salesRank;
    private String platform;
    private String publisher;
    private Long genreId;
    private String esrb;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private String review;
    private boolean cover;
    private Date createDate;
    private Date updateDate;
    private MultipartFile multipartFile;

    public ConsoleInfoVO() {
    }

    public ConsoleInfoVO(ConsoleInfo consoleInfo) {
        this.id = consoleInfo.getId();
        this.title = consoleInfo.getTitle();
        this.asin = consoleInfo.getAsin();
        this.url = consoleInfo.getUrl();
        this.salesRank = consoleInfo.getSalesRank();
        this.platform = consoleInfo.getPlatform();
        this.publisher = consoleInfo.getPublisher();
        this.genreId = consoleInfo.getGenreId();
        this.esrb = consoleInfo.getEsrb();
        this.releaseDate = consoleInfo.getReleaseDate();
        this.review = consoleInfo.getReview();
        this.cover = consoleInfo.isCover();
        this.createDate = consoleInfo.getCreateDate();
        this.updateDate = consoleInfo.getUpdateDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSalesRank() {
        return salesRank;
    }

    public void setSalesRank(Integer salesRank) {
        this.salesRank = salesRank;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getEsrb() {
        return esrb;
    }

    public void setEsrb(String esrb) {
        this.esrb = esrb;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
