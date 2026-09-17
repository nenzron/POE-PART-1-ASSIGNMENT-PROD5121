/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.partone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Ronewa
 */
public class RegistrationTest {
    
    public RegistrationTest() {
    }
 // test to check if username has underscore and is at least 5 characters, testing the data "Kyl_1" to be valid using assertEquals
 @Test
    public void testloginUserEqualTrue() {
    String username = "Kyl_1";
    String password = "";
    String cellphoneNumber = "";
    String enteredusername = "Kyl_1";
    String enteredpassword = "";
    String firstname = "Ray";
    String lastname = "Kay";

    Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
    
   boolean expected = true;
    boolean actual = obj.loginUser(enteredusername, enteredpassword, firstname, lastname);

    assertEquals(expected, actual);
   }
   // test to check if username has underscore and is at least 5 characters, testing the data "Kyle!!!!!" to be invalid using assertEquals
    @Test
    public void testregisterUserFalse() {
    String username = "Kyle!!!!!";
    String password = "";
    String cellphoneNumber = ""; 
    String firstname = "Jay";
    String lastname = "Kay";
    
    Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
    boolean expected = false;
    boolean actual = obj.checkUserName();
    
    
   assertEquals(expected, actual,"The username does not contain an "
           + "underscore and is no more than five charachters long");
   } 
    // test to check if password length is less than, equal to 8, has an uppercase, has a number, has a special character, testing the data Ch&&sec@ke99! to be valid using assertequals
   @Test
    public void testcheckPasswordComplexityEqualTrue(){
    String username = "";
    String password = "Ch&&sec@ke99!";
    String cellphoneNumber = ""; 
    String firstname = "";
    String lastname = "";
    
    Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
    boolean expected = true;
    boolean actual = obj.checkPasswordComplexity();
    assertEquals(expected,actual,"The password meets the complexity requirements");
    }
    // test to check if password length is less than, equal to 8, has an uppercase, has a number, has a special character, testing the data password to be invalid using assertequals
    @Test
    public void testcheckPasswordComplexityEqualFalse(){
    String username = "";
    String password = "passowrd";
    String cellphoneNumber = ""; 
    String firstname = "";
    String lastname = "";
    
    Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
    boolean expected = false;
    boolean actual = obj.checkPasswordComplexity();
    assertEquals(expected,actual,"The password does not meets the complexity requirements");
    }
    // test to check if cellphone number is valid and must start with +27 or 27 and followed by digits, testing the data +27838968976 to be valid using assertequals
    @Test
    public void testcheckCellPhoneNumberEqualsTrue(){
    String username = "";
    String password = "";
    String cellphoneNumber = "+27838968976"; 
    String firstname = "";
    String lastname = "";
    
    Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
    boolean expected = true;
    boolean actual = obj.checkCellPhoneNumber();
    assertEquals(expected,actual,"The cellphone number is correctly formated");
    }
       // test to check if cellphone number is valid and must start with +27 or 27 and followed by digits, testing the data 08966553 to be invalid using assertequals
    @Test
    public void testcheckCellPhoneNumberEqualsFalse(){
    String username = "";
    String password = "";
    String cellphoneNumber = "08966553"; 
    String firstname = "";
    String lastname = "";
    
    Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
    boolean expected = false;
    boolean actual = obj.checkCellPhoneNumber();
    assertEquals(expected,actual,"The cellphone number is incorrectly formated");
    }
    // test to check if entered login details match stored username and password,testing the data "Kyl_1" and Ch&sec@ke99!" to be true using asserttrue

@Test 

    public void testreturnLoginStatusTrue() {
        String username = "Kyl_1";
        String password = "Ch&sec@ke99!";
        String enteredusername = "Kyl_1";
        String enteredpassword = "Ch&sec@ke99!";
        String cellphoneNumber = "";
        String firstname = "";
        String lastname = "";

        Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
       obj.returnLoginStatus(enteredusername,enteredpassword,firstname,lastname);

        
        assertTrue(obj.loginUser("Kyl_1","Ch&sec@ke99!","Dan","John"));
    }
    
     // test to check if entered login details match stored username and password,testing the data "Kyl_1","Kyl_2" and Ch&sec@ke99!" to be false using assertfalse
    @Test 

    public void testreturnLoginStatusFalse() {
        String username = "Kyl_1";
        String password = "Ch&sec@ke99!";
        String enteredusername = "Kyl_2";
        String enteredpassword = "Ch&sec@ke99!";
        String cellphoneNumber = "";
        String firstname = "";
        String lastname = "";

        Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
        obj.returnLoginStatus(enteredusername,enteredpassword,firstname,lastname);
       

        
        assertFalse(obj.loginUser("Kyl_2","Ch&sec@ke99!","Dan","Jhon"));


        
}
    // test to check if username has underscore and is no more than 5 characters, testing the data Kyl_1 to be true using asserttrue
    @Test 
    public void testcheckUserNameTrue(){
       String username = "Kyl_1";
       String password = "";
       String cellphoneNumber = "";
       String firstname = "";
       String lastname = "";
       
       Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
       obj.checkUserName();
       
       assertTrue(obj.checkUserName());
    }
    // test to check if username has underscore and is no more than 5 characters, testing the data Kyl!!!!! to be false using assertfalse
     @Test 
    public void testcheckUserNameFalse(){
       String username = "Kyl!!!!!";
       String password = "";
       String cellphoneNumber = "";
       String firstname = "";
       String lastname = "";
       
       
       Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
       obj.checkUserName();
       
       assertFalse(obj.checkUserName());
    }
    // test to check if password length is less than, equal to 8, has an uppercase, has a number, has a special character, testing the data Ch&&sec@ke99! to be true using asserttrue
    @Test
    public void testcheckPasswordComplexityTrue(){
    String username = "";
    String password = "Ch&&sec@ke99!";
    String cellphoneNumber = ""; 
    String firstname = "";
    String lastname = "";
    
    Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
    obj.checkPasswordComplexity();
    
    assertTrue(obj.checkPasswordComplexity());
    
}
    // test to check if password length is less than, equal to 8, has an uppercase, has a number, has a special character, testing the data password to be false using assertfalse
   @Test
    public void testcheckPasswordComplexityFalse(){
    String username = "";
    String password = "password";
    String cellphoneNumber = ""; 
    String firstname = "";
    String lastname = "";
    
    Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
    obj.checkPasswordComplexity();
    
    assertFalse(obj .checkPasswordComplexity());
    } 
    // test to check if cellphone number is valid and must start with +27 or 27 and followed by digits, by testing the data +27838968976 to be true using asserttrue
     @Test
    public void testcheckCellPhoneNumberTrue(){
    String username = "";
    String password = "";
    String cellphoneNumber = "+27838968976"; 
    String firstname = "";
    String lastname = "";
    
    Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
    obj.checkCellPhoneNumber();
    
    assertTrue(obj .checkCellPhoneNumber());
    }
     // test to check if cellphone number is valid and must start with +27 or 27 and followed by digits, by testing the data 08966553 to be false using assertfalse
    @Test
    public void testcheckCellPhoneNumberFalse(){
    String username = "";
    String password = "";
    String cellphoneNumber = "08966553"; 
    String firstname = "";
    String lastname = "";
    
    Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
    obj.checkCellPhoneNumber();
    assertFalse(obj .checkCellPhoneNumber());
    }
    
    
}
    
   
 /*   
@Test
   public void testregisterUserTrue(){
   String username = "Kyl_1";
   String password = "Ch&&sec@ke99!";
   String cellphoneNumber = ""; 
   
   
   Registration obj = new Registration(username, cellphoneNumber, password);
   String expected = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length";
   String actual = obj.registerUser();
   
   assertEquals(expected,actual);
   }
@Test
   public void testregisterUserFalse(){
   String username = "Kyl!!!!";
   String password = "";
   String cellphoneNumber = ""; 
   
   
   Registration obj = new Registration(username, cellphoneNumber, password);
   String expected = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length";
   String actual = obj.registerUser();
   
   assertEquals(expected, actual);
   }


   
}
    
    @Test
    public void testcheckUsernameTrue() {
    String username = "Kyl_1";
    String password = "";
    String cellphoneNumber = ""; 
    Registration obj = new Registration(username, cellphoneNumber, password);
    assertTrue(obj.checkUserName());
        
    }
    @Test
    public void testcheckUsernameFalse(){
    String username = "Kyl!!!!!!!";
    String password = "";
    String cellphoneNumber = ""; 
    Registration obj = new Registration(username, cellphoneNumber, password);
    assertFalse(obj .checkUserName());
    }
    @Test
    public void testcheckPasswordComplexityTrue(){
    String username = "";
    String password = "Ch&&sec@ke99!";
    String cellphoneNumber = ""; 
    Registration obj = new Registration(username, cellphoneNumber, password);
    assertTrue(obj .checkPasswordComplexity());
    }
    @Test
    public void testcheckPasswordComplexityFalse(){
    String username = "";
    String password = "password";
    String cellphoneNumber = ""; 
    Registration obj = new Registration(username, cellphoneNumber, password);
    assertFalse(obj .checkPasswordComplexity());
    }
    @Test
    public void testcheckCellPhoneNumberTrue(){
    String username = "";
    String password = "";
    String cellphoneNumber = "+27838968976"; 
    Registration obj = new Registration(username, cellphoneNumber, password);
    assertTrue(obj .checkCellPhoneNumber());
    }
    @Test
    public void testcheckCellPhoneNumberFalse(){
    String username = "";
    String password = "";
    String cellphoneNumber = "08966553"; 
    Registration obj = new Registration(username, cellphoneNumber, password);
    assertFalse(obj .checkCellPhoneNumber());
    }
*/
    
    

