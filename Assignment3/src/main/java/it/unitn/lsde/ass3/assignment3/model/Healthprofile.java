package it.unitn.lsde.ass3.assignment3.model;


import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Healthprofile  implements java.io.Serializable {

     private Integer idtabhealthprofile;
     @XmlTransient
     private Person tabperson;
     private Date date;
     private double weight;
     private double height;
     private int steps;
     private int calories;

    public Healthprofile() {
    }

    public Healthprofile(Person tabperson, Date date, double weight, double height, int steps, int calories) {
       this.tabperson = tabperson;
       this.date = date;
       this.weight = weight;
       this.height = height;
       this.steps = steps;
       this.calories = calories;
    }
   
    public Integer getIdtabhealthprofile() {
        return this.idtabhealthprofile;
    }
    
    public void setIdtabhealthprofile(Integer idtabhealthprofile) {
        this.idtabhealthprofile = idtabhealthprofile;
    }
    public Person getTabperson() {
        return this.tabperson;
    }
    
    public void setTabperson (Person tabperson) {
        this.tabperson = tabperson;
    }
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    public double getWeight() {
        return this.weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public double getHeight() {
        return this.height;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
    public int getSteps() {
        return this.steps;
    }
    
    public void setSteps(int steps) {
        this.steps = steps;
    }
    public int getCalories() {
        return this.calories;
    }
    
    public void setCalories(int calories) {
        this.calories = calories;
    }

}


