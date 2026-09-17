/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.partone;

/**
 *
 * @author Ronewa
 */
public class Registration {
    private String username;
    private String password;
    private String cellphoneNumber;
    public String firstname;
    public String lastname;
    
    // constructor to set all values when creating object
public Registration(String username,String cellphoneNumber,String password,String firstname,String lastname){
        
        this.username = username;
        this.password =  password;
        this.cellphoneNumber = cellphoneNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        
    }
// method to check if username has underscore and is no more than 5 characters
public boolean checkUserName(){
   boolean Usersname;
   
   if (username.length()<=5 && username.contains("_")){
       Usersname = true;
       System.out.println("Username successfully captured");
   }else{
       Usersname = false;
       System.out.println("Username is not correctly formatted;"
               + " please ensure that your username contains an underscore "
               + "and is no more than five characters in length");
   }
     
   return Usersname;
}

// method to check if password length is less than, equal to 8, has an uppercase, has a number, has a special character)
public  boolean checkPasswordComplexity(){ //(Stack Overflow, 2025)
    boolean UserPassword;

    if (password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[0-9].*")
            && password.matches(".*[^a-zA-Z0-9].*")) {
        UserPassword = true;
        System.out.println("Password successfully captured");
    } else {
        UserPassword = false;
        System.out.println("Password is not correctly formatted;"
                + " please ensure that the password contains at least eight characters, a capital letter, "
                + "a number, and a special character.");
    }

    return UserPassword;
}
// method to check if cellphone number is valid and must start with +27 or 27 and followed by digits
public  boolean checkCellPhoneNumber(){
    boolean cellPhoneNo;
     String regexPattern = "^(\\+27)[0-9]{1,10}$"; //(Stack Overflow, 2025),(NumberCrunch Programmers, 2024)
     
    if (cellphoneNumber.matches(regexPattern)){//regex is a  JavaScript regular expression used to validate a standard 10-digit phone number format
        cellPhoneNo = true;
        System.out.println("Cell phone number is successfully added");
    }else{
        cellPhoneNo = false;
        System.out.println("Cell phone number incorrectly formatted or does not contain international code");
    }
    return cellPhoneNo;
        
}
// method to register user by checking username, password, and cellphone number
  public  String registerUser( ){
      
 if (!checkUserName()) {
            return "Username is not correctly formatted; "
                    + "please ensure that your username contains an underscore "
                    + "and is no more than five characters in length";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; "
                    + "please ensure that the password contains at least eight characters, a capital letter,"
                    + " a number, and a special character";
        }
        // This if was not required but i added it because without it the user can still access the login with a fake number
        if (!checkCellPhoneNumber()){
            return "Cell phone number incorrectly formatted or does not contain international code";
         
  }
        return "Username and password successfully captured.";
    }
  // method to check if entered login details match stored username and password
  public boolean loginUser(String enteredusername, String enteredpassword,String firstname, String lastname) {
        boolean logindetails;
        
        if (enteredusername.equals(username) && enteredpassword.equals(password)){
            logindetails = true;
            System.out.println("Welcome " + firstname + " " + lastname + ", it is great to see you again.");
        }else{
            logindetails = false;
            System.out.println("Username or password incorrect, please try again.");
            
        }
        return logindetails;
    }

    // method to return login status message if the login is successfull or failed)
    public String returnLoginStatus(String enteredusername, String enteredpassword, String firstname, String lastname) {
       
        if (loginUser(enteredusername, enteredpassword,firstname,lastname)) {
            
            return "Login details are correct";
        } else {
          
            return "Login details are incorrect,try again!";
        }
    }
    
}
