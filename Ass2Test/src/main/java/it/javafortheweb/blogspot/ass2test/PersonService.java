/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.javafortheweb.blogspot.ass2test;

import dao.PersonDao;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import model.Measure;
import model.MeasureType;
import model.Person;

/**
 *
 * @author Lorenzo
 */
@Path("/Person")
public class PersonService {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    /**
     * Following the crud operation on Person
     *
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getPerson(@PathParam("id") String id) {
        Person p = PersonDao.instance.getModel().get(id);
        if (p != null) {
            return Response.status(200).entity(p).build();
        } else {
            return Response.noContent().build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response newPerson(JAXBElement<Person> person) {
        System.out.println("POST");
        Person c = person.getValue();
        System.out.println(c.getId());
        Response res = null;
        if (PersonDao.instance.getModel().containsKey(c.getId())) {
            res = Response.status(Response.Status.CONFLICT).build();
        } else {
            PersonDao.instance.getModel().put(c.getId(), c);
            try {
                URL url = new URL(uriInfo.getAbsolutePath() + "/" + c.getId());

                res = Response.created(url.toURI()).entity(c).build();
            } catch (URISyntaxException ex) {
                Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;

    }

    @DELETE
    @Path("{id}")
    public Response deletePerson(@PathParam("id") String id) {
        Response res = null;
        if (PersonDao.instance.getModel().containsKey(id)) {
            Person p = (Person) PersonDao.instance.getModel().get(id);
            PersonDao.instance.getModel().remove(id);
            res = Response.status(Response.Status.OK).entity(p).build();
        } else {
            res = Response.status(Response.Status.CONFLICT).build();
        }
        return res;
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response putPerson(JAXBElement<Person> person, @PathParam("id") String id) {
        Response res = null;
        Person p = person.getValue();
        if (PersonDao.instance.getModel().containsKey(id)) {
            //fai l'update
            res = Response.noContent().build();
        } else {

            res = Response.created(uriInfo.getAbsolutePath()).entity(p).build();

        }
        PersonDao.instance.getModel().put(id, p);
        return res;
    }

    /**
     *
     * POST /person/{id}/{measure} should save a new value for the {measure} of
     *
     * @param id
     * @param measuretype
     * @param measure
     * @return
     */
    @POST
    @Path("{id}/{measuretype}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})

    public Response newMeasure(@PathParam("id") String id, @PathParam("measuretype") String measuretype, JAXBElement<Measure> measure) {
        Response res = Response.status(200).build();
        System.out.println(id);
        System.out.println(MeasureType.contains(measuretype));
        Measure m = measure.getValue();
        System.out.println(m.getMid());
        return res;
    }
}
