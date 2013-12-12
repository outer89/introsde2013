package it.unitn.lsde.ass3.assignment3;

import it.unitn.lsde.ass3.assignment3.model.Healthprofile;
import it.unitn.lsde.ass3.assignment3.model.Person;
import static it.unitn.lsde.ass3.dao.DaoBase.getSession;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
//        Person person = new Person("HP", "Legendary", new Date());
//        Session s = getSession();
//        Transaction t = null;
//        try {
//            t = s.beginTransaction();
//            //scremo p, gli tolgo l'id
//            person.setIdperson(null);
//            s.save(person);
//            s.flush();
//            t.commit();
//            System.out.println(person.getIdperson());
//        } catch (Exception e) {
//            t.rollback();
//        } finally {
//            s.close();
//        }
        Session s = getSession();
        Criteria c = s.createCriteria(Person.class);
        c.add(Restrictions.idEq(1));
        DetachedCriteria subcriteria = DetachedCriteria.forClass(Healthprofile.class);
        subcriteria.setProjection(Projections.max("date"));
        c.createAlias("tabhealthprofiles", "hp");

        c.add(Property.forName("hp.date").eq(subcriteria));
        Person p = (Person) c.uniqueResult();
    }
}
