package eg.edu.alexu.csd.oop.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fetouh
 */
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
public class JTABLE2 extends JFrame{
 

    public JTABLE2(Object data[][], String []columns)
    {
       
        JTable table = new JTable(data, columns);
         
        this.add(new JScrollPane(table));
         
        this.setTitle("Table");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        this.pack();
        this.setVisible(true);
    }
     
}
    

