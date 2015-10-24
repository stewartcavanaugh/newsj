
package net.longfalcon.newsj.xml;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "group"
})
@XmlRootElement(name = "groups")
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-10-22T10:31:14-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class Groups {

    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-10-22T10:31:14-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<Group> group;

    /**
     * Gets the value of the group property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the group property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Group }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-10-22T10:31:14-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<Group> getGroup() {
        if (group == null) {
            group = new ArrayList<Group>();
        }
        return this.group;
    }

}
