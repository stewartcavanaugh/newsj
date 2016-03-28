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

package net.longfalcon.newsj.model;

import javax.persistence.Transient;
import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 10/9/15
 * Time: 9:49 AM
 */
public class ReleaseRegex {
    private long id;
    private String groupName;
    private String regex;
    private int ordinal;
    private int status;
    private String description;
    private Integer categoryId;
    // these two fields are transient and computed JIT, but will be potentially added
    // to the schema and computed either as statistics after the fact or during release processing
    private int numberReleases;
    private Date lastReleaseDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Transient
    public int getNumberReleases() {
        return numberReleases;
    }

    public void setNumberReleases(int numberReleases) {
        this.numberReleases = numberReleases;
    }

    @Transient
    public Date getLastReleaseDate() {
        return lastReleaseDate;
    }

    public void setLastReleaseDate(Date lastReleaseDate) {
        this.lastReleaseDate = lastReleaseDate;
    }
}
