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
    
     // Part 3: the name of the logged-in user. Only this user can send messages, so this is
    // the "sender" shown for the stored messages.
    private String sender = "";

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
             old.sender = firstname + " " + lastname;   // Part 3: remember who is logged in
            old.runQuickChat();
        } 

  }
  

    // QuickChat menu and message loop (Part 2)
     public  void runQuickChat() {
        Scanner input = new Scanner(System.in);
    // Welcome message
System.out.println("Welcome to QuickChat.");


// Ask how many messages the user wants to enter
System.out.println("\n------------------------------");
System.out.print("How many messages do you wish to send? ");
int maxMessages = Integer.parseInt(input.nextLine());

// Counts how many messages have been entered so far
int entered = 0;

// ---------------------------------------------------------------------------
// PART 3: THE ARRAYS (normal arrays)
// Each array is made with the number the user typed in (maxMessages), the same way the
// golf members array is made in class: new String[golfmember] (Farrell, 2022, p. 267).
// Nothing is hard-coded: the arrays start empty and are filled below while the user
// sends, disregards or stores messages.
// ---------------------------------------------------------------------------
String[] sentMessages = new String[maxMessages];        // Sent Messages array
String[] sentRecipients = new String[maxMessages];      // who each sent message was sent to (parallel with sentMessages)
String[] disregardedMessages = new String[maxMessages]; // Disregarded Messages array

// The Message ID and Message Hash arrays must contain ALL the messages. That includes the
// messages that were stored in the JSON file earlier, so the file is read first and the
// arrays are made big enough for the earlier messages PLUS the new messages of this run.
String[] earlierLines = Message.readStoredLines("storedMessages.json");
String[] messageHashes = new String[earlierLines.length + maxMessages];   // Message Hash array
String[] messageIDs = new String[earlierLines.length + maxMessages];      // Message ID array

// An array does not grow, so these counters remember how many places are already filled
int sentCount = 0;
int disregardedCount = 0;
int idCount = 0;   // how many places of the Message ID and Message Hash arrays are filled

// Put the ID and hash of every message that is already in the file into the two arrays
for (int i = 0; i < earlierLines.length; i++) {
    messageIDs[idCount] = Message.getJsonValue(earlierLines[i], "messageID");
    messageHashes[idCount] = Message.getJsonValue(earlierLines[i], "messageHash");
    idCount++;
}

// The menu keeps showing until the user chooses 3 (Quit)
boolean option = true;

while (option == true) {
    System.out.println("\n1) Send Messages");
    System.out.println("2) Show recently sent messages, Disclaimer: coming soon!");
    System.out.println("3) Quit");
    System.out.println("4)Stored Messages");
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
                        
                        
                        // Part 3: put the message in the Sent Messages array (next free place)
                        sentMessages[sentCount] = Textmessage;
                        sentRecipients[sentCount] = Reciepentcell;   // same position = same message
                        sentCount++;
                        // Part 3: every message also goes in the Message ID and Message Hash arrays
                        messageIDs[idCount] = msg.getMessageID();
                        messageHashes[idCount] = msg.getMessagehash();
                        idCount++;
                   
                        entered++;
                    } else if (result.equals("Message successfully stored")) {
                     
                         // Part 3: a stored message is saved in the JSON file by storeMessage();
                        // here its ID and hash are added to the Message ID and Message Hash arrays
                        messageIDs[idCount] = msg.getMessageID();
                        messageHashes[idCount] = msg.getMessagehash();
                        idCount++;
                        
                        entered++;
                    } else if (result.equals("Press 0 to delete the message.")) {
                        
                          // Part 3: put the message in the Disregarded Messages array
                        disregardedMessages[disregardedCount] = Textmessage;
                        disregardedCount++;
                        messageIDs[idCount] = msg.getMessageID();
                        messageHashes[idCount] = msg.getMessagehash();
                        idCount++;
                       
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
        
         // Option 4 (Part 3): the Stored Messages menu
        case 4 -> storedMessagesMenu(input, sentMessages, sentRecipients, sentCount);
        

        // Anything else is not a valid menu choice
        default -> System.out.println("Invalid option, please choose 1, 2,3 or 4");
    }
}
    }

    // PART 3: THE STORED MESSAGES MENU (main menu option 4)
    
    // When this menu opens, storedMessages.json is READ INTO the Stored Messages arrays.
    // The messages that were read are displayed, then the user can use the arrays to:
    
    
    public void storedMessagesMenu(Scanner input, String[] sentMessages,
            String[] sentRecipients, int sentCount) {
        String fileName = "storedMessages.json";

        // read the JSON file. Each place in storedLines holds one stored message.
        String[] storedLines = Message.readStoredLines(fileName);

        // the number of messages in the file is the size of the Stored Messages arrays
        // (the same idea as new String[golfmember] in that we did in class)
        int storedCount = storedLines.length;
        String[] storedIDs = new String[storedCount];
        String[] storedHashes = new String[storedCount];
        String[] storedRecipients = new String[storedCount];
        String[] storedMessages = new String[storedCount];   // the Stored Messages array

        // its fills the four parallel arrays. Position i is the same message in every array.
        for (int i = 0; i < storedCount; i++) {
            storedIDs[i] = Message.getJsonValue(storedLines[i], "messageID");
            storedHashes[i] = Message.getJsonValue(storedLines[i], "messageHash");
            storedRecipients[i] = Message.getJsonValue(storedLines[i], "recipient");
            storedMessages[i] = Message.getJsonValue(storedLines[i], "message");
        }

        // it display the array that was read from the file
        System.out.println("\n------------------------------");
        System.out.println("Stored messages read from the JSON file: " + storedCount);
        for (int i = 0; i < storedCount; i++) {
            System.out.println((i + 1) + ") " + storedMessages[i]);
        }

       
}

   


   




   



// Refrence list Part one

//YouTube. (2025). MOhiM2SXZl0 [online video]. Available at: https://www.youtube.com/watch?v=MOhiM2SXZl0 [Accessed 15 September 2026]
//Stack Overflow, 2025. Regex pattern for South African cellphone validation. [computer program/code]. Available at: https://stackoverflow.com/... [Accessed 15 September 2026].
// Stack Overflow, 2025. Password complexity validation regex and method. [computer program/code]. Available at: https://stackoverflow.com/... [Accessed 15 September 2026].
// Farrell, J., 2022. Java programming. 10th ed. Boston: Cengage Learning.
//NumberCrunch Programmers. (2024). Java program to validate South African phone numbers using RegeX [online video]. Available at: https://www.youtube.com/watch?v=2M1CpEJZ6rk [Accessed 17 September 2026].

// Refrence list Part two
//Bray, T. ed., 2017. The JavaScript Object Notation (JSON) data interchange format (RFC 8259). [online] Available at: https://www.rfc-editor.org/rfc/rfc8259 [Accessed 18 October 2026].

//JUnit, [s.a.]. JUnit 5 user guide. [online] Available at: https://junit.org/junit5/docs/current/user-guide/ [Accessed 18 October 2026].

//Oracle, [s.a.]. Lesson: regular expressions. The Java tutorials. [online] Available at: https://docs.oracle.com/javase/tutorial/essential/regex/index.html [Accessed 18 October 2026].

//Oracle, 2024a. FileWriter (Java Platform SE 8). [online] Available at: https://docs.oracle.com/javase/8/docs/api/java/io/FileWriter.html [Accessed 18 October 2026].

//Oracle, 2024b. String (Java Platform SE 8). [online] Available at: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html [Accessed 18 October 2026].

// Refrence list Part three
//Oracle, 2024c. Files (Java Platform SE 8). [online] Available at: https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html [Accessed 17 November 2026].
//Farrell, J., 2022. Java programming. 10th ed. Boston: Cengage Learning. (arrays and parallel arrays)


