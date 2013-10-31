/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import beans.Person;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPathExpressionException;
import part1.Part1;
import part2.Part2;

/**
 *
 * @author Lorenzo
 */
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("-------- PART 2 --------");
            System.out.println("creazione xml");
            Part2 p2 = new Part2();
            p2.scrivi();
            p2.leggi();
            System.out.println("-------- PART 2 END --------");
            System.out.println("-------- PART 1 --------");
            Person p = new Person();
            p.setFirstname("Brittni");
            p.setLastname("North");
            Part1 p1 = new Part1(p);
            System.out.println(p1.getHeight());
            System.out.println(p1.getWeight());
            new Part1(null).getHealthProfile("Brittni North");
            p1.printWithParameters(new Double("145"), ">");
            //p1.printAll();
            System.out.println("-------- PART 1 END --------");
        } catch (XPathExpressionException ex) {
            System.err.println("errore nell'esecuzione");
        } catch (JAXBException ex) {
            System.err.println("errore nella lettura dell'xml");
        }
    }
}
