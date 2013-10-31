/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package part2;

import beans.People;
import beans.Person;
import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import utility.GenerateXml;

/**
 *
 * @author Lorenzo
 */
public class Part2 {

    private static final String XSD_PATH = "./src/xsdschema/xsdschemapart2.xsd";
    private static final String PERSON_XML_PATH = "./persone-jaxb.xml";

    public static void main(String[] args) throws JAXBException {
        Part2 p = new Part2();
        p.scrivi();
        p.leggi();
    }

    public void validate() throws JAXBException {
        People p = new People();
        p.setListaPersone(new utility.GenerateXml().casualPeople());
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;
        try {
            File f = new File(XSD_PATH);
            schema = schemaFactory.newSchema(f);
        } catch (SAXException ex) {
            System.err.println("Schema file non trovato");
        }
        JAXBContext context = JAXBContext.newInstance(People.class);
        Marshaller marshaller = context.createMarshaller();
        if (schema == null) {
            System.out.println("validation interrupted, no schema set");
            return;
        }
        marshaller.setSchema(schema);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(p, System.out);
    }

    public void leggi() throws JAXBException {
        System.out.println("leggo e convalido l'xml con lo schema xsd");
        JAXBContext jaxbContext = JAXBContext.newInstance(People.class);
        Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema = null;
        try {
            schema = schemaFactory.newSchema(new File(XSD_PATH));
        } catch (SAXException ex) {
            System.err.println("schema not found");
        }
        if (schema == null) {
            System.out.println("unmarshalling interrupted, no schema set");
            return;
        }
        unMarshaller.setSchema(schema);
        People p = (People) unMarshaller.unmarshal(new File(PERSON_XML_PATH));
        System.out.println("name and surname of first 5 person");
        for (int i = 0; i < 5 && i < p.getListaPersone().size(); i++) {
            Person persona = p.getListaPersone().get(i);
            System.out.printf("person %d\n", i);
            System.out.printf("name %s\n", persona.getFirstname());
            System.out.printf("surname %s\n", persona.getLastname());
            System.out.println("");
        }
    }
    public void scrivi(){
        System.out.println("creating xml file with persons...");
        GenerateXml gxml = new GenerateXml();
        Boolean res = gxml.generateXml(PERSON_XML_PATH);
        System.out.println("xml generation had a positive end: " + res);
    }
}
