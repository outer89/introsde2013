/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.assignment2.client;

import it.unitn.assignment2.model.MeasureType;
import it.unitn.assignment2.model.Person;
import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author Lorenzo
 */
public class MyClient {

    private WebTarget target;
    private final String port = "5901";
    private final String url = "10.107.28.148";

    public MyClient() {
        final Client client = ClientBuilder.newClient();
        target = client.target(getUrl());
        //to be tested VIVA
//        getPerson("5", MediaType.APPLICATION_XML);
//        getPerson("5", MediaType.APPLICATION_JSON);
//        getMeasures();
//        getAllPerson(MediaType.APPLICATION_XML);
//        getAllPerson(MediaType.APPLICATION_JSON);
//        getPersonMeasure("1", MeasureType.HEIGHT.toString(), "1", MediaType.APPLICATION_XML);
//        getPersonMeasure("1", MeasureType.HEIGHT.toString(), "1", MediaType.APPLICATION_JSON);
//        getMeasureHistory("1", MeasureType.WEIGHT.toString(), MediaType.APPLICATION_XML);
//        getMeasureHistory("1", MeasureType.WEIGHT.toString(), MediaType.APPLICATION_JSON);
        //deletePerson("5");
        putPerson("1005");
        //to be tested on my own services provider
//        postPerson("");

    }

    public void putPerson(String id) {
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
                System.out.println(entity);

            } else if (res.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
                System.out.println("RESOURCE IN CONFLICT");
            } else {
                System.out.println("server says : " + res.getStatus() + " " + res.getStatusInfo());
                System.out.println("MAY be NOT IMPLEMENTED");
            }
        }
    }

    public void postPerson(String id) {
        System.out.println("POST PERSON");
        Person p = new Person();
        p.setId(id);
        Response res = target.path("person").request().post(Entity.entity(p, MediaType.APPLICATION_XML));
        if (res == null) {
            //can not happen
            System.out.println("No connection to server, res can not be null");
        } else {
            if (res.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
                System.out.println("server says: RESOURCE CONFLICT");
            } else if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
                System.out.println("server says: CREATED");
                String entity = res.readEntity(String.class);
                System.out.println(entity);

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
        processGetResonse(res);

    }

    //GET /person/{id}/{measure} 
    public void getMeasureHistory(String personId, String measure, String type) {
        System.out.println("GET MEASURE HISTORY " + type);
        Response res = target.path("person").path(personId)
                .path(measure).request(type).get();
        processGetResonse(res);
    }

    // GET /person/id
    public void getPerson(String id, String type) {
        System.out.println("GET PERSON " + type);
        //Response res = target.path("person/"+id).request(MediaType.APPLICATION_JSON).get();
        Response res = target.path("person").path(id).request(type).get();

        processGetResonse(res);

    }

    // DELETE /person/id
    public void deletePerson(String id) {
        System.out.println("DELETE PERSON ");
        //Response res = target.path("person/"+id).request(MediaType.APPLICATION_JSON).get();
        Response res = target.path("person").path(id).request().delete();
        if (res == null) {
            //can not happen
            System.out.println("No connection to server, res can not be null");
        } else {
            if (res.getStatus() == Response.Status.OK.getStatusCode()) {
                System.out.println("OK - RESOURCE DELETED");
                String entity = res.readEntity(String.class);
                System.out.println(entity);
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

        processGetResonse(res);

    }

    public static void main(String[] args) {
        new MyClient();
    }

    private void processGetResonse(Response res) {
        if (res == null) {
            //can not happen
            System.out.println("No connection to server, res can not be null");
        } else {
            if (res.getStatus() == Response.Status.OK.getStatusCode()) {
                //following http rcf if get is fine must return 200 OK
                //GET an entity corresponding to the requested resource is sent in the response;
                //chapter 10.2.1
                String entity = res.readEntity(String.class);
                System.out.println(entity);
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
