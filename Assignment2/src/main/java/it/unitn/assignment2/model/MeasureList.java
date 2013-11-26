/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unitn.assignment2.model;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lorenzo
 */
@XmlRootElement(name = "measure-history")
public class MeasureList{
    
    private List<Measure> lista = new LinkedList<Measure>();

    /**
     * @return the lista
     */
    @XmlElement(name = "measure")
    public List<Measure> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List<Measure> lista) {
        this.lista = lista;
    }
    
}
