
package it.unitn.lsde.ass3.assignment3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.unitn.lsde.ass3.assignment3 package. 
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

    private final static QName _Exception_QNAME = new QName("http://assignment3.ass3.lsde.unitn.it/", "Exception");
    private final static QName _Person_QNAME = new QName("http://assignment3.ass3.lsde.unitn.it/", "person");
    private final static QName _Healthprofile_QNAME = new QName("http://assignment3.ass3.lsde.unitn.it/", "healthprofile");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.unitn.lsde.ass3.assignment3
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link Healthprofile }
     * 
     */
    public Healthprofile createHealthprofile() {
        return new Healthprofile();
    }

    /**
     * Create an instance of {@link History }
     * 
     */
    public History createHistory() {
        return new History();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assignment3.ass3.lsde.unitn.it/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assignment3.ass3.lsde.unitn.it/", name = "person")
    public JAXBElement<Person> createPerson(Person value) {
        return new JAXBElement<Person>(_Person_QNAME, Person.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Healthprofile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://assignment3.ass3.lsde.unitn.it/", name = "healthprofile")
    public JAXBElement<Healthprofile> createHealthprofile(Healthprofile value) {
        return new JAXBElement<Healthprofile>(_Healthprofile_QNAME, Healthprofile.class, null, value);
    }

}
