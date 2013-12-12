/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.lsde.ass3.publisher;

import it.unitn.lsde.ass3.assignment3.ServiceImpl;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.xml.ws.Endpoint;

/**
 *
 * @author Lorenzo
 */
public class Publisher {

    public static void main(String[] args) {
        try {
            String localhost = //InetAddress.getLocalHost().getHostAddress();
            InetAddress.getLoopbackAddress().getHostAddress();
            System.out.println(InetAddress.getLocalHost().getHostAddress());
            String port = "5912";
            String url = "http://" + localhost + ":" + port + "/";
            System.out.println(url);
            Endpoint e = Endpoint.publish(url + "ws/soap", new ServiceImpl() );
            
        } catch (UnknownHostException ex) {
            System.out.println("errore");
        }
    }
}
