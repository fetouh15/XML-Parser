package eg.edu.alexu.csd.oop.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;

/**
 *
 * @author effat
 */
public class Delete {


    public Delete() {
    }

    public void DeleteF(String filename) {
        String filenameD = filename + ".xml";
        File file = new File(filenameD);
        File fileXSD = new File(filename + ".xsd");
        boolean delete = file.delete();
        delete = fileXSD.delete();
        if (delete) {
            System.out.println(filenameD + " File Deleted Succesfully");
        } else {
            System.out.println("Delete Failed!");
        }
    }
}
