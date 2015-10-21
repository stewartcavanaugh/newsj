package net.longfalcon.newsj.model;

/**
 * Nonpersistent representation of a part
 * User: Sten Martinez
 * Date: 10/7/15
 * Time: 6:05 PM
 */
public class MessagePart {
    private int partNumber;
    private long articleNumber;
    private String messageId;
    private int size;

    public MessagePart(int partNumber, long articleNumber, String messageId, int size) {
        this.partNumber = partNumber;
        this.articleNumber = articleNumber;
        this.messageId = messageId;
        this.size = size;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    public long getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(long articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
