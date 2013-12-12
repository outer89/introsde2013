/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.lsde.ass3.assignment3;

import it.unitn.lsde.ass3.assignment3.model.Healthprofile;
import it.unitn.lsde.ass3.assignment3.model.Person;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author Lorenzo
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface Ass3Services {

    @WebMethod(operationName = "readPerson")
    @WebResult(name = "person")
    public Person readPerson(@WebParam(name = "personId") int id)throws Exception;

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
}
