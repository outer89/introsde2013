/*
 <person>
                <firstname>George R. R.</firstname>            
                <lastname>Martin</lastname> 
                <healthprofile>
                    <weitgh>120</weitgh>
                    <height>1.65</height> 
                </healthprofile>
            </person>

 */

package beans;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Lorenzo
 */
@XmlRootElement(name = "person")
@XmlType(propOrder = { "firstname", "lastname", "healthprofile" })
public class Person {
    private String firstname;
    private String lastname;
    private HealthProfile healthprofile;
    
    public Person(){
       //default
        this.firstname = "Jhon";
        this.lastname = "Doe";
        this.healthprofile = new HealthProfile();
    }
    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the healthprofile
     */
    public HealthProfile getHealthprofile() {
        return healthprofile;
    }

    /**
     * @param healthprofile the healthprofile to set
     */
    public void setHealthprofile(HealthProfile healthprofile) {
        this.healthprofile = healthprofile;
    }
}
