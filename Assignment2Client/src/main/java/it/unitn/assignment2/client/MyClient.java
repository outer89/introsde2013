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
    private final String url = "10.23.98.68";

    public MyClient() {
        final Client client = ClientBuilder.newClient();
        target = client.target(getUrl());

        //postPerson("5");
        //getPerson("5");
        //getMeasures();
        //getAllPerson();
        //getPersonMeasure("1", MeasureType.HEIGHT.toString(), "1");
        getMeasureHistory("1", MeasureType.WEIGHT.toString());
    }

    public void postPerson(String id) {
//        System.out.println("to be implemented");
//        Person p = new Person();
//        p.setId(id);
//        Response res = target.path("person").request().post(Entity.entity(p, MediaType.APPLICATION_XML));
//        if (res == null) {
//            //can not happen
//            System.out.println("No connection to server, res can not be null");
//        } else {
//            if (res.getStatus() == Response.Status.CONFLICT.getStatusCode()) {
//                System.out.println("server says: RESOURCE CONFLICT");
//            }
//            else
//            if (res.getStatus() == Response.Status.CREATED.getStatusCode()) {
//                System.out.println("server says: CREATED");
//            } else {
//                System.out.println("MAY be NOT IMPLEMENTED");
//            }
//        }

    }

    // GET /measures
    public void getMeasures() {
        Response res = target.path("measures").request(MediaType.TEXT_PLAIN).get();
        String misure = res.readEntity(String.class);
        System.out.println(misure);

    }

    //GET /person/{id}/{measure}/{mid}
    public void getPersonMeasure(String personId, String measure, String mid) {
        Response res = target.path("person").path(personId)
                .path(measure).path(mid).request(MediaType.APPLICATION_XML).get();
        processGetResonse(res);

    }

    //GET /person/{id}/{measure} 
    public void getMeasureHistory(String personId, String measure) {
        Response res = target.path("person").path(personId)
                .path(measure).request(MediaType.APPLICATION_XML).get();
        processGetResonse(res);
    }

    // GET /person/id
    public void getPerson(String id) {
        //Response res = target.path("person/"+id).request(MediaType.APPLICATION_JSON).get();
        Response res = target.path("person").path(id).request(MediaType.APPLICATION_JSON).get();

        processGetResonse(res);

    }

    // GET /person
    public void getAllPerson() {
        Response res = target.path("person").request(MediaType.APPLICATION_XML).get();

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
