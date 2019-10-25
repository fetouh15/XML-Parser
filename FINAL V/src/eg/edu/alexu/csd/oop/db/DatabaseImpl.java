package eg.edu.alexu.csd.oop.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fetouh
 */
public class DatabaseImpl implements Database {

    String filename, filenameD;

    @Override
    public boolean executeStructureQuery(String query) throws SQLException {

        try {

            String regexC = "\\s*CREATE\\s*TABLE\\s*(\\w+)\\s*\\(((\\s*\\w+\\s*(INT|VARCHAR)\\s*,?)+)\\s*\\)\\s*;\\s*";
            String regexD = "\\s*DROP\\s*TABLE\\s*(\\w+)\\s*;\\s*";
            String regexT = "\\s*(\\w+)\\s*(INT|VARCHAR)\\s*";

            Pattern p = Pattern.compile(regexC);
            Matcher M = p.matcher(query);
            Pattern p2 = Pattern.compile(regexD);
            Matcher M2 = p2.matcher(query);

            String condition = null;

            if (M.matches()) {
                filename = M.group(1);

                String data = null;
                data = M.group(2);

                String[] datas;
                datas = data.split("[(,)=]+|[();]");

                ArrayList<String> CN = new ArrayList<>();
                ArrayList<String> CT = new ArrayList<>();
                Pattern p3 = Pattern.compile(regexT);
                Matcher M3;

                for (int i = 0; i < datas.length; i++) {
                    M3 = p3.matcher(datas[i]);
                    if (M3.find()) {
                        CN.add(M3.group(1));
                        CT.add(M3.group(2));
                    }
                }
                CreateTable writer = new CreateTable();
                writer.CreateT(filename, CN, CT);

            } else if (M2.matches()) {
                filename = M2.group(1);
                Delete D = new Delete();
                D.DeleteF(filename);

            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override

    public int executeUpdateQuery(String query) throws SQLException {
        String regexI = "\\s*INSERT\\s*INTO\\s*(\\w+)\\s*\\(\\s*((\\w+\\s*,?\\s*)+)\\s*\\)\\s*VALUES\\s*\\(\\s*((\\w+\\s*,?\\s*)+)\\s*\\);\\s*";
        String regexDE = "\\s*DELETE\\s*FROM\\s*(\\w+)\\s*WHERE\\s*\\(\\s*(\\w+)\\s*(=|>|<)\\s*(\\w+)\\s*\\);\\s*";
        Pattern p = Pattern.compile(regexI);
        int C = 0;
        Matcher M = p.matcher(query);
        if (M.matches()) {
            try {
                String data = null, data2 = null;
                filename = M.group(1);
                data = M.group(2);
                data2 = M.group(4);
                ArrayList<String> datas = new ArrayList<>(Arrays.asList(data.split("[ (,)=]+|[ ( );]")));
                ArrayList<String> datas2 = new ArrayList<>(Arrays.asList(data2.split("[ (,)=]+|[ ( );]")));
                Insert ins = new Insert();
                ins.InsertRow(filename, datas, datas2);
                RowCounter R = new RowCounter();
                C = R.CountR(M.group(1));

            } catch (IOException ex) {
                Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            p = Pattern.compile(regexDE);
            Matcher M2 = p.matcher(query);
            if (M2.find()) {
                try {
                    filename = M2.group(1);

                    String CN = M2.group(2);
                    String OP = M2.group(3);
                    String CV = M2.group(4);

                    DeleteRow DR = new DeleteRow();
                    DR.DeleteRow(filename, CN, OP, CV);

                    RowCounter R = new RowCounter();

                    C = R.CountR(M2.group(1));
                } catch (IOException ex) {
                    Logger.getLogger(DatabaseImpl.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        System.out.println(C);

        return C;
    }

    @Override
    public Object[][] executeRetrievalQuery(String query) throws SQLException {
        String regexS = "\\s*SELECT\\s*((\\w+\\s*,?\\s)+)\\s*FROM\\s*(\\w+)\\s*WHERE\\s*\\(\\s*(\\w+)\\s*(=|>|<)\\s*(\\w+)\\s*\\)\\s*;\\s*";
        Pattern p = Pattern.compile(regexS);
        Matcher M;
        M = p.matcher(query);
        if (M.find()) {
            filename = M.group(3);
            String data;
            data = M.group(1);
            String CN = M.group(4);
            String OP = M.group(5);
            String CV = M.group(6);

            ArrayList<String> datas = new ArrayList<>(Arrays.asList(data.split("[(,)=]+|[();]")));
            ArrayList<String> datas2 = new ArrayList<>();

            for (int i = 0; i < datas.size(); i++) {

                data = datas.get(i);
                data = data.trim();
                datas2.add(data);

            }

            String[] datasa = new String[datas2.size()];
            datasa = datas2.toArray(datasa);

            Select SE = new Select();
            Object a[][] = SE.Select(filename, CN, OP, CV, datas2);

            JTABLE2 table = new JTABLE2(a, datasa);

            return a;

        }

        return null;

    }
}
