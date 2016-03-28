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

package net.longfalcon.web.api.xml.caps;

import javax.xml.bind.annotation.*;

/**
 * User: longfalcon
 * Date: 3/1/16
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServerType")
public class ServerType {

    @XmlAttribute(name = "appversion")
    protected String appVersion;

    @XmlAttribute
    protected String version;

    @XmlAttribute
    protected String title;

    @XmlAttribute
    protected String strapline;

    @XmlAttribute
    protected String email;

    @XmlAttribute
    protected String url;

    @XmlAttribute
    protected String image;

    public ServerType() {
    }

    public ServerType(String appVersion, String version, String title, String strapline, String email, String url, String image) {
        this.appVersion = appVersion;
        this.version = version;
        this.title = title;
        this.strapline = strapline;
        this.email = email;
        this.url = url;
        this.image = image;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStrapline() {
        return strapline;
    }

    public void setStrapline(String strapline) {
        this.strapline = strapline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
