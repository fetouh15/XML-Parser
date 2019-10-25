/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XSDValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 *
 * @author effat
 */
public class XSDValidation {
    public boolean validatefile(String filename) {
        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream(filename+".xml"));

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(filename+".xsd"));

            javax.xml.validation.Validator validator = schema.newValidator();

            validator.validate(new StAXSource(reader));
        } catch (XMLStreamException | IOException ex) {
            return false;
        } catch (org.xml.sax.SAXException ex) {
            System.out.println("Somethign Went Wrong!");
        }
        return true;
    }

}