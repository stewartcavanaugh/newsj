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

import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 10/5/15
 * Time: 5:13 PM
 */
public class Group {
    private long id;
    private String name;
    private int backfillTarget;
    private long firstRecord;
    private Date firstRecordPostdate;
    private long lastRecord;
    private Date lastRecordPostdate;
    private Date lastUpdated;
    private Integer minFilesToFormRelease;
    private boolean active;
    private String description;
    private int releaseCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBackfillTarget() {
        return backfillTarget;
    }

    public void setBackfillTarget(int backfillTarget) {
        this.backfillTarget = backfillTarget;
    }

    public long getFirstRecord() {
        return firstRecord;
    }

    public void setFirstRecord(long firstRecord) {
        this.firstRecord = firstRecord;
    }

    public Date getFirstRecordPostdate() {
        return firstRecordPostdate;
    }

    public void setFirstRecordPostdate(Date firstRecordPostdate) {
        this.firstRecordPostdate = firstRecordPostdate;
    }

    public long getLastRecord() {
        return lastRecord;
    }

    public void setLastRecord(long lastRecord) {
        this.lastRecord = lastRecord;
    }

    public Date getLastRecordPostdate() {
        return lastRecordPostdate;
    }

    public void setLastRecordPostdate(Date lastRecordPostdate) {
        this.lastRecordPostdate = lastRecordPostdate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getMinFilesToFormRelease() {
        return minFilesToFormRelease;
    }

    public void setMinFilesToFormRelease(Integer minFilesToFormRelease) {
        this.minFilesToFormRelease = minFilesToFormRelease;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseCount() {
        return releaseCount;
    }

    public void setReleaseCount(int releaseCount) {
        this.releaseCount = releaseCount;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                '}';
    }
}
