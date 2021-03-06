// classes are grouped together in 'packages'
// Classes in the same pakage already see each other. 
// If a class is in another package, in other to see it, you need to import it
package it.unitn.assignment2.model;

import it.unitn.assignment2.utils.DateAdapter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

// This is a typical Java Class. 
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    private String id;
    private String firstname;
    private String lastname;
    @XmlElement(name = "healthProfile")
    private HealthProfile hProfile;
    @XmlElement(name = "birthday")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date bday;
    @XmlTransient
    private List<Measure> listaMisure;

    public Person() {
        this.firstname = "Lorenzo";
        this.lastname = "Gramola";
        this.hProfile = new HealthProfile();
        listaMisure = new LinkedList<Measure>();    
        this.setBday(new Date());
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public HealthProfile gethProfile() {
        return hProfile;
    }

    public void sethProfile(HealthProfile hProfile) {
        this.hProfile = hProfile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the listaMisure
     */
    public List<Measure> getListaMisure() {
        return listaMisure;
    }

    /**
     * @param listaMisure the listaMisure to set
     */
    public void setListaMisure(List<Measure> listaMisure) {
        this.listaMisure = listaMisure;
    }

    /**
     * @return the bday
     */
    public Date getBday() {
        return bday;
    }

    /**
     * @param bday the bday to set
     */
    public void setBday(Date bday) {
        this.bday = bday;
    }
}

