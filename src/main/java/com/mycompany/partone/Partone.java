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
        
      
    
        if (Registerresult.equals("Username and password successfully captured.")){
         System.out.println("Please enter your username");
         enteredusername = input.nextLine();
        
        System.out.println("Please enter your password");
         enteredpassword = input.nextLine();
        

        }else{
            System.out.println("details are not register correctly");
        }  
        
        String loginStatus = obj.returnLoginStatus(enteredusername, enteredpassword, firstname, lastname);
        System.out.println(loginStatus);

        // Users may only use QuickChat if they logged in successfully
        if (loginStatus.equals("Login details are correct")) {
            Partone old = new Partone();
            old.runQuickChat();
        }
  }

    // QuickChat menu and message loop (Part 2)
    public  void runQuickChat() {
        Scanner input = new Scanner(System.in);
    
        System.out.println("Welcome to QuickChat.");
        
    System.out.print("How many messages do you wish to enter? ");
    int maxMessages = input.nextInt();
    int entered = 0;

        
    
         int choice = 0;
    while (choice != 3) {
        System.out.println("\n1) Send Messages");
        System.out.println("2) Show recently sent messages");
        System.out.println("3) Quit");
        System.out.print("Choose an option: ");

        choice = Integer.parseInt(input.nextLine());

         switch (choice) {
        case 1:
            // your send messages code goes here
            System.out.println("Send Messages selected");

            // NEW: only allow the set number of messages
             if (entered > maxMessages) {
                    System.out.println("You have already entered all " + maxMessages + " messages.");
                    break; // leaves the switch, back to the menu
                }
            
            

            // NEW: for loop for the remaining messages
            for (int i = entered; i <= maxMessages; i++) {
                System.out.print("Enter recipient cell (e.g. +27831234567): ");
                String Reciepentcell = input.nextLine();

                System.out.print("Enter your message (max 250 characters): ");
                String text = input.nextLine();

                if (text.length() > 250) {
                    System.out.println("Message exceeds 250 characters by "
                            + (text.length() - 250) + ", please reduce size.");
                    i--;
                    continue;
                }
                
          String messageID = "";
          
          String messagehash = "";
          String printMessage = "";
          String Totalmessage = "";
          String messageNumber = "";
        Message msg = new Message( messageID,Reciepentcell, messagehash,
         printMessage, Totalmessage,messageNumber);
                String cellCheck = msg.checkReciepentCell();
                System.out.println(cellCheck);
                if (!cellCheck.equals("Cell phone number successfully captured.")) {
                    i--;
                    continue;
                }

                System.out.println("1) Send  2) Disregard  3) Store");
                int action = Integer.parseInt(input.nextLine());
                System.out.println(msg.sentMessage());

                if (action == 1 || action == 3) {
                    System.out.println(msg.printMessage());
                    entered++;
                }
            }
            break;
        case 2:
            System.out.println("Coming Soon.");
            break;
        case 3:
            System.out.println("Goodbye.");
            input.close();
            break;
        default:
            System.out.println("Invalid option, please choose 1, 2 or 3.");
    }
}
    }
    }



// Refrence list

//YouTube. (2025). MOhiM2SXZl0 [online video]. Available at: https://www.youtube.com/watch?v=MOhiM2SXZl0 [Accessed 15 September 2026]
//Stack Overflow, 2025. Regex pattern for South African cellphone validation. [computer program/code]. Available at: https://stackoverflow.com/... [Accessed 15 September 2026].
// Stack Overflow, 2025. Password complexity validation regex and method. [computer program/code]. Available at: https://stackoverflow.com/... [Accessed 15 September 2026].
// Farrell, J., 2022. Java programming. 10th ed. Boston: Cengage Learning.
//NumberCrunch Programmers. (2024). Java program to validate South African phone numbers using RegeX [online video]. Available at: https://www.youtube.com/watch?v=2M1CpEJZ6rk [Accessed 17 September 2026].

    


