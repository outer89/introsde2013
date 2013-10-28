    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lorenzo
 */
//@XmlRootElement(name="people" , namespace = "com.blogspot.javafortheweb")
@XmlRootElement(name="people")
@XmlAccessorType(XmlAccessType.FIELD)
public class People {
    // XmLElementWrapper generates a wrapper element around XML representation
   // @XmlElementWrapper(name = "listaPersone")
   // XmlElement sets the name of the entities

    @XmlElement(name="person")
    private ArrayList<Person> listaPersone;
    
    /**
     * @return the listaPersone
     */
    public ArrayList<Person> getListaPersone() {
        return listaPersone;
    }

    /**
     * @param listaPersone the listaPersone to set
     */
    public void setListaPersone(ArrayList<Person> listaPersone) {
        this.listaPersone = listaPersone;
    }

}
