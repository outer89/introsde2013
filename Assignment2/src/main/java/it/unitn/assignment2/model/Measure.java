/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unitn.assignment2.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lorenzo
 */


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Measure{
    
    @XmlTransient
    private MeasureType type;
    private String mid;
    private String value;
    @XmlElement(name = "created")
    private Date date;

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the id
     */
    public String getMid() {
        return mid;
    }

    /**
     * @param id the id to set
     */
    public void setMid(String mid) {
        this.mid = mid;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the type
     */
    public MeasureType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MeasureType type) {
        this.type = type;
    }
    
}
