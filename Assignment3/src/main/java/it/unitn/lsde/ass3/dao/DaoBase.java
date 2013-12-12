/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.lsde.ass3.dao;

import org.hibernate.Session;

/**
 *
 * @author Lorenzo
 */
public abstract class DaoBase {

    public static Session getSession() {
        return ConnectionManager.getInstance().getSession();
    }
}
