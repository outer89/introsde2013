/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.lsde.firstsoap;

import javax.jws.WebService;

/**
 *
 * @author Lorenzo
 */
@WebService(endpointInterface = "it.unitn.lsde.firstsoap.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    @Override
    public String getHelloWorldAsString(String name) {
        return "Hello World JAX-WS " + name;
    }

}
