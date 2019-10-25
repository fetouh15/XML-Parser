/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CreateXSD;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author effat
 */
public class XSDCreator {
    public void CreateXSD(String filename, ArrayList<String>colN,ArrayList<String>colNT){
        FileWriter FW=null;
        try {
            FW = new FileWriter(filename+".xsd");
            BufferedWriter BW=new BufferedWriter(FW);
            BW.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            BW.flush();
            BW.write("\t<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">");
            BW.flush();
            BW.write("\t\t<xs:element name=\""+filename+"\">\n");
            BW.flush(); 
            BW.write("\t\t\t<xs:complexType>\n");
            BW.flush();
            BW.write("\t\t\t<xs:sequence>\n");
            BW.flush();
            BW.write("\t\t\t<xs:element name=\"Person\" maxOccurs=\"unbounded\">\n");
            BW.flush();
            BW.write("\t\t\t\t<xs:complexType>\n\t\t\t\t<xs:sequence>\n");
            BW.flush();
            for(int i=0;i<colN.size();i++){
                if(colNT.get(i).equalsIgnoreCase("varchar")){
                    BW.write("\t\t<xs:element name=\"" +colN.get(i) +"\" type=\"xs:string\"/>\n");
                    BW.flush();
                }
                else if(colNT.get(i).equalsIgnoreCase("integer")||colNT.get(i).equalsIgnoreCase("int")){
                BW.write("\t\t<xs:element name=\"" +colN.get(i) +"\" type=\"xs:integer\"/>\n");
                BW.flush();
                }
            }
            BW.write("\t\t\t\t\t\t</xs:sequence>\n"
                    + "\t\t\t\t\t</xs:complexType>\n"
                    + "\t\t\t\t</xs:element>\n"
                    + "\t\t\t</xs:sequence>\n"
                    + "\t\t</xs:complexType>\n"
                    + "\t</xs:element>\n"
                    + "</xs:schema>");
            BW.flush();
            BW.close();
        
        
        } catch (IOException ex) {
            Logger.getLogger(XSDCreator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                FW.close();
            } catch (IOException ex) {
                Logger.getLogger(XSDCreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
