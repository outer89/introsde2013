/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unitn.assignment2.utils;

import it.unitn.assignment2.model.Measure;
import java.util.Comparator;

/**
 *
 * @author Lorenzo
 */
public class MeasureListComparator implements Comparator<Measure>{

    public int compare(Measure o1, Measure o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
    
}
