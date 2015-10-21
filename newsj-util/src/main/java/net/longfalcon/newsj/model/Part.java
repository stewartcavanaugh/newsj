package net.longfalcon.newsj.model;

import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 11:45 PM
 */
public class Part {
    private long id;
    private long binaryId;
    private String messageId;
    private long number;
    private int partNumber;
    private long size;
    private Date dateAdded;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBinaryId() {
        return binaryId;
    }

    public void setBinaryId(long binaryId) {
        this.binaryId = binaryId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
