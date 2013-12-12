package it.unitn.test.testh2;

import it.unitn.test.testh2.model.Tabperson;
import java.util.Date;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println("testing");
        Configuration cfg = new Configuration().configure();
//        System.out.println("creating schema");
//        SchemaExport se = new SchemaExport(cfg);
//        se.create(true, true);
//        System.out.println("created");
        SessionFactory sf = cfg.buildSessionFactory();
        Session s = sf.openSession();
        Tabperson p = new Tabperson("asdf", "pro", new Date());
        Transaction t = null;
        try {
            System.out.println("prendo la tx");
            t = s.beginTransaction();
            s.saveOrUpdate(p);
            s.flush();
            t.commit();
            System.out.println("carico");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            t.rollback();
        } finally {
            s.close();
        }

    }
}
