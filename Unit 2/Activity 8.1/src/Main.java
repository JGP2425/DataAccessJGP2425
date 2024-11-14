import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main {
    public static void main( String[] args ) {
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse("contacts.xml", new myXMLContactsHandler());
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}

class myXMLContactsHandler extends DefaultHandler {
    protected String tagContent;
    private String name;
    private String surname;
    private String cellPhone;
    private String workPhone;
    private String homePhone;

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    }

    public void characters( char ch[], int start, int length ) throws SAXException {
        tagContent = new String( ch, start, length );
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ( !qName.isBlank() ) {
            if (qName.equals("name") )
                name = tagContent;
            if (qName.equals("surname"))
                surname = tagContent;

            if (qName.equals("home"))
                homePhone = tagContent;
            if (qName.equals("cell"))
                cellPhone = tagContent;
            if (qName.equals("work"))
                workPhone = tagContent;

            if (qName.equals("contact"))
                printContactData();
        }
    }

    private void printContactData() {
        System.out.println("Full name: " + name + " " + surname);
        System.out.print("Phone: ");
        if (cellPhone != null) System.out.print(cellPhone);
        else if (workPhone != null) System.out.print(workPhone);
        else if (homePhone != null) System.out.print(homePhone);
        System.out.println();
        System.out.println("-------------------");
    }
}

