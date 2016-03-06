package net.longfalcon.web.api.xml.caps;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * User: longfalcon
 * Date: 3/3/16
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CategoryType {

    @XmlAttribute
    protected int id;

    @XmlAttribute
    protected String name;

    @XmlElement(name = "subcat")
    protected List<SubCatType> subCategories;

    public CategoryType() {
    }

    public CategoryType(int id, String name, List<SubCatType> subCategories) {
        this.id = id;
        this.name = name;
        this.subCategories = subCategories;
    }

    public List<SubCatType> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCatType> subCategories) {
        this.subCategories = subCategories;
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
