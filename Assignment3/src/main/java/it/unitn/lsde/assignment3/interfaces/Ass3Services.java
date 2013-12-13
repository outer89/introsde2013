/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.lsde.assignment3.interfaces;

import it.unitn.lsde.ass3.assignment3.model.Healthprofile;
import it.unitn.lsde.ass3.assignment3.model.History;
import it.unitn.lsde.ass3.assignment3.model.Person;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author Lorenzo
 */
@WebService(targetNamespace = "http://assignment3.ass3.lsde.unitn.it/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Ass3Services {

    @WebMethod(operationName = "readPerson")
    @WebResult(name = "person")
    public Person readPerson(@WebParam(name = "personId") int id) throws Exception;

    @WebMethod(operationName = "createPerson")
    @WebResult(name = "personId")
    public int addPerson(@WebParam(name = "person") Person person) throws Exception;

    @WebMethod(operationName = "updatePerson")
    @WebResult(name = "personId")
    public int updatePerson(@WebParam(name = "person") Person person) throws Exception;

    @WebMethod(operationName = "deletePerson")
    @WebResult(name = "personId")
    public int deletePerson(@WebParam(name = "personId") int id) throws Exception;

    @WebMethod(operationName = "updatePersonHealthProfile")
    @WebResult(name = "hpId")
    public int updatePersonHP(@WebParam(name = "personId") int id, @WebParam(name = "healthProfile") Healthprofile hp) throws Exception;

    @WebMethod(operationName = "addPersonHealthProfile")
    @WebResult(name = "hpId")
    public int addPersonHP(@WebParam(name = "personId") int id, @WebParam(name = "healthProfile") Healthprofile hp) throws Exception;

    @WebMethod(operationName = "getHistory")
    @WebResult(name = "history")
    public History readHistory(@WebParam(name = "personId") int id) throws Exception;

}
