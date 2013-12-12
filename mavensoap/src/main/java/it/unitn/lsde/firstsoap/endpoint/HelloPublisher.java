/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.lsde.firstsoap.endpoint;

import it.unitn.lsde.firstsoap.HelloWorldImpl;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.Endpoint;

/**
 *
 * @author Lorenzo
 */
public class HelloPublisher {

    public static void main(String[] args) {
        try {
            String localhost = //InetAddress.getLocalHost().getHostAddress();
            InetAddress.getLoopbackAddress().getHostAddress();
            System.out.println(InetAddress.getLocalHost().getHostAddress());
            String port = "6900";
            String url = "http://" + localhost + ":" + port + "/";
            System.out.println(url);
            Endpoint e = Endpoint.publish(url+ "ws/hello", new HelloWorldImpl());
        } catch (UnknownHostException ex) {
            System.out.println("errore");
        }
            
    }
}
