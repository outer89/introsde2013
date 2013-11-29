/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.assignment2.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ext.ContextResolver;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author Lorenzo
 */
public class StandAloneServer {

    HttpServer server = null;
    private String baseUrl;

    public StandAloneServer() {
        try {
            String localhost = //InetAddress.getLocalHost().getHostAddress();
                    InetAddress.getLoopbackAddress().getHostAddress();
            System.out.println(InetAddress.getLocalHost().getHostAddress());
            String port = "5901";
            String url = "http://" + localhost + ":" + port + "/";
            System.out.println(url);
            ResourceConfig rc = createApp();
            URI uri = URI.create(url);
            server = GrizzlyHttpServerFactory.createHttpServer(uri, rc);
            try {
                System.in.read();
                server.stop();
            } catch (IOException ex) {
                Logger.getLogger(StandAloneServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(StandAloneServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ResourceConfig createApp() {
        return new ResourceConfig().
                packages("it.unitn.assignment2");
              //  register(createMoxyJsonResolver());
    }

    public static ContextResolver<MoxyJsonConfig> createMoxyJsonResolver() {
        final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
        //per sicurezza 
        //Map<String, String> namespacePrefixMapper = new HashMap<String, String>(1);
        //namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
        //moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper).setNamespaceSeparator(':');
        return moxyJsonConfig.resolver();
    }
    public static void main(String[] args) {
        new StandAloneServer();
    }

}
