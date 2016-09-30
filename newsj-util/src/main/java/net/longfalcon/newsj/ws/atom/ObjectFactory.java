//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.12 at 05:59:54 PM PDT 
//


package net.longfalcon.newsj.ws.atom;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.w3._2005.atom package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Rights_QNAME = new QName("http://www.w3.org/2005/Atom", "rights");
    private final static QName _Subtitle_QNAME = new QName("http://www.w3.org/2005/Atom", "subtitle");
    private final static QName _Author_QNAME = new QName("http://www.w3.org/2005/Atom", "author");
    private final static QName _Contributor_QNAME = new QName("http://www.w3.org/2005/Atom", "contributor");
    private final static QName _Summary_QNAME = new QName("http://www.w3.org/2005/Atom", "summary");
    private final static QName _Email_QNAME = new QName("http://www.w3.org/2005/Atom", "email");
    private final static QName _Updated_QNAME = new QName("http://www.w3.org/2005/Atom", "updated");
    private final static QName _Uri_QNAME = new QName("http://www.w3.org/2005/Atom", "uri");
    private final static QName _Title_QNAME = new QName("http://www.w3.org/2005/Atom", "title");
    private final static QName _Published_QNAME = new QName("http://www.w3.org/2005/Atom", "published");
    private final static QName _Name_QNAME = new QName("http://www.w3.org/2005/Atom", "name");
    private final static QName _EntryContent_QNAME = new QName("http://www.w3.org/2005/Atom", "content");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.w3._2005.atom
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Entry }
     * 
     */
    public Entry createEntry() {
        return new Entry();
    }

    /**
     * Create an instance of {@link AtomTextConstruct }
     * 
     */
    public AtomTextConstruct createAtomTextConstruct() {
        return new AtomTextConstruct();
    }

    /**
     * Create an instance of {@link AtomPersonConstruct }
     * 
     */
    public AtomPersonConstruct createAtomPersonConstruct() {
        return new AtomPersonConstruct();
    }

    /**
     * Create an instance of {@link Icon }
     * 
     */
    public Icon createIcon() {
        return new Icon();
    }

    /**
     * Create an instance of {@link Link }
     * 
     */
    public Link createLink() {
        return new Link();
    }

    /**
     * Create an instance of {@link UndefinedContent }
     * 
     */
    public UndefinedContent createUndefinedContent() {
        return new UndefinedContent();
    }

    /**
     * Create an instance of {@link Generator }
     * 
     */
    public Generator createGenerator() {
        return new Generator();
    }

    /**
     * Create an instance of {@link AtomDateConstruct }
     * 
     */
    public AtomDateConstruct createAtomDateConstruct() {
        return new AtomDateConstruct();
    }

    /**
     * Create an instance of {@link Source }
     * 
     */
    public Source createSource() {
        return new Source();
    }

    /**
     * Create an instance of {@link Category }
     * 
     */
    public Category createCategory() {
        return new Category();
    }

    /**
     * Create an instance of {@link Id }
     * 
     */
    public Id createId() {
        return new Id();
    }

    /**
     * Create an instance of {@link Logo }
     * 
     */
    public Logo createLogo() {
        return new Logo();
    }

    /**
     * Create an instance of {@link Feed }
     * 
     */
    public Feed createFeed() {
        return new Feed();
    }

    /**
     * Create an instance of {@link Entry.Content }
     * 
     */
    public Entry.Content createEntryContent() {
        return new Entry.Content();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "rights")
    public JAXBElement<AtomTextConstruct> createRights(AtomTextConstruct value) {
        return new JAXBElement<AtomTextConstruct>(_Rights_QNAME, AtomTextConstruct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "subtitle")
    public JAXBElement<AtomTextConstruct> createSubtitle(AtomTextConstruct value) {
        return new JAXBElement<AtomTextConstruct>(_Subtitle_QNAME, AtomTextConstruct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomPersonConstruct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "author")
    public JAXBElement<AtomPersonConstruct> createAuthor(AtomPersonConstruct value) {
        return new JAXBElement<AtomPersonConstruct>(_Author_QNAME, AtomPersonConstruct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomPersonConstruct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "contributor")
    public JAXBElement<AtomPersonConstruct> createContributor(AtomPersonConstruct value) {
        return new JAXBElement<AtomPersonConstruct>(_Contributor_QNAME, AtomPersonConstruct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "summary")
    public JAXBElement<AtomTextConstruct> createSummary(AtomTextConstruct value) {
        return new JAXBElement<AtomTextConstruct>(_Summary_QNAME, AtomTextConstruct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "email")
    public JAXBElement<String> createEmail(String value) {
        return new JAXBElement<String>(_Email_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomDateConstruct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "updated")
    public JAXBElement<AtomDateConstruct> createUpdated(AtomDateConstruct value) {
        return new JAXBElement<AtomDateConstruct>(_Updated_QNAME, AtomDateConstruct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "uri")
    public JAXBElement<String> createUri(String value) {
        return new JAXBElement<String>(_Uri_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "title")
    public JAXBElement<AtomTextConstruct> createTitle(AtomTextConstruct value) {
        return new JAXBElement<AtomTextConstruct>(_Title_QNAME, AtomTextConstruct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomDateConstruct }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "published")
    public JAXBElement<AtomDateConstruct> createPublished(AtomDateConstruct value) {
        return new JAXBElement<AtomDateConstruct>(_Published_QNAME, AtomDateConstruct.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Entry.Content }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "content", scope = Entry.class)
    public JAXBElement<Entry.Content> createEntryContent(Entry.Content value) {
        return new JAXBElement<Entry.Content>(_EntryContent_QNAME, Entry.Content.class, Entry.class, value);
    }

}
