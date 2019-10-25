package eg.edu.alexu.csd.oop.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import XSDValidator.ValidatorTest;
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
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.XMLEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author effat
 */
public class DeleteRow {

    public DeleteRow() {
    }

    public void DeleteRow(String filename, String columnname, String operator, String value) throws IOException {
        XSDValidation validate = new XSDValidation();
        boolean isValid = validate.validatefile(filename);
        if (isValid) {
            try {

                DeleteRow D = new DeleteRow();
                XMLOutputFactory outFactory = XMLOutputFactory.newInstance();
                XMLInputFactory inFactory = XMLInputFactory.newInstance();
                FileInputStream fr = new FileInputStream(filename + ".xml");
                FileOutputStream f = new FileOutputStream("temp2.xml");

                XMLEventReader eventReader = inFactory.createXMLEventReader(fr);
                XMLEventWriter eventWriter = outFactory.createXMLEventWriter((f));
                XMLEventFactory eventFactory = XMLEventFactory.newInstance();

                XMLEvent end = eventFactory.createDTD("\n");
                XMLEvent tab = eventFactory.createDTD("\t");
                StartDocument startDocument = eventFactory.createStartDocument();
                eventWriter.add(startDocument);
                eventWriter.add(end);
                XMLEvent event = eventFactory.createStartElement("", "", filename);
                eventWriter.add(event);
                event = eventFactory.createNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
                eventWriter.add(event);
                event = eventFactory.createNamespace("noNamespaceSchemaLocation", filename + ".xsd");
                eventWriter.add(event);
                eventWriter.add(end);

                event = eventReader.nextEvent();
                event = eventReader.nextEvent();
                ArrayList<String> colV = new ArrayList<>();
                ArrayList<String> colN = new ArrayList<>();
                boolean isElement;

                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder;
                docBuilder = docBuilderFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(new File(filename + ".xsd"));
                NodeList list = doc.getElementsByTagName("xs:element");

                ArrayList<String> colNT = new ArrayList<>();

                Element rootelement1 = (Element) list.item(1);
                String rootelement = rootelement1.getAttribute("name");

                for (int i = 2; i < list.getLength(); i++) {
                    Element first = (Element) list.item(i);
                    if (first.hasAttributes()) {
                        String nm = first.getAttribute("name");
                        colN.add(nm);

                    }
                }

                while (eventReader.hasNext()) {

                    event = eventReader.nextEvent();

                    if (event.isStartElement() && event.asStartElement().getName().toString().equalsIgnoreCase(rootelement)) {

                        event = eventReader.nextEvent();
                        isElement = false;
                        if (operator.equalsIgnoreCase("=")) {
                            while (!event.isEndElement() || !(event.asEndElement().getName().toString().equalsIgnoreCase(rootelement))) {
                                if (event.isCharacters() && event.asCharacters().getData().equalsIgnoreCase(value) && isElement) {
                                    colV.clear();
                                    break;
                                } else if (event.isStartElement() && event.asStartElement().getName().toString().equalsIgnoreCase(columnname)) {
                                    isElement = true;
                                    event = eventReader.nextEvent();
                                } else if (event.toString().contains("\n") || event.toString().contains("\t") || (event.isStartElement() || event.isEndElement())) {
                                    event = eventReader.nextEvent();
                                } else if (event.isEndElement() && event.asEndElement().getName().toString().equalsIgnoreCase(rootelement)) {
                                    break;
                                } else {
                                    colV.add(event.asCharacters().getData());
                                    event = eventReader.nextEvent();
                                }
                            }
                        } else if (operator.equalsIgnoreCase(">")) {
                            while (!event.isEndElement() || !(event.asEndElement().getName().toString().equalsIgnoreCase(rootelement))) {
                                if (event.isCharacters() && D.isInteger(event.asCharacters().getData()) && (Integer.parseInt(event.asCharacters().getData()) > Integer.parseInt(value)) && isElement) {
                                    colV.clear();

                                    break;
                                } else if (event.isStartElement() && event.asStartElement().getName().toString().equalsIgnoreCase(columnname)) {
                                    isElement = true;
                                    event = eventReader.nextEvent();
                                } else if (event.isEndElement() && event.asEndElement().getName().toString().equalsIgnoreCase(columnname) && !colV.isEmpty()) {
                                    isElement = false;
                                    event = eventReader.nextEvent();
                                } else if (event.toString().contains("\n") || event.toString().contains("\t") || (event.isStartElement() || event.isEndElement())) {
                                    event = eventReader.nextEvent();
                                } else if (event.isEndElement() && event.asEndElement().getName().toString().equalsIgnoreCase(rootelement)) {
                                    break;
                                } else {
                                    colV.add(event.asCharacters().getData());
                                    event = eventReader.nextEvent();
                                }
                            }
                        } else if (operator.equalsIgnoreCase("<")) {
                            while (!event.isEndElement() || !(event.asEndElement().getName().toString().equalsIgnoreCase(rootelement))) {
                                if (event.isCharacters() && D.isInteger(event.asCharacters().getData()) && (Integer.parseInt(event.asCharacters().getData()) < Integer.parseInt(value)) && isElement) {
                                    colV.clear();

                                    break;
                                } else if (event.isStartElement() && event.asStartElement().getName().toString().equalsIgnoreCase(columnname)) {
                                    isElement = true;
                                    event = eventReader.nextEvent();
                                } else if (event.isEndElement() && event.asEndElement().getName().toString().equalsIgnoreCase(columnname) && !colV.isEmpty()) {
                                    isElement = false;
                                    event = eventReader.nextEvent();
                                } else if (event.toString().contains("\n") || event.toString().contains("\t") || (event.isStartElement() || event.isEndElement())) {
                                    event = eventReader.nextEvent();
                                } else if (event.isEndElement() && event.asEndElement().getName().toString().equalsIgnoreCase(rootelement)) {
                                    break;
                                } else {
                                    colV.add(event.asCharacters().getData());
                                    event = eventReader.nextEvent();
                                }
                            }
                        }
                        if (!colV.isEmpty()) {
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
                                character = eventFactory.createCharacters(colV.get(j));
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
                            colV.clear();
                        }
                    } else {
                    }

                }

                eventWriter.add(eventFactory.createEndDocument());
                eventWriter.flush();
                eventWriter.close();
                eventReader.close();

                f.flush();
                f.close();
                fr.close();

            } catch (FileNotFoundException | XMLStreamException ex) {
                Logger.getLogger(DeleteRow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DeleteRow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(DeleteRow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(DeleteRow.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.gc();
            File file = new File(filename + ".xml");
            file.delete();

            Path source = Paths.get("temp2.xml");
            Files.move(source, source.resolveSibling(filename + ".xml"));

        } else {
            System.out.println("File is Invalid or Empty!");
        }
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
