package net.longfalcon.web.api.xml.caps;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * User: longfalcon
 * Date: 3/1/16
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RegistrationType {

    @XmlAttribute
    protected String available;

    @XmlAttribute
    protected String open;

    public RegistrationType() {
    }

    public RegistrationType(boolean available, boolean open) {
        this.available = available ? "yes" : "no";
        this.open = open ? "yes" : "no";
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
}
