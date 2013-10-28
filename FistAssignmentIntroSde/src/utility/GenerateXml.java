/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import beans.HealthProfile;
import beans.People;
import beans.Person;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author Lorenzo
 */
public class GenerateXml {

    static final String[] nomi = {"Brittni", "Cassie", "Dwight", "Shana", "Emma",
        "Carol", "Georgianne", "Rebekah", "Deangelo", "Clinton",
        "Micaela", "Geraldo", "Leonor", "Yanira", "Latoyia",
        "Ignacia", "Ariana", "Raymond", "Stephen", "Reta", "Mattia", "Carlo", "Franco", "Lorenzo", "Massimo",
        "Massimiliano", "Giorgio", "Iolanda", "Michela", "Giorgia", "Franca", "Ilona", "Valentina"};
    static final String[] cognomi = {"North", "Ogden", "Oliver", "Paige", "Parr",
        "Parsons", "Paterson", "Payne", "Peake", "Peters", "Piper", "Poole", "Powell",
        "Pullman", "Quinn", "Rampling", "Randall", "Rees", "Reid", "Roberts", "Robertson",
        "Ross", "Russell", "Rutherford", "Sanderson", "Scott", "Rossi", "Pittarello", "DeAgostini", "Agostini", "Ferrari", "Bianchi"};

    public boolean generateXml(String path) {
        boolean res = false;
        try {
            //create context
            JAXBContext context = JAXBContext.newInstance(People.class);
            //get marshaller and set an output well formatted
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            People people = new People();
            ArrayList<Person> temp = new ArrayList<>();
            //generate for each name and each surname a person with a random profile
            for (String nome : nomi) {
                for (String cognome : cognomi) {
                    Person person = new Person();
                    person.setFirstname(nome);
                    person.setLastname(cognome);
                    person.setHealthprofile(new HealthProfile());
                    temp.add(person);
                }
            }
            Collections.shuffle(temp);
            people.setListaPersone(temp);
            
            //write-marshall to file
            m.marshal(people, new File(path));
            res = true;
        } catch (JAXBException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return res;

    }
    
}
