package eg.edu.alexu.csd.oop.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author effat
 */
import XSDValidator.XSDValidation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author effat
 */
public class Insert {

    public Insert() {
    }

    public void InsertRow(String filename, ArrayList<String> colN, ArrayList<String> Values) throws IOException {

        try {
            XMLOutputFactory outFactory = XMLOutputFactory.newInstance();
            XMLInputFactory inFactory = XMLInputFactory.newInstance();
            FileInputStream fr = new FileInputStream(filename + ".xml");
            FileOutputStream f = new FileOutputStream("temp1.xml");
            XMLEventReader eventReader = inFactory.createXMLEventReader(fr);

            XMLEventWriter eventWriter = outFactory.createXMLEventWriter((f));

            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            XMLEvent tab = eventFactory.createDTD("\t");

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
            docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(filename + ".xsd"));
            NodeList list = doc.getElementsByTagName("xs:element");

            ArrayList<String> colNT = new ArrayList<>();
            ;
            Element rootelement1 = (Element) list.item(1);
            String rootelement = rootelement1.getAttribute("name");

            Scanner s = new Scanner(System.in);

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.END_ELEMENT: {
                        if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(filename)) {

                            int j = 0;

                            event = eventFactory.createStartElement("", "", rootelement);
                            eventWriter.add(tab);
                            eventWriter.add(event);
                            eventWriter.add(end);
                            for (int c = 0; c < colN.size(); c++) {
                                event = eventFactory.createStartElement("", "", colN.get(c));
                                eventWriter.add(tab);
                                eventWriter.add(tab);
                                eventWriter.add(event);
                                Characters character;
                                character = eventFactory.createCharacters(Values.get(j));
                                eventWriter.add(character);
                                EndElement eElement = eventFactory.createEndElement("", "", colN.get(c));
                                eventWriter.add(eElement);
                                eventWriter.add(end);
                                j++;
                            }
                            event = eventFactory.createEndElement("", "", rootelement);
                            eventWriter.add(tab);
                            XMLEvent endElement = eventFactory.createEndElement("", "", rootelement);
                            eventWriter.add(endElement);
                            eventWriter.add(end);

                        }
                        break;
                    }
                }

                eventWriter.add(event);

            }
            eventWriter.flush();
            eventWriter.close();
            eventReader.close();
            f.flush();
            f.close();
            fr.close();

        } catch (FileNotFoundException | XMLStreamException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Insert.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.gc();
        File file = new File(filename + ".xml");
        file.delete();
        Path source = Paths.get("temp1.xml");
        Files.move(source, source.resolveSibling(filename + ".xml"));

    }
}
