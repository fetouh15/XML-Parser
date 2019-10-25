package eg.edu.alexu.csd.oop.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author fetouh
 */
public class regex {

    String regexG = "\\s*(\\w+)\\s*.+\\s*";
    String regexC = "\\s*CREATE\\s*TABLE\\s*(\\w+)\\s*\\(((\\s*\\w+\\s*(INT|VARCHAR)\\s*,?)+)\\s*\\)\\s*;\\s*";
    String regexD = "\\s*DROP\\s*TABLE\\s*(\\w+)\\s*;\\s*";
    String regexI = "\\s*INSERT\\s*INTO\\s*(\\w+)\\s*\\(\\s*((\\w+\\s*,?\\s*)+)\\s*\\)\\s*VALUES\\s*\\(\\s*((\\w+\\s*,?\\s*)+)\\s*\\)\\s*;\\s*";
    String regexDE = "\\s*DELETE\\s*FROM\\s*(\\w+)\\s*WHERE\\(\\s*(\\w+)\\s*(=|>|<)\\s*(\\w+)\\s*\\)\\s*;\\s*";
    String regexS = "\\s*SELECT\\s*((\\w+\\s*,?)+)\\s*FROM\\s*(\\w+)\\s*WHERE\\s*\\(\\s*(\\w+)\\s*(=|>|<)\\s*(\\w+)\\s*\\)\\s*;\\s*";

    public void setRegexG(String regexG) {
        this.regexG = regexG;

        this.regexC = regexC;

        this.regexD = regexD;

        this.regexI = regexI;

        this.regexDE = regexDE;
    }

    public String getRegexG() {
        return regexG;
    }

    public String getRegexC() {
        return regexC;
    }

    public String getRegexD() {
        return regexD;
    }

    public String getRegexI() {
        return regexI;
    }

    public String getRegexDE() {
        return regexDE;
    }

    Pattern p = Pattern.compile(regexG);
    Matcher M = p.matcher("CREATE TABLE table_name (column1 INT, column2 INT, column3 VARCHAR);");

    public int specify(String general) {
        if (general.equalsIgnoreCase("CREATE")) {
            return 1;
        } else if (general.equalsIgnoreCase("DROP")) {
            return 2;
        } else if (general.equalsIgnoreCase("INSERT")) {
            return 3;
        } else if (general.equalsIgnoreCase("SELECT")) {
            return 4;
        } else if (general.equalsIgnoreCase("DELETE")) {
            return 5;
        }
        return 0;
    }

    DatabaseImpl imp = new DatabaseImpl();

    String input;
    String input2;

    public void function(int x) throws SQLException {
        switch (x) {

            case (1):
                imp.executeStructureQuery(input);
                break;
            case (2):
                imp.executeStructureQuery(input);
                break;
            case (3):
                imp.executeUpdateQuery(input);
                break;
            case (4):
                imp.executeRetrievalQuery(input);
                break;
            case (5):
                imp.executeUpdateQuery(input);
                break;

        }

    }

    public String getInput2() {
        return input2;
    }

    public void setInput2(String input2) {
        this.input2 = input2;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
