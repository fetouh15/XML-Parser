package eg.edu.alexu.csd.oop.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author effat
 */
public class RowCounter {

    public int CountR(String filename) {
        try {
            int C = 0;
            XMLInputFactory inFactory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = inFactory.createXMLEventReader(new FileInputStream(filename + ".xml"));
            XMLEvent event = eventReader.nextEvent();

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
            docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(filename + ".xsd"));
            NodeList list = doc.getElementsByTagName("xs:element");
            Element rootelement1 = (Element) list.item(1);
            String rootelement = rootelement1.getAttribute("name");

            ArrayList<String> colN = new ArrayList<>();
            while (eventReader.hasNext()) {
                if (event.isStartElement() && event.asStartElement().getName().toString().equalsIgnoreCase(rootelement)) {
                    event = eventReader.nextEvent();
                } else if (event.isStartElement() && event.asStartElement().getName().toString().equalsIgnoreCase(filename)) {
                    event = eventReader.nextEvent();
                } else if (event.isStartElement()) {
                    colN.add(event.asStartElement().getName().toString());
                    event = eventReader.nextEvent();
                } else if (event.isEndElement() && event.asEndElement().getName().toString().equalsIgnoreCase(rootelement)) {
                    C++;
                    event = eventReader.nextEvent();
                } else if (event.isEndElement() && event.asEndElement().getName().toString().equalsIgnoreCase(filename)) {
                    break;
                } else {
                    event = eventReader.nextEvent();
                }
            }
            return C;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RowCounter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(RowCounter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(RowCounter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RowCounter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(RowCounter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
