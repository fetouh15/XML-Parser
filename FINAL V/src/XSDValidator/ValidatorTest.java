/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XSDValidator;

/**
 *
 * @author effat
 */
public class ValidatorTest {
        public static void main(String[] args) {
        XSDValidation X=new XSDValidation();
        boolean x=X.validatefile("test");  
        if(x){
            System.out.println("Success!");
        }
        else{
            System.out.println("Fail :(");
        }
    }
}
