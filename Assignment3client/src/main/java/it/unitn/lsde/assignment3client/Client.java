/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.lsde.assignment3client;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import it.unitn.lsde.ass3.assignment3.Ass3Services;
import it.unitn.lsde.ass3.assignment3.Exception_Exception;
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
        int i = c.createPerson();
        c.readPerson(1);
        if (i > 0) {
            c.deletePerson(i);
        }
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
        } catch (Exception_Exception ex) {
            //nothing, this should not report exception
        }
    }

    public void updatePerson() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void getHistory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updateHealthprofile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addHealthprofile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
