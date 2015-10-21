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

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                '}';
    }
}
