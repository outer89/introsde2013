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
public class HpDao extends DaoBase {

    /**
     *
     * @param id
     * @param hp
     * @return id of hp else -1
     */
    public int addHp(int id, Healthprofile hp) {
        Session s = getSession();
        Transaction tx = null;
        boolean save = false;
        try {
            tx = s.beginTransaction();
            if (hp.getIdtabhealthprofile() == null) {
                save = true;
            } else if ( gethp(hp.getIdtabhealthprofile()) != null) {
                //there exist already an HP with this profile
                //return -1;
            } else {
                save = true;
            }
            if (save == true) {
                Person p = new PersonDao().getPersonNofilter(id);
                if (p == null) {
                    return -2;
                }
                hp.setTabperson(p);
                s.save(hp);
                s.flush();
                tx.commit();
                return hp.getIdtabhealthprofile();
            } else {
                return -1;
            }
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            return -3;
        } finally {
            s.close();
        }

    }

    private Healthprofile gethp(int id) {
        Session s = getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Criteria c = s.createCriteria(Healthprofile.class).add(Restrictions.idEq(id));
            Healthprofile hp = (Healthprofile) c.uniqueResult();
            s.flush();
            tx.commit();
            return hp;
        } catch (Exception e) {
            return null;
        } finally {
            s.close();
        }
    }

    /**
     *
     * @param id
     * @param hp
     * @return id of hp or -1 in case of error, -2 if person does not exist
     */
    public int updateHp(int id, Healthprofile hp) {
        Session s = getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Person p = new PersonDao().getPersonNofilter(id);
            if (p == null) {
                return -2;
            }
            if ( gethp(hp.getIdtabhealthprofile()) == null) {
                System.out.println("cazziemazzi");
                hp.setIdtabhealthprofile(null);
            }
            hp.setTabperson(p);
            s.saveOrUpdate(hp);
            s.flush();
            tx.commit();
            return hp.getIdtabhealthprofile();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return -1;
        } finally {
            s.close();
        }

    }

    public List getHistory(int idPerson) {
        Session s = getSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Criteria c = s.createCriteria(Healthprofile.class);
            Person p = new Person();
            p.setIdperson(idPerson);
            c.add(Restrictions.eq("tabperson", p));
            return c.list();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return null;
        } finally {
            s.close();
        }

    }
}
