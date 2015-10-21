package net.longfalcon.newsj.model;

/**
 * User: Sten Martinez
 * Date: 10/8/15
 * Time: 8:07 AM
 */
public class PartRepair {
    private long id;
    private long numberId;
    private long groupId;
    private int attempts;
    private Part part;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumberId() {
        return numberId;
    }

    public void setNumberId(long numberId) {
        this.numberId = numberId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
}
