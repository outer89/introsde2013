/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.lsde.ass3.assignment3;

import it.unitn.lsde.ass3.assignment3.model.Healthprofile;
import it.unitn.lsde.ass3.assignment3.model.Person;
import it.unitn.lsde.ass3.dao.PersonDao;
import javax.jws.WebService;

/**
 *
 * @author Lorenzo
 */
@WebService(endpointInterface = "it.unitn.lsde.ass3.assignment3.Ass3Services", serviceName = "soapService")
public class ServiceImpl implements Ass3Services {

    public Person readPerson(int id) throws Exception {
        PersonDao p = new PersonDao();
        Person res = p.getPerson(id);
        if (res == null) {
            //throw new Exception("no such person");
            throw new Exception("No such person");
        } else {
            int size = res.getTabhealthprofiles().size();
            System.out.println("size " + size);
            if(size>0){
                Healthprofile latesthp = null;
                Object[] history = res.getTabhealthprofiles().toArray();
                latesthp = (Healthprofile)history[size-1];
                res.setHealthprofile(latesthp);
            }
            return res;
        }
    }

    public int addPerson(Person person) {
        PersonDao p = new PersonDao();
        return p.createPerson(person);
    }

    public int updatePerson(Person person) {
        PersonDao p = new PersonDao();
        return p.updatePerson(person);
    }

    public int deletePerson(int id) {
        PersonDao p = new PersonDao();
        return p.deletePerson(id);
    }

    public int updatePersonHP(int id, Healthprofile hp) {
        return 0;
    }

}
