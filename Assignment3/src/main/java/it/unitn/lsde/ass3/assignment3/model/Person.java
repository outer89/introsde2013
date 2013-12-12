package it.unitn.lsde.ass3.assignment3.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements java.io.Serializable {

    private Integer idperson;
    private String name;
    private String surname;
    private Date birthday;

    private Healthprofile healthprofile;

    @XmlTransient
    private Set<Healthprofile> tabhealthprofiles = new HashSet<Healthprofile>(0);

    public Person() {

    }

    public Person(String name, String surname, Date birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }

    public Person(String name, String surname, Date birthday, Set<Healthprofile> tabhealthprofiles) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.tabhealthprofiles = tabhealthprofiles;

    }

    public Integer getIdperson() {
        return this.idperson;
    }

    public void setIdperson(Integer idperson) {
        this.idperson = idperson;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set getTabhealthprofiles() {
        return this.tabhealthprofiles;
    }

    public void setTabhealthprofiles(Set<Healthprofile> tabhealthprofiles) {
        this.tabhealthprofiles = tabhealthprofiles;
    }

    /**
     * @return the healthprofile
     */
    public Healthprofile getHealthprofile() {
        return healthprofile;
    }

    /**
     * @param healthprofile the healthprofile to set
     */
    public void setHealthprofile(Healthprofile healthprofile) {
        this.healthprofile = healthprofile;
    }


}
