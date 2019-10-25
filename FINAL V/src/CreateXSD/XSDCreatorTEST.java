/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CreateXSD;

import java.util.ArrayList;

/**
 *
 * @author effat
 */
public class XSDCreatorTEST {
    public static void main(String[] args) {
        XSDCreator A=new XSDCreator();
        ArrayList<String> colN=new ArrayList<>();
        ArrayList<String> colNT=new ArrayList<>();
        colN.add("Name");
        colNT.add("varchar");
        colN.add("Age");
        colNT.add("integer");
        A.CreateXSD("Effat", colN, colNT);
    }
}
