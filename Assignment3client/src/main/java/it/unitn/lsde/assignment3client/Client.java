/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.lsde.assignment3client;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import it.unitn.lsde.ass3.assignment3.Ass3Services;
import it.unitn.lsde.ass3.assignment3.Exception_Exception;
import it.unitn.lsde.ass3.assignment3.Healthprofile;
import it.unitn.lsde.ass3.assignment3.History;
import it.unitn.lsde.ass3.assignment3.Person;
import it.unitn.lsde.testinterface.ServiceTest;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;

/**
 *
 * @author Lorenzo
 */
public class Client implements ServiceTest {

    Ass3Services ass3;

    public static void main(String[] args) {
        Client c = new Client();
       /* int i = c.createPerson();
        c.readPerson(1);
        if (i > 0) {
            c.deletePerson(i);
        }
        c.updatePerson();
        c.getHistory();*/
        c.updateHealthprofile();
      //  c.addHealthprofile();
    }
    

    public Client() {
        init();
    }

    public void init() {
        try {
            URL url;
            url = new URL("http://localhost:5914/ws/soap?wsdl");
            QName qname = new QName("http://assignment3.ass3.lsde.unitn.it/", "soapService");
            Service service = Service.create(url, qname);
            ass3 = service.getPort(Ass3Services.class);

        } catch (MalformedURLException ex) {
            System.out.println("errore url");
            ex.printStackTrace();
        }
    }

    public void readPerson(int id) {
        System.out.println("read person");
        try {
            Person p = ass3.readPerson(id);
            printPerson(p);
        } catch (Exception_Exception ex) {
            System.out.println("NO SUCH PERSON");
        }

    }

    private void printPerson(Person p) {
        System.out.println(p.getName());
    }

    public int createPerson() {
        System.out.println("create person");
        int response = -1;
        try {
            Person p = new Person();
            p.setName("Jhon");
            p.setSurname("Doe");
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(new Date());
            XMLGregorianCalendar c;
            c = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
            p.setBirthday(c);

            int res = ass3.createPerson(p);
            response = res;
            System.out.println("result is : " + res);
            return res;
        } catch (DatatypeConfigurationException ex) {
            ex.printStackTrace();
            return response;
        } catch (Exception_Exception ex) {
            //nothing
            return response;
        }

    }

    public void deletePerson(int id) {
        System.out.println("delete person");
        Holder<Integer> h = new Holder<Integer>(id);
        try {
            ass3.deletePerson(h);
            System.out.println("deleted");
        } catch (Exception_Exception ex) {
            //nothing, this should not report exception
        }
    }

    public void updatePerson() {
        System.out.println("update person");
        try {
            Person p = ass3.readPerson(1);
            p.setName("nananana batman");
            int res = ass3.updatePerson(p);
            System.out.println("updated result " + res);
        } catch (Exception_Exception ex) {
            System.out.println("NO SUCH PERSON");
        }

    }

    public void getHistory() {
        System.out.println("get history");
        try {
            History h = ass3.getHistory(1);
            printHistory(h);
        } catch (Exception_Exception ex) {
            //nothing
        }
    }

    private void printHistory(History h) {
        for (Healthprofile healthprofile : h.getHealthprofile()) {
            System.out.println(healthprofile.getDate());
        }
    }

    public void updateHealthprofile() {
        System.out.println("update healthprofile");
        Healthprofile hp = null;
        try {
            Person p = ass3.readPerson(1);
            hp = (Healthprofile) p.getHealthprofile().toArray()[0];
            hp.setHeight(888);
            int res = ass3.updatePersonHealthProfile(1, hp);
            System.out.println("update hp res " + res);
        } catch (Exception_Exception ex) {
            System.out.println("NO SUCH PERSON");
        }

    }

    public void addHealthprofile() {
        System.out.println("add health profile");
        try {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(new Date());
            XMLGregorianCalendar c;
            c = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
            Healthprofile hp = new Healthprofile();
            hp.setDate(c); hp.setCalories(200);hp.setHeight(200);hp.setSteps(200);hp.setSteps(200);hp.setWeight(200);
            int res = ass3.addPersonHealthProfile(1, hp);
            System.out.println("add hp res " + res);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception_Exception ex) {
            //nothing
        }
    }
}
