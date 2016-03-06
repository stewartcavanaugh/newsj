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
