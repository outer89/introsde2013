/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.assignment2.client;

import it.unitn.assignment2.model.Measure;
import it.unitn.assignment2.model.MeasureType;
import it.unitn.assignment2.model.Person;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.xml.sax.InputSource;

/**
 *
 * @author Lorenzo
 */
public class MyClient {

    private WebTarget target;
    private final String port = "5901";
    private final String url = "localhost";

    public MyClient() {

        final Client client = ClientBuilder.newClient().register(MoxyJsonFeature.class);
        target = client.target(getUrl());
        //to be tested VIVA
        getPerson("1", MediaType.APPLICATION_XML);
        getPerson("1", MediaType.APPLICATION_JSON);
        getMeasures();
        getAllPerson(MediaType.APPLICATION_XML);
        getAllPerson(MediaType.APPLICATION_JSON);
        getPersonMeasure("1", MeasureType.HEIGHT.toString(), "1", MediaType.APPLICATION_XML);
        getPersonMeasure("1", MeasureType.HEIGHT.toString(), "1", MediaType.APPLICATION_JSON);
        getMeasureHistory("1", MeasureType.WEIGHT.toString(), MediaType.APPLICATION_XML);
        getMeasureHistory("1", MeasureType.WEIGHT.toString(), MediaType.APPLICATION_JSON);
        deletePerson("5");
        putPerson("1005");
        //to be tested on my own services provider
//        postPerson("", MediaType.APPLICATION_XML);
//        postPerson("", MediaType.APPLICATION_JSON);
//        getPersonbyMeasureWithParams(MediaType.APPLICATION_XML, MeasureType.WEIGHT.toString(), new Double(40), new Double(50));
//        getPersonbyMeasureWithParams(MediaType.APPLICATION_JSON, MeasureType.WEIGHT.toString(), new Double(40), new Double(50));
//        Calendar before = new GregorianCalendar(2011, 0, 06);
//        Calendar after = new GregorianCalendar(2010, 11, 31);
//        getPersonMesuresByDates("1", MeasureType.HEIGHT.toString(), after.getTime(), before.getTime(), MediaType.APPLICATION_XML);
//        getPersonMesuresByDates("1", MeasureType.HEIGHT.toString(), after.getTime(), before.getTime(), MediaType.APPLICATION_JSON);
//        putPersonMeasure("1", MeasureType.WEIGHT.toString(), "123788", MediaType.APPLICATION_JSON);
//        putPersonMeasure("1", MeasureType.WEIGHT.toString(), "1237887", MediaType.APPLICATION_XML);

    }

    /**
     *
     * to be tested
     *
     *
     */
    private String prettyXml(String content) {
        Transformer transformer;
        try {
            transformer = SAXTransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //I just omit to parse the declaration which has not a closure tag, this leads to wrong format
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            Source xmlSource = new SAXSource(new InputSource(new ByteArrayInputStream(content.getBytes())));
            StreamResult res = new StreamResult(new ByteArrayOutputStream());
            transformer.transform(xmlSource, res);
            return new String(((ByteArrayOutputStream) res.getOutputStream()).toByteArray());
        } catch (TransformerConfigurationException ex) {
            System.err.println("can not config transformer -- SEE STACK TRACE");
            ex.printStackTrace();
        } catch (TransformerException tex) {
            System.err.println("error during tranformation to pretty xml -- SEE STACK TRACE");
            tex.printStackTrace();
        }
        return null;
    }

    private String prettyJson(String content) {
        String res;
        try {
            res = new JSONObject(content).toString(2);
            return res;
        } catch (JSONException ex) {
            System.err.println("error during tranformation to pretty json (returning null) -- SEE STACK TRACE");
            ex.printStackTrace();
            return null;
        }
    }

    public void putPerson(String id) {
        //this one is just tested in XML
        System.out.println("PUT PERSON");
        Person p = new Person();
        p.setId(id);
        Response res = target.path("person").path(id).request().put(Entity.entity(p, MediaType.APPLICATION_XML));
        if (res == null) {
            //can not happen
            System.out.println("No connection to server, res can not be null");
        } else {
            if (res.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
                System.out.println("RESOURCE UPDATED");
            } else if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
                System.out.println("RESOURCE CREATED");
                String entity = res.readEntity(String.class);
                String print = prettyXml(entity);
                if (print == null) {
                    System.out.println("if not ERROR then  MAY BE NOT IMPLEMENTED");
                } else {
                    System.out.println(print);
                }

            } else if (res.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
                System.out.println("RESOURCE IN CONFLICT");
            } else {
                System.out.println("server says : " + res.getStatus() + " " + res.getStatusInfo());
                System.out.println("MAY be NOT IMPLEMENTED");
            }
        }
    }

    public void postPerson(String id, String mediatype) {
        System.out.println("POST PERSON");
        Person p = new Person();
        p.setId(id);
        Response res = target.path("person").request().accept(mediatype).post(Entity.entity(p, mediatype));
        if (res == null) {
            //can not happen
            System.out.println("No connection to server, res can not be null");
        } else {
            if (res.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
                System.out.println("server says: RESOURCE CONFLICT");
            } else if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
                System.out.println("server says: CREATED");
                String entity = res.readEntity(String.class);
                if (mediatype.equals(MediaType.APPLICATION_JSON)) {
                    System.out.println(prettyJson(entity));
                } else if (mediatype.equals(MediaType.APPLICATION_XML)) {
                    System.out.println(prettyXml(entity));

                } else {
                    System.out.println(entity);
                }

            } else {
                System.out.println("server says : " + res.getStatus() + " " + res.getStatusInfo());

                System.out.println("MAY be NOT IMPLEMENTED");
            }
        }
    }

    // GET /measures
    public void getMeasures() {
        System.out.println("GET MEASURES");
        Response res = target.path("measures").request(MediaType.TEXT_PLAIN).get();
        String misure = res.readEntity(String.class);
        System.out.println(misure);

    }

    //GET /person/{id}/{measure}/{mid}
    public void getPersonMeasure(String personId, String measure, String mid, String type) {
        System.out.println("GET PERSON MEASURE " + type);
        Response res = target.path("person").path(personId)
                .path(measure).path(mid).request(type).get();
        processGetResonse(res, type);

    }

    public void putPersonMeasure(String personId, String measure, String mid, String type) {
        System.out.println("PUT PERSON MEASURE");
        Measure m = new Measure();
        m.setDate(new Date());
        m.setMid("");
        m.setType(MeasureType.WEIGHT);
        m.setValue("100");
        Response res = target.path("person").path(personId)
                .path(measure).path(mid).request(type).accept(type).put(Entity.entity(m, type));
        if (res == null) {
            //can not happen
            System.out.println("No connection to server, res can not be null");
        } else {
            if (res.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
                System.out.println("RESOURCE UPDATED");
            } else if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
                System.out.println("RESOURCE CREATED");
                String entity = res.readEntity(String.class);
                //System.out.println(entity);
                String print = "";
                if (type.equals(MediaType.APPLICATION_XML)) {
                    print = prettyXml(entity);
                } else {
                    print = prettyJson(entity);
                }
                if (print == null) {
                    System.out.println("if not ERROR then  MAY BE NOT IMPLEMENTED");
                } else {
                    System.out.println(print);
                }

            } else if (res.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
                System.out.println("RESOURCE IN CONFLICT");
            } else {
                System.out.println("server says : " + res.getStatus() + " " + res.getStatusInfo());
                System.out.println("MAY be NOT IMPLEMENTED");
            }
        }

    }

    //http://localhost:5901/person?measure=weight&max=80&min=0
    //GET /person?measure={measure}&max={max}&min={min} 
    //retrieves people whose {measure} value is in the [{min},{max}] range (if only one for the query params is provided, use only that)
    public void getPersonbyMeasureWithParams(String type, String measure, Double min, Double max) {
        Response res = target.path("person").queryParam("measure", measure)
                .queryParam("min", min).queryParam("max", max).request(type).get();
        processGetResonse(res, type);

    }

    //GET /person/{id}/{measure}?before={beforeDate}&after={afterDate} 
    //should return the history of {measure} for person {id} in the specified range of date
    //http://localhost:5901/person/1/height?before=2011-12-31&after=2011-01-08
    public void getPersonMesuresByDates(String id, String measure, Date from, Date to, String type) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Response res = target.path("person").path(id).path(measure)
                .queryParam("before", formatter.format(to)).queryParam("after", formatter.format(from)).request(type).get();
        //String s = target.path("person").path(id).path(measure)
        // .queryParam("before", formatter.format(to)).queryParam("after", formatter.format(from)).request(type).get().toString();
        //System.out.println(s);
        processGetResonse(res, type);
    }

    //GET /person/{id}/{measure} 
    public void getMeasureHistory(String personId, String measure, String type) {
        System.out.println("GET MEASURE HISTORY " + type);
        Response res = target.path("person").path(personId)
                .path(measure).request(type).get();
        processGetResonse(res, type);
    }

    // GET /person/id
    public void getPerson(String id, String type) {
        System.out.println("GET PERSON " + type);
        //Response res = target.path("person/"+id).request(MediaType.APPLICATION_JSON).get();
        Response res = target.path("person").path(id).request(type).get();
        processGetResonse(res, type);

    }

    // DELETE /person/id
    public void deletePerson(String id) {
        System.out.println("DELETE PERSON ");
        //Response res = target.path("person/"+id).request(MediaType.APPLICATION_JSON).get();
        Response res = target.path("person").path(id).request().accept(MediaType.APPLICATION_XML).delete();
        if (res == null) {
            //can not happen
            System.out.println("No connection to server, res can not be null");
        } else {
            if (res.getStatus() == Response.Status.OK.getStatusCode()) {
                System.out.println("OK - RESOURCE DELETED");
                String entity = res.readEntity(String.class);
                String print = prettyXml(entity);
                if (print == null) {
                    System.out.println("if not ERROR then  MAY BE NOT IMPLEMENTED");
                } else {
                    System.out.println(print);
                }
            } else if (res.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
                System.out.println("CONFLICT");
            } else {
                System.out.println("server says : " + res.getStatus() + " " + res.getStatusInfo());
                System.out.println("NOT IMPLEMENTED");
            }
        }

    }

    // GET /person
    public void getAllPerson(String type) {
        System.out.println("GET ALL PERSON " + type);
        Response res = target.path("person").request(type).get();
        processGetResonse(res, type);

    }

    public static void main(String[] args) {
        new MyClient();
    }

    private void processGetResonse(Response res, String m) {
        if (res == null) {
            //can not happen
            System.out.println("No connection to server, res can not be null");
        } else {
            if (res.getStatus() == Response.Status.OK.getStatusCode()) {
                //following http rcf if get is fine must return 200 OK
                //GET an entity corresponding to the requested resource is sent in the response;
                //chapter 10.2.1
                System.out.println("status ok");
                String entity = res.readEntity(String.class);
                String print = entity;
                if (m.equals(MediaType.APPLICATION_JSON)) {
                    System.out.println("processing json");
                    print = prettyJson(entity);
                } else if (m.equals(MediaType.APPLICATION_XML)) {
                    System.out.println("processing xml");
                    print = prettyXml(entity);
                    if (print == null) {
                        System.out.println("if not ERROR then  MAY BE NOT IMPLEMENTED");
                    } else {
                        System.out.println(print);
                    }
                } else {
                    print = entity;
                }
                if (print == null) {
                    System.out.println("if not ERROR then  MAY BE NOT IMPLEMENTED");
                } else {
                    System.out.println(print);
                }
            } else if (res.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {

                System.out.println("NO CONTENT");
            } else {
                System.out.println("server says : " + res.getStatus() + " " + res.getStatusInfo());
                System.out.println("NOT IMPLEMENTED ");
            }
        }
    }

    private URI getUrl() {
        return UriBuilder.fromUri("http://" + url + ":" + port).build();
    }

}
