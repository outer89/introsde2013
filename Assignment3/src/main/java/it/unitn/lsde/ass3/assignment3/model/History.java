/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unitn.lsde.ass3.assignment3.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 *
 * @author Lorenzo
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class History {
    @XmlElement(name="healthprofile")
    private ArrayList<Healthprofile> history;

    /**
     * @return the history
     */
    public ArrayList<Healthprofile> getHistory() {
        return history;
    }

    /**
     * @param history the history to set
     */
    public void setHistory(ArrayList<Healthprofile> history) {
        this.history = history;
    }
}
