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

/**
 * User: Sten Martinez
 * Date: 10/13/15
 * Time: 1:02 PM
 */
public class MatchedReleaseQuery {
    private Long numberOfBinaries;
    private String releaseName;
    private Integer releaseTotalParts;
    private long group;
    private String fromName;
    private Integer reqId;

    public Long getNumberOfBinaries() {
        return numberOfBinaries;
    }

    public void setNumberOfBinaries(Long numberOfBinaries) {
        this.numberOfBinaries = numberOfBinaries;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public Integer getReleaseTotalParts() {
        return releaseTotalParts;
    }

    public void setReleaseTotalParts(Integer releaseTotalParts) {
        this.releaseTotalParts = releaseTotalParts;
    }

    public long getGroup() {
        return group;
    }

    public void setGroup(long group) {
        this.group = group;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }
}
