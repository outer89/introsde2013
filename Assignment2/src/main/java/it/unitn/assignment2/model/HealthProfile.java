package it.unitn.assignment2.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HealthProfile {

    private double weight; 
    private double height; 

    public HealthProfile(double weight, double height) {
        this.weight = weight;
        this.height = height;
    }

    public HealthProfile() {
        Double maxheight = 2.54;
        Double minheight = 1.00;
        Double rangeheight = (maxheight - minheight);
        Double maxweight = 200.00;
        Double minweight = 50.00;
        Double rangeweight = (maxweight - minweight);
        this.weight = Math.floor((Math.random() * rangeweight + 1) * 100) / 100;
        this.height = Math.floor((Math.random() * rangeheight + 1) * 100) / 100;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String toString() {
        return "Height=" + height + ", Weight=" + weight;
    }

}
