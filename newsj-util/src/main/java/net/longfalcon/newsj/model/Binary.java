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
 * Date: 10/5/15
 * Time: 4:52 PM
 */
public class Binary {
    private long id;
    private String name;
    private String fromName;
    private Date date;
    private String xref;
    private int totalParts;
    private long groupId;
    private int procStat;
    private int procAttempts;
    private Integer categoryId;
    private Long regexId;
    private Integer reqId;
    private int relPart;
    private int relTotalPart;
    private String binaryHash;
    private String relName;
    private String importName;
    private Long releaseId;
    private Date dateAdded;
    private String categoryName;
    private String groupName;
    private int numberParts;
    private String releaseGuid;

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

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getXref() {
        return xref;
    }

    public void setXref(String xref) {
        this.xref = xref;
    }

    public int getTotalParts() {
        return totalParts;
    }

    public void setTotalParts(int totalParts) {
        this.totalParts = totalParts;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getProcStat() {
        return procStat;
    }

    public void setProcStat(int procStat) {
        this.procStat = procStat;
    }

    public int getProcAttempts() {
        return procAttempts;
    }

    public void setProcAttempts(int procAttempts) {
        this.procAttempts = procAttempts;
    }

    public Long getRegexId() {
        return regexId;
    }

    public void setRegexId(Long regexId) {
        this.regexId = regexId;
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public int getRelPart() {
        return relPart;
    }

    public void setRelPart(int relPart) {
        this.relPart = relPart;
    }

    public int getRelTotalPart() {
        return relTotalPart;
    }

    public void setRelTotalPart(int relTotalPart) {
        this.relTotalPart = relTotalPart;
    }

    public String getBinaryHash() {
        return binaryHash;
    }

    public void setBinaryHash(String binaryHash) {
        this.binaryHash = binaryHash;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getImportName() {
        return importName;
    }

    public void setImportName(String importName) {
        this.importName = importName;
    }

    public Long getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Long releaseId) {
        this.releaseId = releaseId;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Transient
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Transient
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Transient
    public int getNumberParts() {
        return numberParts;
    }

    public void setNumberParts(int numberParts) {
        this.numberParts = numberParts;
    }

    @Transient
    public String getReleaseGuid() {
        return releaseGuid;
    }

    public void setReleaseGuid(String releaseGuid) {
        this.releaseGuid = releaseGuid;
    }
}
