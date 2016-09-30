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

package net.longfalcon.view;

/**
 * User: Sten Martinez
 * Date: 9/22/16
 * Time: 11:07 AM
 */
public class SitemapItemView {
    private String type;
    private String name;
    private String location;
    private float priority;
    private String changeFrequency;

    public SitemapItemView(String type, String name, String location, float priority, String changeFrequency) {
        this.type = type;
        this.name = name;
        this.location = location;
        this.priority = priority;
        this.changeFrequency = changeFrequency;
    }

    public SitemapItemView(String type, String name, String location) {
        this.type = type;
        this.name = name;
        this.location = location;
        this.changeFrequency = "daily";
        this.priority = (float) 1.0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }

    public String getChangeFrequency() {
        return changeFrequency;
    }

    public void setChangeFrequency(String changeFrequency) {
        this.changeFrequency = changeFrequency;
    }
}
