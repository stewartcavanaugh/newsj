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
