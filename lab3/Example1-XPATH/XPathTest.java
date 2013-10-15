
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathTest {

    public static void main(String[] args) throws ParserConfigurationException, SAXException,
            IOException, XPathExpressionException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        System.out.println("Loading books.xml...");
        Document doc = builder.parse("books.xml");

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        System.out.println("Reading list of titles...");
        System.out.println("using xpath = //bookstore/book/title/text() | //bookstore/book/author/text()");
        //XPathExpression expr = xpath.compile("/bookstore/book/@title/text()");
        XPathExpression expr = xpath.compile("//bookstore/book/title/text() | //bookstore/book/author/text()");
        Object res = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) res;
        System.out.println("Elementi trovati " + nodes.getLength());
        System.out.println("printing with System.out.println(nodes.item(i).getNodeValue()");
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
        System.out.println("using xpath = //bookstore/book/title | //bookstore/book/author");
        XPathExpression expr1 = xpath.compile("//bookstore/book/title | //bookstore/book/author");
        Object res1 = expr1.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes1 = (NodeList) res1;
        System.out.println("Elementi trovati " + nodes1.getLength());
        System.out.println("printing with nodes1.item(i).getFirstChild().getNodeValue()");
        for (int i = 0; i < nodes1.getLength(); i++) {
            System.out.println(nodes1.item(i).getFirstChild().getNodeValue());
        }

//          trivial way to do things
//          XPathExpression expr = xpath.compile("/bookstore/book");
//        Object result = expr.evaluate(doc, XPathConstants.NODESET);
//        NodeList nodes = (NodeList) result;
//        System.out.println("Elementi trovati " + nodes.getLength());
//        for (int i = 0; i < nodes.getLength(); i++) {
//            //System.out.println(nodes.item(i).getNodeValue());
//            NodeList n1 = nodes.item(i).getChildNodes();
//            for (int j = 0; j < n1.getLength(); j++) {
//                String nodename = n1.item(j).getNodeName();
//                if(nodename.equals("author") || nodename.equals("title")){
//                    System.out.println(n1.item(j).getFirstChild().getNodeValue());
//                }
//            }
//        }


    }
}
