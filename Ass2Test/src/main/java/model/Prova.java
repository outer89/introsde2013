/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lorenzo
 */

public class Prova {
    private int ciaograzie;

    /**
     * @return the ciaograzie
     */
    public int getCiaograzie() {
        return ciaograzie;
    }

    /**
     * @param ciaograzie the ciaograzie to set
     */
    public void setCiaograzie(int ciaograzie) {
        this.ciaograzie = ciaograzie;
    }
}
