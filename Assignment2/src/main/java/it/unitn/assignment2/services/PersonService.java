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
    //GET /person?measure={measure}&max={max}&min={min} retrieves people whose {measure} value is in the [{min},{max}] range (if only one for the query params is provided, use only that)
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getPeople(@QueryParam("measure") String measure, @QueryParam("max") Double max, @QueryParam("min") Double min) {
        Response res = null;
        MeasureType m = MeasureType.getType(measure);
        if (m == null && (max != null || min != null)) {
            res = Response.status(Response.Status.NO_CONTENT).build();
            return res;
        }
        List<String> l = getListaPersoneQuery(measure, max, min);
        if (l == null) {
            res = Response.noContent().build();
        } else if (l.isEmpty()) {
            res = Response.noContent().build();
        } else {
            People p = new People();
            p.setLista(l);
            res = Response.ok(p).build();
        }
        return res;
    }
    private List<String> getListaPersoneQuery(String measure, Double max, Double min) {
        List<String> res = null;
        MeasureType m = MeasureType.getType(measure);
        res = new LinkedList<String>();
        Set<Entry<String, Person>> set = PersonDao.instance.getModel().entrySet();
        boolean getmax = true;
        boolean getmin = true;
        if (max == null) {
            getmax = false;
        }
        if (min == null) {
            getmin = false;
        }
        for (Map.Entry<String, Person> en : set) {
            Person p = en.getValue();
            if (m == null) {
                String s = p.getFirstname() + " " + p.getLastname();
                res.add(s);
            } else if (checkContrains(p, m, max, min, getmin, getmax)) {
                String s = p.getFirstname() + " " + p.getLastname();
                res.add(s);
            }
        }

        return res;
    }

    private boolean checkContrains(Person p, MeasureType measure, Double max, Double min, boolean getmin, boolean getmax) {
        boolean res = false;
        switch (measure) {
            case HEIGHT:
                res = chekcSingle(p.gethProfile().getHeight(), max, min, getmin, getmax);
                break;
            case WEIGHT:
                res = chekcSingle(p.gethProfile().getWeight(), max, min, getmin, getmax);
                break;
            default:
                res = false;
                break;
        }
        return res;
    }

    private boolean chekcSingle(Double value, Double max, Double min, boolean getmin, boolean getmax) {
        if (getmin && getmax) {

            if (value.compareTo(max) <= 0 && value.compareTo(min) >= 0) {
                //must be less than max and greater than min
                //take also the two extremes
                return true;
            }
        } else if (getmax) {
            if (value.compareTo(max) <= 0) {
                //take also the two extremes
                return true;
            }
        } else if (getmin) {
            if (value.compareTo(min) >= 0) {
                //take also the two extremes
                return true;
            }
        } else {
            return true;
        }
        return false;
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
        Person c = person.getValue();
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
            if (p.getId().toString().equals(id)) {
                if (PersonDao.instance.getModel().containsKey(id)) {
                    //fai l'update
                    res = Response.noContent().build();
                } else {
                    res = Response.created(uriInfo.getAbsolutePath()).entity(p).build();
                }
                PersonDao.instance.getModel().put(id, p);
            } else {
                //se gli id sono diversi
                //per una politica di indicizzazione delle risorse ho deciso di dare conflict se gli id sono diversi
                res = Response.status(Response.Status.CONFLICT).build();
            }
        } else {
            // se non c'è l'id lo assegno uguale a quello di cui sta facendo l'update, sempre che non sia già usato
            if (PersonDao.instance.getModel().containsKey(id)) {
                //conflict = voglio l'id but it is missing i reject the update
                res = Response.status(Response.Status.CONFLICT).build();
            } else {
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

    //PUT /person/{id}/{measure}/{mid} should update the value for the {measure} of person {id}
    @PUT
    @Path("{id}/{measure}/{mid}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response putPersonMeasure(JAXBElement<Measure> measurepassed,
            @PathParam("id") String id,
            @PathParam("measure") String measure,
            @PathParam("mid") String mid) {
        Response res = null;
        //<editor-fold defaultstate="collapsed" desc="should never be reached">
        if (id == null || measure == null || mid == null) {
            //request should not be performed in the same way
            //this in theory should never be reached
            res = Response.status(Response.Status.BAD_REQUEST).build();
            return res;
        }
//</editor-fold>
        Person p = PersonDao.instance.getModel().get(id);
        if (p == null) {
            //in conflict, there exist no such person
            res = Response.status(Response.Status.CONFLICT).build();
            return res;
        } else {
            //if the person exists
            MeasureType m = MeasureType.getType(measure);
            if (m == null) {
                res = Response.status(Response.Status.CONFLICT).build();
                return res;
            }
            //try to get value of acutal passed measure
            Measure a = measurepassed.getValue();
            //<editor-fold defaultstate="collapsed" desc="never reached">
            if (a == null) {
                //should never be reache
                //jaxb+jersey tells: no entity
                res = Response.status(Response.Status.CONFLICT).build();
                return res;
            }
//</editor-fold>
            a.setType(m);
            //serach measure inside the person measure list
            Measure misura = getMeasure(mid, p, measure);
            if (misura == null) {
                //this means that the resource is empty at that URL so I can do a post here
                //maintain consistency, change a.getmid to the mid indicated and save it 
                a.setMid(mid);
                p.getListaMisure().add(a);
                res = Response.created(uriInfo.getAbsolutePath()).entity(a).build();
                return res;
            }

            //means that i have measure, i have controlled that nothing is null, i update measure
            //check that acutal passed measure has a valid id
            if (a.getMid() == null || a.getMid().equals("")) {
                //maintain consistency -> thus next if updates the value
                a.setMid(misura.getMid());
            }
            if (a.getMid().equals(misura.getMid())) {
                p.getListaMisure().remove(misura);
                p.getListaMisure().add(a);
                res = Response.status(Response.Status.NO_CONTENT).build();
                return res;
            } else {
                //id are different, this is not allowed
                //i want to keep consistency
                res = Response.status(Response.Status.CONFLICT).build();
                return res;
            }
        }
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
        Response res = null;
        Person p = PersonDao.instance.getModel().get(id);
        if (p == null) {
            res = Response.status(Response.Status.NO_CONTENT).build();
        } else {
            Measure m = getMeasure(mid, p, measuretype);
            if (m == null) {
                res = Response.status(Response.Status.NO_CONTENT).build();
            } else {
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

    //GET /person/{id}/{measure}?before={beforeDate}&after={afterDate}
    //GET /person/{id}/{measure}
    @GET
    @Path("{id}/{measure}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getMeasureList(@PathParam("id") String id,
            @PathParam("measure") String measure,
            @QueryParam("after") String from,
            @QueryParam("before") String to) {

        Response res = null;
        Person p = PersonDao.instance.getModel().get(id);
        if (p == null) {
            res = Response.status(Response.Status.CONFLICT).build();
            return res;
        }

        //else go on
        List<Measure> history = getListaMisure(p, measure, from, to);
        if (history.isEmpty()) {
            res = Response.noContent().build();
        } else {
            MeasureList ml = new MeasureList();
            Collections.sort(history, new MeasureListComparator());
            ml.setLista(history);
            res = Response.ok(ml).build();
        }
        return res;
    }

    private List<Measure> getListaMisure(Person p, String measure, String datefrom, String dateto) {
        List<Measure> res = null;
        MeasureType m = MeasureType.getType(measure);
        Date from = null;
        Date to = null;
        if (m == null) {
            res = new LinkedList<Measure>();
            return res;
        }
        //try to convert dates
        from = transformDate(datefrom);
        to = transformDate(dateto);
        boolean before = true;
        boolean after = true;
        if (from == null) {
            after = false;
        }
        if (to == null) {
            before = false;
        }
        res = new LinkedList<Measure>();
        for (Measure misura : p.getListaMisure()) {
            if (misura.getType().equals(m)) {
                if (after && before) {
                    if (misura.getDate().after(from) && misura.getDate().before(to)) {
                        //only if between the two dates
                        res.add(misura);
                    }
                } else if (after) {
                    //only if after the from date
                    if (misura.getDate().after(from)) {
                        res.add(misura);
                    }
                } else if (before) {
                    //only if before the to date
                    if (misura.getDate().before(to)) {
                        res.add(misura);
                    }
                } else {
                    //get the whole history, no date is specified
                    res.add(misura);
                }
            }
        }
        return res;
    }

    private Date transformDate(String date) {
        Date res = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            res = formatter.parse(date);
        } catch (Exception e) {
            res = null;
        }
        return res;
    }
}
