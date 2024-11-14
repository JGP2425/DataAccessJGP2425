import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;


public class Activity_8_1 {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder loader = factory.newDocumentBuilder();
        Document doc = loader.parse("contacts.xml");
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("contact");

        for (int i = 0; i < nList.getLength(); i++) {
            String phone = "";
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;
                String fullName = elem.getElementsByTagName("name").item(0).getTextContent()
                        + " " + elem.getElementsByTagName("surname").item(0).getTextContent();
                System.out.println("Full name: " + fullName);


                NodeList childNodes = elem.getElementsByTagName("phones").item(0).getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element phoneElement = (Element) childNodes.item(j);
                        if (phoneElement.getTagName().equals("cell"))
                             phone = phoneElement.getTextContent();
                        else if (phoneElement.getTagName().equals("work") && phone.isEmpty())
                             phone = phoneElement.getTextContent();
                        else if (phoneElement.getTagName().equals("home") && phone.isEmpty())
                            phone = phoneElement.getTextContent();
                    }
                }
                System.out.println("Phone: " + phone);

            }
        }
    }
}
