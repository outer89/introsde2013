/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unitn.lsde.ass3.dao;

import it.unitn.lsde.ass3.assignment3.model.Healthprofile;
import it.unitn.lsde.ass3.assignment3.model.Person;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Lorenzo
 */
public class HpDao extends DaoBase{
    
    public List getHistory(int idPerson){
        Session s = getSession();
        Transaction tx = null;
        try {
            tx= s.beginTransaction();
            Criteria c = s.createCriteria(Healthprofile.class);
            Person p = new Person();
            p.setIdperson(idPerson);
            c.add(Restrictions.eq("tabperson", p));
            return c.list();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return null;
        }
        finally{
            s.close();
        }
        
    }
}
