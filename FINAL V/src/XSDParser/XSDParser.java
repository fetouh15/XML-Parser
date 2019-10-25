package XSDParser;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XSDParser {

    /**
     * @param args
     */
    public static void main(String args[]) {

        try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
            docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File("Effat.xsd"));
            NodeList list = doc.getElementsByTagName("xs:element");
            
            ArrayList<String> colN=new ArrayList<>();
            ArrayList<String> colNT=new ArrayList<>();


            Element filename = (Element) list.item(0);
            String fn=filename.getAttribute("name");
            System.out.println("File name: "+fn);
            Element rootelement = (Element) list.item(1);
            String rt=rootelement.getAttribute("name");
            System.out.println("rootelement: " + rt);
            for (int i = 2 ; i < list.getLength(); i++) {
                Element first = (Element) list.item(i);
                if (first.hasAttributes()) {
                    String nm = first.getAttribute("name");
                    //System.out.println(nm);
                    colN.add(nm);
                    String nm1 = first.getAttribute("type");
                    //System.out.println(nm1);
                    colNT.add(nm1);
                }
            }
            for(int i=0;i<colN.size();i++){
                System.out.println(colN.get(i) + " " + colNT.get(i));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
