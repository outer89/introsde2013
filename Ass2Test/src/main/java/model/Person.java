// classes are grouped together in 'packages'
// Classes in the same pakage already see each other. 
// If a class is in another package, in other to see it, you need to import it
package model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

// This is a typical Java Class. 
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    private String id;
    private String firstname;
    private String lastname;
    private HealthProfile hProfile;
    @XmlTransient
    private List<Measure> listaMisure;
    
    public Person(String id, String fname, String lname) {
        this.setId(id);
        this.setFirstname(fname);
        this.setLastname(lname);
    }

    public Person(String fname, String lname, HealthProfile hp) {
        this.setFirstname(fname);
        this.setLastname(lname);
        this.hProfile = hp;
    }

    public Person(String fname, String lname) {
        this.setFirstname(fname);
        this.setLastname(lname);
        this.hProfile = new HealthProfile();
    }

    public Person() {
        this.firstname = "Pinco";
        this.lastname = "Pallino";
        this.hProfile = new HealthProfile();
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
}
