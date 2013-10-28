/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import utility.GenerateXml;

/**
 *
 * @author Lorenzo
 */
public class GeneratorTest {

    private static final String PERSON_XML_PATH = "./persone-jaxb.xml";

    public static void main(String[] args) {
        GenerateXml gxml = new GenerateXml();
        Boolean res = gxml.generateXml(PERSON_XML_PATH);
        System.out.println("esito: " + res);
    }
}
