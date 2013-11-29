/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.assignment2.services;

import it.unitn.assignment2.dao.PersonDao;
import it.unitn.assignment2.model.Measure;
import it.unitn.assignment2.model.MeasureList;
import it.unitn.assignment2.model.MeasureType;
import it.unitn.assignment2.model.People;
import it.unitn.assignment2.model.Person;
import it.unitn.assignment2.utils.MeasureListComparator;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Lorenzo
 */
@Path("/person")
public class PersonService {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getPeople() {
        Response res = null;
        Set<Entry<String, Person>> set = PersonDao.instance.getModel().entrySet();
        List<String> l = new LinkedList<String>();
        if (set.isEmpty()) {
            res = Response.noContent().build();
        } else {
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
        /**
         * control if there is a person.id if there is not, get a new one and
         * insert resource else control if the p.id is already present in the
         * model if is present return a conflict, with the actual state of
         * resources the request can not be fulfilled else insert in the model
         *
         */
        System.out.println("POST");
        Person c = person.getValue();
        System.out.println(c.getId());
        if (c.getId() == null || c.getId().equals("")) {
            //devo assegnargli un id
            c.setId(PersonDao.instance.getNewID());
        }
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
            //compliant to rfc
            res = Response.status(Response.Status.CONFLICT).build();
        }
        return res;
    }

    @PUT //fa l'update
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response putPerson(JAXBElement<Person> person, @PathParam("id") String id) {
        /**
         * makes the update of a person i get and xml or json and i preocede in
         * checking if the following are respected: i will not update the
         * resource if the person.id is different from id if person.id is null
         * and id is present in the database I return a conflict if person.id is
         * null and id is NOT present in the database I add the person to the
         * database
         *
         * if the person.id is present I check that p.id and id are equal before
         * updating the resource because I don't want to update them if they
         * have different id
         *
         */
        Response res = null;
        Person p = person.getValue();
        if (p.getId() != null && !p.getId().equals("")) {
            System.out.println("entity has id");
            if (p.getId().toString().equals(id)) {
                if (PersonDao.instance.getModel().containsKey(id)) {
                    //fai l'update
                    System.out.println("faccio l'update");
                    res = Response.noContent().build();
                } else {
                    System.out.println("faccio una insert");
                    res = Response.created(uriInfo.getAbsolutePath()).entity(p).build();
                }
                System.out.println("executing update");
                PersonDao.instance.getModel().put(id, p);
            } else {
                //se gli id sono diversi
                //per una politica di indicizzazione delle risorse ho deciso di dare conflict se gli id sono diversi
                System.out.println("gli id sono diversi");
                res = Response.status(Response.Status.CONFLICT).build();
            }
        } else {
            // se non c'è l'id lo assegno uguale a quello di cui sta facendo l'update, sempre che non sia già usato
            if (PersonDao.instance.getModel().containsKey(id)) {
                //conflict = voglio l'id but it is missing i reject the update
                System.out.println("conflitto");
                res = Response.status(Response.Status.CONFLICT).build();
            } else {
                System.out.println("faccio una nuova insert con l'id usato per il path");
                p.setId(id);
                PersonDao.instance.getModel().put(id, p);
                res = Response.created(uriInfo.getAbsolutePath()).entity(p).build();
            }
        }
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
        } else if (containsMID(p, id, measuretype)) {
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

    private boolean containsMID(Person p, String id, String measureType) {
        for (Measure m1 : p.getListaMisure()) {
            if (m1.getMid().equals(id) && m1.getType().equals(MeasureType.getType(measureType))) {
                return true;
            }
        }
        return false;
    }

    @GET
    @Path("{id}/{measuretype}/{mid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getMeasure(@PathParam("id") String id,
            @PathParam("measuretype") String measuretype,
            @PathParam("mid") String mid) {
        System.out.println(uriInfo.getAbsolutePath());
        System.out.println(id + " " + measuretype + " " + mid);
        Response res = null;
        Person p = PersonDao.instance.getModel().get(id);
        if (p == null) {
            System.out.println("there is not such person");
            res = Response.status(Response.Status.NO_CONTENT).build();
        } else {
            System.out.println("looking for the measure");
            Measure m = getMeasure(mid, p, measuretype);
            if (m == null) {
                System.out.println("there is not such measure");
                res = Response.status(Response.Status.NO_CONTENT).build();
            } else {
                System.out.println("ok returning measure");
                res = Response.status(Response.Status.OK).entity(m).build();
            }
        }
        return res;
    }

    private Measure getMeasure(String mid, Person p, String measureType) {
        for (Measure measure : p.getListaMisure()) {
            if (measure.getMid().equals(mid) && measure.getType().equals(MeasureType.getType(measureType))) {
                return measure;
            }
        }
        return null;
    }

    private Date transformDate(String date) {
        Date res = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd");
            res = formatter.parse(date);
        } catch (Exception e) {
            res = null;
        }
        return res;

    }

    //FIXME
    //GET /person/{id}/{measure}?before={beforeDate}&after={afterDate}
    //GET /person/{id}/{measure}
//    @GET
//    @Path("{id}/{measure}")
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response getMeasureList(@PathParam("id") String id,
//            @PathParam("measure") String measure,
//            @QueryParam("after") String from,
//            @QueryParam("before") String to) {
//
//        Response res = null;
//        Person p = PersonDao.instance.getModel().get(id);
//        if (p == null) {
//            res = Response.status(Response.Status.CONFLICT).build();
//        } else {
//            List<Measure> history = new LinkedList<Measure>();
//            MeasureType m = MeasureType.getType(measure);
//            Boolean dummydate = false;
//            Date fromdate = null;
//            Date todate = null;
//            if (from != null && to != null) {
//                if (from.equals("")) {
//                    dummydate = true;
//                }
//                if (to.equals("")) {
//                    dummydate = true;
//                } else {
//                    //try to convert dates
//
//                }
//            }
//            for (Measure misura : p.getListaMisure()) {
//                if (misura.getType().equals(m)) {
//
//                    if (misura.getDate().after(fromdate) && misura.getDate().before(todate)) {
//                        history.add(misura);
//                    }
//                } else {
//                    history.add(misura);
//                }
//            }
//        }
//        if (history.isEmpty()) {
//            res = Response.noContent().build();
//        } else {
//            MeasureList ml = new MeasureList();
//            Collections.sort(history, new MeasureListComparator());
//            ml.setLista(history);
//            res = Response.ok(ml).build();
//        }
//    }
//    return res ;
//}

}
