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
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Lorenzo
 */
public class PersonDao extends DaoBase {

    public Person getPerson(int id) {
        Person res = null;
        try {
            Session s = getSession();
            s.enableFilter("ultimadata");
            Criteria c = s.createCriteria(Person.class);
            c.add(Restrictions.idEq(id));
            res = (Person) c.uniqueResult();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
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
        //serve per non fare sgorillare l'update, poiche manca l'id della person
        if (p.getTabhealthprofiles() != null) {
            if (p.getTabhealthprofiles().size() == 1) {
                Healthprofile hp = (Healthprofile) p.getTabhealthprofiles().toArray()[0];
                hp.setTabperson(p);
                p.getTabhealthprofiles().clear();
                p.getTabhealthprofiles().add(hp);

            }
        }
        try {
            tx = s.beginTransaction();
            s.update(p);
            s.flush();
            tx.commit();
            return p.getIdperson();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return -1;
        } finally {
            s.close();
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
        } finally {
            s.close();
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
            if (p.getTabhealthprofiles() != null) {
                if (p.getTabhealthprofiles().size() == 1) {
                    Healthprofile hp = (Healthprofile) p.getTabhealthprofiles().toArray()[0];
                    hp.setTabperson(p);
                    p.getTabhealthprofiles().clear();
                    p.getTabhealthprofiles().add(hp);

                }
            }
            s.save(p);
            s.flush();
            t.commit();
            System.out.println(p.getIdperson());
            return p.getIdperson();
        } catch (Exception e) {
            e.printStackTrace();
            t.rollback();
            return -1;
        } finally {
            s.close();
        }
    }

    protected Person getPersonNofilter(int id) {
        Person res = null;
        try {
            Session s = getSession();
            Criteria c = s.createCriteria(Person.class);
            c.add(Restrictions.idEq(id));
            res = (Person) c.uniqueResult();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
            res = null;
        } finally {
            return res;
        }

    }
}
