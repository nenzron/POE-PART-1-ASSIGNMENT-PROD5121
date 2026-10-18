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
    // Welcome message
System.out.println("Welcome to QuickChat.");

// Ask how many messages the user wants to enter
System.out.print("How many messages do you wish to send? ");
int maxMessages = Integer.parseInt(input.nextLine());

// Counts how many messages have been entered so far
int entered = 0;

// The menu keeps showing until the user chooses 3 (Quit)
boolean option = true;

while (option == true) {
    System.out.println("\n1) Send Messages");
    System.out.println("2) Show recently sent messages, Disclaimer: coming soon!");
    System.out.println("3) Quit");
    System.out.print("Choose an option: ");

    int choice = Integer.parseInt(input.nextLine());

    switch (choice) {

        // Option 1: send messages
        case 1 -> {

            // Stop the user from entering more than the number they chose
            if (entered >= maxMessages) {
                System.out.println("You have already entered all " + maxMessages + " messages.");
            } else {

                // Repeat once for each message that is left to enter
                for (int i = entered; i < maxMessages; i++) {

                    // Message number for the screen (starts at 1 instead of 0)
                    int messageNumber = Message.incrementmessageNumber(i);

                    System.out.println("\n------------------------------");
                    System.out.println("Message number: " + messageNumber);

                    // Ask for the recipient until the number is correct
                    System.out.print("Enter recipient cell (e.g. +27831234567): ");
                    String Reciepentcell = input.nextLine();
                    String Reciepentcellresult = Message.checkReciepentCell(Reciepentcell);
                    while (!Reciepentcellresult.equals("Cell phone number successfully captured.")) {
                        System.out.println(Reciepentcellresult);
                        System.out.print("Please enter recipient cell again: ");
                        Reciepentcell = input.nextLine();
                        Reciepentcellresult = Message.checkReciepentCell(Reciepentcell);
                    }
                    System.out.println(Reciepentcellresult);

                    // Ask for the message until it is 250 characters or less
                    System.out.print("Please enter your message (max of 250 characters): ");
                    String Textmessage = input.nextLine();
                    String Textmessageresult = Message.checkmessagelenght(Textmessage);
                    while (!Textmessageresult.equals("Message is ready to send.")) {
                        System.out.println(Textmessageresult);
                        System.out.print("Please enter your message again(Max 250 Characters): ");
                        Textmessage = input.nextLine();
                        Textmessageresult = Message.checkmessagelenght(Textmessage);
                    }
                    System.out.println(Textmessageresult);

                    // Create the message, then give it an ID and a hash
                   Message msg = new Message("", Reciepentcell, "", "",
        Message.returnTotalMessages(), messageNumber, Textmessage, "", "");
                    msg.generateMessageID();
                    msg.createMessagehash();

                    // Ask the user to send, disregard or store the message
                    String result = msg.sentMessage();
                    System.out.println(result);

                    if (result.equals("Message successfully sent.")) {
                        // Show the full details and the running total
                        System.out.println("\n------------------------------");
                        System.out.println("\n" + msg.details());
                        System.out.println("The number of messages sent is: " + Message.returnTotalMessages());
                        entered++;
                    } else if (result.equals("Message successfully stored")) {
                        entered++;
                    } else if (result.equals("Press 0 to delete the message.")) {
                        entered++;   // deleted message still uses up one of the messages
                    } else {
                        i--;         // wrong option, so ask for this message again
                    }
                }

                // Show the total once all the messages have been entered
            System.out.println("\n------------------------------");
            System.out.println("All messages sent:\n");
            System.out.println(Message.printMessages());
            System.out.println("Total number of messages sent: " + Message.returnTotalMessages());
            System.out.println("All messages entered. Choose 3 to quit.");
            }
        }

        // Option 2: not finished yet
        case 2 -> System.out.println("Coming Soon.");

        // Option 3: quit the program
        case 3 -> {
            option = false;
            System.out.println("Goodbye.");
            input.close();
        }

        // Anything else is not a valid menu choice
        default -> System.out.println("Invalid option, please choose 1, 2 or 3.");
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

    


