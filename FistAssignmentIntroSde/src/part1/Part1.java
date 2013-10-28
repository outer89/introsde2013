/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package part1;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import beans.Person;

/**
 *
 * @author Lorenzo
 */
public class Part1 {

    private Person p = null;
    private String name = "";
    private String surname = "";
    static final String XML_PATH = "./persone-jaxb.xml";
    static Document doc;
    static XPath xpath;
    static NodeList nodes = null;
    final static String WEIGHT = "weight";
    final static String HEIGHT = "height";
    final static Double PAGESIZE = 20.0;
    final static String expressionforallperson = "/people/person";

    private synchronized static void initForPrint() throws XPathExpressionException {
        XPathExpression expr;
        expr = xpath.compile(expressionforallperson);
        Object res = expr.evaluate(doc, XPathConstants.NODESET);
        nodes = (NodeList) res;
    }

    private synchronized static void init() {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory
                .newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder;
        try {
            builder = domFactory.newDocumentBuilder();
            System.out.println("Loading people list...");
            doc = builder.parse(XML_PATH);
            System.out.println("creo new xpath..");
            XPathFactory factory = XPathFactory.newInstance();
            xpath = factory.newXPath();
        } catch (ParserConfigurationException ex) {
            System.err.println("parser configuration exception "
                    + ex.getMessage());
        } catch (SAXException ex) {
            System.err.println("sax exception " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("io exception  " + ex.getMessage());
        }
    }

    public Part1(Person p) {
        init();
        this.p = p;
    }

    public Part1(String name, String surname) {
        init();
        this.name = name;
        this.surname = surname;
    }

    private String buildExpressionForSinglePerson(String name, String surname,
            String param) {
        return "//people/" + "person[firstname = \'" + name + "\'"
                + " and lastname = \'" + surname + "\']" + "/healthprofile/"
                + param + "/text()";

    }

    /**
     * Use xpath to implement methods like getWeight and getHeight of a given
     * person.
     *
     * @return the weight of the person indicated over initializing part1 or -1
     * if the person does not exist
     * @throws XPathExpressionException
     */
    public double getWeight() throws XPathExpressionException {
        System.out.println("executing getWeight");
        XPathExpression expr;
        String expression = "";
        // xpath.compile("//bookstore/book/title/text() | //bookstore/book/author/text()");
        if (p != null) {

            expression = buildExpressionForSinglePerson(p.getFirstname(),
                    p.getLastname(), Part1.WEIGHT);
            System.out.println(expression);

        } else if (!name.contentEquals("") && !surname.contentEquals("")) {
            expression = buildExpressionForSinglePerson(name, surname,
                    Part1.WEIGHT);
            System.out.println(expression);
        } else {
            return -1.0;
        }
        expr = xpath.compile(expression);
        Object res = expr.evaluate(doc, XPathConstants.NUMBER);
        if (res == null) {
            return -1.0;
        }
        if (res.equals(Double.NaN)) {
            System.out.println("Not a number");
            return -1.0;
        }
        //System.out.println(res);
        return (Double) res;

    }

    /**
     * @see getWeight();
     *
     * @param p
     * @return
     * @throws XPathExpressionException
     */
    public double getWeight(Person p) throws XPathExpressionException {
        this.p = p;
        return getWeight();
    }

    public void getHealthProfile(String nominativo) throws XPathExpressionException {
        System.out.println("executing getHealtProfile");
        XPathExpression expr;
        String expression = "";
        if (nominativo != null) {
            if (nominativo.compareTo("") != 0) {
                String surname = nominativo.substring(nominativo.lastIndexOf(" ") + 1);
                String name = nominativo.substring(0, nominativo.lastIndexOf(" "));
                expression = "//people/" + "person[firstname = \'" + name + "\'"
                        + " and lastname = \'" + surname + "\']" + "/healthprofile";
                System.out.println(expression);
                expr = xpath.compile(expression);
                Object obj = expr.evaluate(doc, XPathConstants.NODE);
                Node res = (Node) obj;
                if (res == null) {
                    return;
                } else {
                    printHealthProfile(res);
                }

            }
        }
    }
        /**
         * Use xpath to implement methods like getWeight and getHeight of a
         * given person.
         *
         * @return height of given person, or name and surname or -1 if not
         * found or invalid person
         * @throws XPathExpressionException if can not compile expression or
         * evaluate it
         */
    

    public double getHeight() throws XPathExpressionException {
        System.out.println("executing getHeight");

        XPathExpression expr;
        String expression = "";
        // xpath.compile("//bookstore/book/title/text() | //bookstore/book/author/text()");
        if (p != null) {

            expression = buildExpressionForSinglePerson(p.getFirstname(),
                    p.getLastname(), Part1.HEIGHT);
            System.out.println(expression);

        } else if (!name.contentEquals("") && !surname.contentEquals("")) {
            expression = buildExpressionForSinglePerson(name, surname,
                    Part1.HEIGHT);
            System.out.println(expression);
        } else {
            return -1.0;
        }
        expr = xpath.compile(expression);
        Object res = expr.evaluate(doc, XPathConstants.NUMBER);
        if (res == null) {
            return -1.0;
        }
        if (res.equals(Double.NaN)) {
            System.out.println("Not a number");
            return -1.0;
        }
        //System.out.println(res);
        return (Double) res;
    }

    public double getHeight(Person p) throws XPathExpressionException {
        this.p = p;
        return getHeight();
    }

    /**
     *
     * @param weight weight in kilos of the people to be found
     * @param relation relation that may be of the following type =, <,>
     * @throws XPathExpressionException
     */
    public void printWithParameters(Double weight, String relation) throws XPathExpressionException {
        System.out.println("executing printWithParameters, relation is the following " + relation + weight);
        if (weight == null) {
            System.out.println("weight null");
            return;
        }
        if (relation == null) {
            System.out.println("relation null");
            return;
        }
        if (weight.equals(Double.NaN)) {
            System.out.println("weight is nan");
            return;
        }
        if (relation.equalsIgnoreCase("")) {
            System.out.println("relation is empty");
            return;
        }
        if (relation.equals("=") || relation.equals("<") || relation.equals(">")) {
            //check for stupid input finished
            ///people/person[./healthprofile[weight>35.00]]
            String expression = "/people/person[./healthprofile[" + Part1.WEIGHT + relation + weight + "]]";
            System.out.println(expression);
            XPathExpression expr = xpath.compile(expression);
            Object obj = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList lista = (NodeList) obj;
            if (lista == null) {
                System.out.println("lista null");
                return;
            }
            if (lista.getLength() == 0) {
                System.out.println("lunghezza lista zero");
                return;
            } else {
                for (int i = 0; i < lista.getLength(); i++) {
                    printPerson(lista.item(i));
                }
            }
        } else {
            System.out.println("relation is not of type = or < or >");
        }

    }

    /**
     * Make a function that prints all the people in the list with detail (if
     * >20, paginated)
     *
     * can be simple printed taking outermost node (person) and calling method
     * getcontenttext()
     *
     * @throws XPathExpressionException if can not compile expression or
     * evaluate it
     */
    public void printAll() throws XPathExpressionException {
        // String expression = "/people/person[firstname=\'Brittni\']";
        if (nodes == null) {
            initForPrint();
        }
        Integer length = nodes.getLength();
        System.out.println("trovate " + length + " persone ");
        if (length < PAGESIZE) {
            System.out.println("stampo pagina zero");
            //notice that i start from 1
            printPage(1);
        } else {
            Double n = Math.ceil(nodes.getLength() / PAGESIZE);
            Integer numberofpages = n.intValue();
            //notice that i start from 1

            for (int i = 1; i < numberofpages; i++) {
                printPage(i);
                System.out.println("############### end of page " + i + " ###############");
            }

        }
    }

    private void printPerson(Node n) {
        NodeList lista = n.getChildNodes();
        for (int i = 0; i < lista.getLength(); i++) {
            Node nodo = lista.item(i);
            if (nodo.getLocalName() != null) {
                if (nodo.getLocalName().compareToIgnoreCase("healthprofile") == 0) {
                    printHealthProfile(nodo);
                } else {
                    System.out.printf("%s : %s\n", nodo.getLocalName(), nodo
                            .getFirstChild().getNodeValue());

                }
            }
        }
    }

    private void printHealthProfile(Node n) {
        NodeList lista = n.getChildNodes();
        for (int i = 0; i < lista.getLength(); i++) {
            Node nodo = lista.item(i);
            if (nodo.getLocalName() != null) {
                System.out.printf("%s : %s\n", nodo.getLocalName(), nodo
                        .getFirstChild().getNodeValue());

            }
        }
    }

    /**
     *
     * @param pageNumber the page number to be printed
     * @throws XPathExpressionException
     */
    public void printPage(Integer pageNumber) throws XPathExpressionException {
        if (pageNumber == null) {
            System.out.println("page number equals to null");
            return;
        }
        if (pageNumber <= 0) {
            System.out.println("page number invalid");
            return;
        }
        if (nodes == null) {
            initForPrint();
        }
        Double n = Math.ceil(nodes.getLength() / PAGESIZE);
        Integer numberofpages = n.intValue();
        if (pageNumber > numberofpages) {
            System.out.println("page out of bound");
            return;
        }
        Double s = ((pageNumber - 1) * PAGESIZE);
        int start = s.intValue();
        System.out.println("start at: " + start);
        Double e = (pageNumber * PAGESIZE) - 1;
        int end = e.intValue();
        System.out.println("end at: " + end);
        //o i < end quando sei nel mezzo della lista
        //o i< nodes.getlenght quando stai stampando l'ultima pagina
        for (int i = start; i < end && i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            printPerson(node);
        }
    }

    public static void main(String[] args) {
        Person p = new Person();
        p.setFirstname("Brittni");
        p.setLastname("North");
        Part1 p1 = new Part1(p);
        try {
            System.out.println(p1.getWeight());
            System.out.println(p1.getHeight());
            //p1.printAll();
            p1.getHealthProfile("Brittni North");
            p1.printWithParameters(80.0, ">");
        } catch (Exception e) {
            System.err.println("errore");
            e.printStackTrace();
        }

    }
}
