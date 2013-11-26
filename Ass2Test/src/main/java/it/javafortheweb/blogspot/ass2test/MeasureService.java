/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.javafortheweb.blogspot.ass2test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.MeasureType;

/**
 *
 * @author Lorenzo
 */
@Path("/measure")
public class MeasureService {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAllMeasures(){
        Response res = null;
        String s = "";
        for (MeasureType mt : MeasureType.values()) {
            s = s +  mt.name() + ",";
        }
        s = s.substring(0, s.length()-1);
        System.out.println(s);
        res = Response.ok(s).build();
        return res;
    }
    
}
