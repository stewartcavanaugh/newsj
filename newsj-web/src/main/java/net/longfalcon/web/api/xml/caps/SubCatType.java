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
public class SubCatType {

    @XmlAttribute
    protected int id;

    @XmlAttribute
    protected String name;

    public SubCatType() {
    }

    public SubCatType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
