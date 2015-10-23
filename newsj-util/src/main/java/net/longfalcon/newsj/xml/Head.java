
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
    "meta"
})
@XmlRootElement(name = "head")
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-10-22T10:31:14-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class Head {

    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-10-22T10:31:14-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<Meta> meta;

    /**
     * Gets the value of the meta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the meta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Meta }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-10-22T10:31:14-07:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<Meta> getMeta() {
        if (meta == null) {
            meta = new ArrayList<Meta>();
        }
        return this.meta;
    }

}
