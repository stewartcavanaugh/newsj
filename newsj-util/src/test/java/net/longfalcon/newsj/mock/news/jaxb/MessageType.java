
package net.longfalcon.newsj.mock.news.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="messageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="from" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subject" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="number" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageType", propOrder = {
        "id",
        "date",
        "from",
        "subject",
        "content"
})
public class MessageType {

    protected String id;
    protected String date;
    protected String from;
    @XmlElement(required = true)
    protected String subject;
    protected String content;
    @XmlAttribute(name = "number")
    protected Long number;

    /**
     * Gets the value of the id property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the date property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the from property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFrom(String value) {
        this.from = value;
    }

    /**
     * Gets the value of the subject property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * Gets the value of the content property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the number property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setNumber(Long value) {
        this.number = value;
    }

}