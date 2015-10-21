package net.longfalcon.newsj.model;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 4:33 PM
 */
public class BinaryBlacklistEntry {
    private long id;
    private String groupName;
    private String regex;
    private int msgCol;
    private int opType;
    private int status;
    private String description;

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

    public int getMsgCol() {
        return msgCol;
    }

    public void setMsgCol(int msgCol) {
        this.msgCol = msgCol;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
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
}
