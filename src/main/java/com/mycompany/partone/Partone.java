/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.partone;

import java.util.Scanner;

/**
 *
 * @author Ronewa
 */
public class Partone {

    
  public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        
        //This questions are asked from the user and are used in checkUserName, checkPasswordComplexity, checkCellphoneNumber, register user, login user and returnloginuser which are in the external class
        System.out.println("Please enter your first name");
        String firstname = input.nextLine();

        System.out.println("Please enter your last name");
        String lastname = input.nextLine();
       
         System.out.println("Please enter your username,(must contain '_' and be no more than 5 charachter)");
         String username = input.nextLine();
        
        System.out.println("Please enter your password,"
                 + " (must contain 8+ charachter, a capital letter, a number, a special character)");
        String password = input.nextLine();
        
        
        System.out.println("Please enter your cellphone number,(e.g+278449456807");
        String cellphoneNumber = input.nextLine();
        
        
        Registration obj = new Registration(username, cellphoneNumber, password, firstname, lastname);
        obj.registerUser( );
        String Registerresult = obj.registerUser();
        String enteredusername = "";
        String enteredpassword = "";
     //The information collected from these question are used to determine if the user password and username matches with the registered one
        
      
    
        if (Registerresult.equals("Username and password successfully captured. Cell phone number successfully added")){
         System.out.println("Please enter your username");
         enteredusername = input.nextLine();
        
        System.out.println("Please enter your password");
         enteredpassword = input.nextLine();
        }else{
            System.out.println("details are not register correctly");
        }  
      
        
        System.out.println(obj.returnLoginStatus( enteredusername, enteredpassword,firstname, lastname));
    }
        
      
    }
// Refrence list

//YouTube. (2025). MOhiM2SXZl0 [online video]. Available at: https://www.youtube.com/watch?v=MOhiM2SXZl0 [Accessed 15 September 2026]
//Stack Overflow, 2025. Regex pattern for South African cellphone validation. [computer program/code]. Available at: https://stackoverflow.com/... [Accessed 15 September 2026].
// Stack Overflow, 2025. Password complexity validation regex and method. [computer program/code]. Available at: https://stackoverflow.com/... [Accessed 15 September 2026].
// Farrell, J., 2022. Java programming. 10th ed. Boston: Cengage Learning.

    


