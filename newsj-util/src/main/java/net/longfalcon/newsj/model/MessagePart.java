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
