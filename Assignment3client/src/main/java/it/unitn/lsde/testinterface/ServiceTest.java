/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.unitn.lsde.testinterface;

/**
 *
 * @author Lorenzo
 */
public interface ServiceTest {
    
    public void readPerson(int id);
    public int createPerson();
    public void deletePerson(int id);
    public void updatePerson();
    public void getHistory();
    public void updateHealthprofile();
    public void addHealthprofile();
    
}
