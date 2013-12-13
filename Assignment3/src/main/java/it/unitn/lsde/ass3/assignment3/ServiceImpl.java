/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.lsde.ass3.assignment3;

import it.unitn.lsde.assignment3.interfaces.Ass3Services;
import it.unitn.lsde.ass3.assignment3.model.Healthprofile;
import it.unitn.lsde.ass3.assignment3.model.History;
import it.unitn.lsde.ass3.assignment3.model.Person;
import it.unitn.lsde.ass3.dao.HpDao;
import it.unitn.lsde.ass3.dao.PersonDao;
import java.util.ArrayList;
import javax.jws.WebService;

/**
 *
 * @author Lorenzo
 */
@WebService(endpointInterface = "it.unitn.lsde.assignment3.interfaces.Ass3Services", serviceName = "soapService")
public class ServiceImpl implements Ass3Services {

    public Person readPerson(int id) throws Exception {
        PersonDao p = new PersonDao();
        Person res = p.getPerson(id);
        if (res == null) {
            throw new Exception("no such person");
        } else {
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
        HpDao hpd = new HpDao();
        return hpd.updateHp(id, hp);
    }

    public History readHistory(int id) throws Exception {
        HpDao hp = new HpDao();
        History res = new History();
        ArrayList<Healthprofile> r = (ArrayList<Healthprofile>) hp.getHistory(id);
        res.setHistory(r);
                
        if (res == null) {
            throw new Exception("empty list");
        } else {
            return res;
        }
    }

    public int addPersonHP(int id, Healthprofile hp) throws Exception {
         HpDao hpd = new HpDao();
         return hpd.addHp(id, hp);
    }

}
