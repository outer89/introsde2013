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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
import model.MeasureList;
import model.MeasureType;
import model.People;
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

    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getPeople(){
        Response res = null;
        Set<Entry<String, Person>> set = PersonDao.instance.getModel().entrySet();
        List<String> l = new LinkedList<String>();
        if(set.isEmpty()){
            res = Response.noContent().build();
        }else{
            for (Map.Entry<String, Person> en : set) {
                Person p = en.getValue();
                String nominativo = p.getFirstname() + " " + p.getLastname();
                l.add(nominativo);
            }
            People p = new People();
            p.setLista(l);
            res = Response.ok(p).build();
        }
        return res;
    }
    
    
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
        Response res = null;
        Measure m = measure.getValue();
        Person p = PersonDao.instance.getModel().get(id);
        if (p == null) {
            res = Response.status(Response.Status.CONFLICT).build();
        } else if (containsMID(p, id)) {
            res = Response.status(Response.Status.CONFLICT).build();
        } else {
            m.setType(MeasureType.getType(measuretype));
            p.getListaMisure().add(m);
            URL url;
            try {
                url = new URL(uriInfo.getAbsolutePath() + "/" + m.getMid());
                res = Response.created(url.toURI()).entity(m).build();
            } catch (MalformedURLException ex) {
                Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(PersonService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }

    private boolean containsMID(Person p, String id) {
        for (Measure m1 : p.getListaMisure()) {
            if (m1.getMid().equals(id)) {
                return true;
            }
        }
        return false;
    }

    //http://localhost:8084/Ass2Test/rest/Person/1/HEIGHT/1001
    @GET
    @Path("{id}/{measuretype}/{mid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getMeasure(@PathParam("id") String id,
            @PathParam("measuretype") String measuretype,
            @PathParam("mid") String mid) {
        Response res = null;
        Person p = PersonDao.instance.getModel().get(id);
        if (p == null) {
            res = Response.status(Response.Status.CONFLICT).build();
        } else {
            Measure m = getMeasure(mid, p);
            if (m == null) {
                res = Response.status(Response.Status.CONFLICT).build();
            } else {
                res = Response.status(Response.Status.OK).entity(m).build();
            }
        }
        return res;
    }

    private Measure getMeasure(String mid, Person p) {
        for (Measure measure : p.getListaMisure()) {
            if (measure.getMid().equals(mid)) {
                return measure;
            }
        }
        return null;
    }

    @GET
    @Path("{id}/{measure}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getHistory(@PathParam("id") String id, @PathParam("measure") String measure) {
        Response res = null;
        Person p = PersonDao.instance.getModel().get(id);
        if (p == null) {
            res = Response.status(Response.Status.CONFLICT).build();
        } else {
            List<Measure> history = new LinkedList<Measure>();
            MeasureType m = MeasureType.getType(measure);
            for (Measure misura : p.getListaMisure()) {
                if (misura.getType().equals(m)) {
                    history.add(misura);
                }
            }
            if (history.isEmpty()) {
                res = Response.noContent().build();
            } else {
                MeasureList ml = new MeasureList();
                ml.setLista(history);
                res = Response.ok(ml).build();
            }
        }
        return res;
    }

}
