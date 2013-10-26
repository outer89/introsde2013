/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import beans.People;
import beans.Person;
import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Lorenzo
 */
public class MainTest {

    private static final String PERSON_XML_PATH = "./persone-jaxb.xml";

    public static void main(String[] args) throws JAXBException {

        ArrayList<Person> lista = new ArrayList<Person>();
        Person p = new Person();
        p.setFirstname("Lorenzo");
        p.setLastname("Massimo");
        lista.add(p);
        Person p1 = new Person();
        p1.setFirstname("Lorenzo");
        p1.setLastname("M");
        lista.add(p1);
        Person p2 = new Person();
        p2.setFirstname("L");
        p2.setLastname("Massimo");
        lista.add(p2);
        People people = new People();
        people.setListaPersone(lista);
//        JAXBContext context = JAXBContext.newInstance(Person.class);
//        Marshaller m = context.createMarshaller();
//        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//        m.marshal(p, System.out);
        JAXBContext context1 = JAXBContext.newInstance(People.class);
        Marshaller m1 = context1.createMarshaller();
        m1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m1.marshal(people,System.out);
        
        m1.marshal(people, new File(PERSON_XML_PATH));
        
        
    }
}
