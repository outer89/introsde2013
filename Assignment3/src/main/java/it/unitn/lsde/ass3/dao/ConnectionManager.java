/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.lsde.ass3.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 *
 *
 * @author Lorenzo
 *
 * This class is a singleton that manages the connection. Acutally is active
 * only one session factory which "gives" session to those who requests it
 */
public class ConnectionManager {

    private static SessionFactory sessionFactory = null;

    /**
     * costruttore private so that no-one can initialize it
     */
    private ConnectionManager() {
        try {
            init();
        } catch (Exception e) {
            System.out.println("Errore nell'inizializzare hibernate");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void init() {
        
        sessionFactory = configureSessionFactory();
    }

    private static SessionFactory configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.configure();
        //builds the schema
        //SchemaExport se = new SchemaExport(configuration);
        //se.create(false,true); 
        //builds the session factory
        sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }

    /**
     * public static method to be called to obtain a session
     *
     * @return a new session if the sessionfactory is valid; an exception
     * otherwise
     */
    public Session getSession() {
        if (sessionFactory == null) {
            throw new SessionException("Session factory is null");
        }
        Session s = sessionFactory.openSession();
        s.clear();
        return s;
    }

    public static ConnectionManager getInstance() {
        return ConnectionHolder.INSTANCE;
    }

    private static class ConnectionHolder {

        private static final ConnectionManager INSTANCE = new ConnectionManager();
    }
}
