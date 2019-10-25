package eg.edu.alexu.csd.oop.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change t * To change this license header, choose License Headers in Project Properties.
his template file, choose Tools | Templates
 * and open the template in the editor.
 */
import XSDValidator.XSDValidation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
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
public class Select {

    public Select() {
    }

    public Object[][] Select(String filename, String columnname, String operator, String value, ArrayList<String> colD) {
        XSDValidation validate = new XSDValidation();
        boolean isValid = validate.validatefile(filename);
        if (isValid) {
            try {
                ArrayList<String> kimo = new ArrayList();
                Select S = new Select();
                int count = 0;
                XMLOutputFactory outFactory = XMLOutputFactory.newInstance();
                XMLInputFactory inFactory = XMLInputFactory.newInstance();
                XMLEventReader eventReader = inFactory.createXMLEventReader(new FileInputStream(filename + ".xml"));
                XMLEventReader eventReader1 = inFactory.createXMLEventReader(new FileInputStream(filename + ".xml"));

                XMLEventFactory eventFactory = XMLEventFactory.newInstance();
                XMLEvent end = eventFactory.createDTD("\n");
                XMLEvent tab = eventFactory.createDTD("\t");

                Scanner s = new Scanner(System.in);

                XMLEvent event = eventReader.nextEvent();
                event = eventReader.nextEvent();
                boolean found;
                boolean isElement;
                boolean displayed = false;
                ArrayList<String> colV = new ArrayList<>();
                ArrayList<String> colN = new ArrayList<>();

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
                        String nm1 = first.getAttribute("type");
                        colNT.add(nm1);
                    }
                }
                while (eventReader1.hasNext()) {
                    event = eventReader1.nextEvent();
                    if (event.isStartElement() && event.asStartElement().getName().toString().equalsIgnoreCase(rootelement)) {
                        found = false;
                        isElement = false;
                        event = eventReader1.nextEvent();

                        if (operator.equalsIgnoreCase("=")) {
                            while (!event.isEndElement() || !(event.asEndElement().getName().toString().equalsIgnoreCase(rootelement))) {
                                if (event.isCharacters() && event.asCharacters().getData().equalsIgnoreCase(value) && isElement) {
                                    colV.add(event.asCharacters().getData());
                                    event = eventReader1.nextEvent();
                                    found = true;
                                } else if (event.isStartElement() && event.asStartElement().getName().toString().equalsIgnoreCase(columnname)) {
                                    isElement = true;
                                    event = eventReader1.nextEvent();
                                } else if (event.toString().contains("\n") || event.toString().contains("\t") || (event.isStartElement() || event.isEndElement())) {
                                    event = eventReader1.nextEvent();
                                } else if (event.isEndElement() && event.asEndElement().getName().toString().equalsIgnoreCase(rootelement)) {
                                    break;
                                } else {
                                    colV.add(event.asCharacters().getData());
                                    event = eventReader1.nextEvent();
                                }
                            }
                        } else if (operator.equalsIgnoreCase(">")) {
                            while (!event.isEndElement() || !(event.asEndElement().getName().toString().equalsIgnoreCase(rootelement))) {
                                if (event.isCharacters() && S.isInteger(event.asCharacters().getData()) && (Integer.parseInt(event.asCharacters().getData()) > Integer.parseInt(value)) && isElement) {
                                    colV.add(event.asCharacters().getData());
                                    event = eventReader1.nextEvent();
                                    found = true;
                                } else if (event.isStartElement() && event.asStartElement().getName().toString().equalsIgnoreCase(columnname)) {
                                    isElement = true;
                                    event = eventReader1.nextEvent();
                                } else if (event.isEndElement() && event.asEndElement().getName().toString().equalsIgnoreCase(columnname) && !found) {
                                    isElement = false;
                                    event = eventReader1.nextEvent();
                                } else if (event.toString().contains("\n") || event.toString().contains("\t") || (event.isStartElement() || event.isEndElement())) {
                                    event = eventReader1.nextEvent();
                                } else if (event.isEndElement() && event.asEndElement().getName().toString().equalsIgnoreCase(rootelement)) {
                                    break;
                                } else {
                                    colV.add(event.asCharacters().getData());
                                    event = eventReader1.nextEvent();
                                }
                            }
                        } else if (operator.equalsIgnoreCase("<")) {
                            while (!event.isEndElement() || !(event.asEndElement().getName().toString().equalsIgnoreCase(rootelement))) {
                                if (event.isCharacters() && S.isInteger(event.asCharacters().getData()) && (Integer.parseInt(event.asCharacters().getData()) < Integer.parseInt(value)) && isElement) {
                                    colV.add(event.asCharacters().getData());
                                    event = eventReader1.nextEvent();
                                    found = true;
                                } else if (event.isStartElement() && event.asStartElement().getName().toString().equalsIgnoreCase(columnname)) {
                                    isElement = true;
                                    event = eventReader1.nextEvent();
                                } else if (event.isEndElement() && event.asEndElement().getName().toString().equalsIgnoreCase(columnname) && !found) {
                                    isElement = false;
                                    event = eventReader1.nextEvent();
                                } else if (event.toString().contains("\n") || event.toString().contains("\t") || (event.isStartElement() || event.isEndElement())) {
                                    event = eventReader1.nextEvent();
                                } else if (event.isEndElement() && event.asEndElement().getName().toString().equalsIgnoreCase(rootelement)) {
                                    break;
                                } else {
                                    colV.add(event.asCharacters().getData());
                                    event = eventReader1.nextEvent();
                                }
                            }
                        }
                        if (found && isElement) {
                            int j = 0;
                            for (int c = 0; c < colN.size(); c++) {
                                if (colD.contains(colN.get(c).toString())) {
                                    System.out.println(colN.get(c) + ":" + colV.get(j));
                                    kimo.add(
                                            colV.get(j));
                                    displayed = true;
                                }
                                j++;
                            }
                            count++;
                        }
                        colV.clear();
                    } else {

                    }
                }
                if (!displayed) {
                    System.out.println("No Contacts meet the criteria!");
                } else {
                    System.out.println("Contacts Found:" + count);
                    Object[][] data = new Object[count][colD.size()];
                    int F = 0;
                    String[] kimos = new String[kimo.size()];
                    kimos = kimo.toArray(kimos);
                    for (int A = 0; A < count; A++) {
                        for (int B = 0; B < colD.size(); B++) {
                            data[A][B] = kimos[F];
                            F++;
                        }
                    }
                    return data;
                }
            } catch (FileNotFoundException | XMLStreamException ex) {
                Logger.getLogger(Select.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Select.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(Select.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Select.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            System.out.println("File is Invalid!");
        }
        return null;
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}
