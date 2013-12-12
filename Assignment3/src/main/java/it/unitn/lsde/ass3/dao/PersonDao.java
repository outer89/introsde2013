/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.lsde.ass3.dao;

import it.unitn.lsde.ass3.assignment3.model.Healthprofile;
import it.unitn.lsde.ass3.assignment3.model.Person;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Lorenzo
 */
public class PersonDao extends DaoBase {

    public Person getPerson(int id) {
        System.out.println("id: " + id);
        Person res = null;
        try {
            Session s = getSession();
            Criteria c = s.createCriteria(Person.class);
            c.add(Restrictions.idEq(id));
            DetachedCriteria subcriteria = DetachedCriteria.forClass(Healthprofile.class);
            subcriteria.setProjection(Projections.max("date"));
            c.add(Property.forName("date").eq(subcriteria));
            
            res = (Person) c.uniqueResult();
        } catch (Exception e) {
            res = null;
        } finally {
            return res;
        }
    }
    /**
     * 
     * @param p peson to be updated
     * @return id of the person updated or -1 in case of problems
     */
    public Integer updatePerson(Person p) {
        Session s = getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.update(p);
            s.flush();
            tx.commit();
            return p.getIdperson();
        } catch (Exception e) {
            tx.rollback();
            return -1;
        }
    }

    /**
     *
     * @param id of the person to be deleted
     * @return -1 if person does not exist, -2 if problem deleting person, 0 if
     * everything ok
     */
    public Integer deletePerson(Integer id) {
        Session s = getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();;
            Person p = getPerson(id);
            if (p == null) {
                return -1;
            }
            s.delete(p);
            s.flush();
            tx.commit();
            return 0;
        } catch (Exception e) {
            tx.rollback();
            return -2;
        }
    }

    /**
     * Id of person is overwritten and returned if person can be created
     *
     * @param p
     * @return id of the person created, -1 in case of problems
     */
    public Integer createPerson(Person p) {
        Session s = getSession();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            //scremo p, gli tolgo l'id
            p.setIdperson(null);
            s.save(p);
            s.flush();
            t.commit();
            System.out.println(p.getIdperson());
            return p.getIdperson();
        } catch (Exception e) {
            t.rollback();
            return -1;
        } finally {
            s.close();
        }
    }
}
