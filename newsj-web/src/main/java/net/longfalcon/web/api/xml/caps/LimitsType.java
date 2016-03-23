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
@XmlType(name = "LimitsType")
public class LimitsType {
    @XmlAttribute
    protected int max;

    @XmlAttribute(name = "default")
    protected int default_;

    public LimitsType() {
    }

    public LimitsType(int max, int default_) {
        this.max = max;
        this.default_ = default_;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getDefault() {
        return default_;
    }

    public void setDefault(int default_) {
        this.default_ = default_;
    }
}
