
package it.unitn.lsde.ass3.assignment3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for history complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="history">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://assignment3.ass3.lsde.unitn.it/}healthprofile" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "history", propOrder = {
    "healthprofile"
})
public class History {

    protected ArrayList<Healthprofile> healthprofile;

    /**
     * Gets the value of the healthprofile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the healthprofile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHealthprofile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Healthprofile }
     * 
     * 
     */
    public ArrayList<Healthprofile> getHealthprofile() {
        if (healthprofile == null) {
            healthprofile = new ArrayList<Healthprofile>();
        }
        return this.healthprofile;
    }

}
