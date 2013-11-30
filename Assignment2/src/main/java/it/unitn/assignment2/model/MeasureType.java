/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.assignment2.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lorenzo
 */
public enum MeasureType {

    HEIGHT, WEIGHT;

    public static boolean contains(String measure) {
        for (MeasureType measureType : MeasureType.values()) {
            if (measureType.toString().toLowerCase().equals(measure.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static MeasureType getType(String measure) {
        if (measure == null) {
            return null;
        }
        for (MeasureType measureType : MeasureType.values()) {
            if (measureType.toString().toLowerCase().equals(measure.toLowerCase())) {
                return measureType;
            }
        }
        return null;
    }
}
