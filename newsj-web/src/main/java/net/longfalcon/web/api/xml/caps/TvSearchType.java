package net.longfalcon.web.api.xml.caps;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * User: longfalcon
 * Date: 3/3/16
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class TvSearchType {

    @XmlAttribute
    protected String available;

    public TvSearchType() {
    }

    public TvSearchType(boolean available) {
        this.available = available ? "yes" : "no";
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
