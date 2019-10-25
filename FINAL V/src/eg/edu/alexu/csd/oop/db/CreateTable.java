package eg.edu.alexu.csd.oop.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import CreateXSD.XSDCreator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author effat
 */
public class CreateTable {

    /**
     * @param args the command line arguments
     */

    public CreateTable() {
    }
    
    
    public void CreateT(String filename,ArrayList<String> colN,ArrayList<String> colNT) {
        try {
           
           
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEventWriter xmlEventWriter = xmlOutputFactory.createXMLEventWriter(new FileOutputStream(filename+".xml"), "UTF-8");
            XMLEvent event;
            XMLEvent end = eventFactory.createDTD("\n");
            StartDocument startDocument = eventFactory.createStartDocument();
            xmlEventWriter.add(startDocument);
            xmlEventWriter.add(end);
            event = eventFactory.createStartElement("", "", filename);
            xmlEventWriter.add(event);
            event= eventFactory.createNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            xmlEventWriter.add(event);
            event= eventFactory.createNamespace("noNamespaceSchemaLocation", filename+".xsd");
            xmlEventWriter.add(event);
            xmlEventWriter.add(end);
            XMLEvent tab = eventFactory.createDTD("\t");
         
            XSDCreator xsdcreator=new XSDCreator();
            xsdcreator.CreateXSD(filename,colN,colNT );
            xmlEventWriter.add(eventFactory.createEndDocument());
            
            xmlEventWriter.close();
            
            
              

           
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(CreateTable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     

}
