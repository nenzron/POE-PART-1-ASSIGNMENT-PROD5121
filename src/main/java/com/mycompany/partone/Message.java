/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.partone;
import java.io.File;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author fhumu
 */
public class Message {
    private String messageID;
    private String Reciepentcell;
    private String messagehash;
    private String printMessage;
    private static String allSentMessages = "";
    private int messageNumber;
    private String Textmessage;
    private String sentMessage;
    private String Status;
    
    private static int Totalmessage = 0;
    //private static String printMessage = ""; 
    // private Static String print

public Message(String messageID,String Reciepentcell, String messagehash,
        String printMessage,int Totalmessage,int messageNumber,String Textmessage
         ,String sentMessage, String Status){
        
        this.messageID = messageID; 
        this.Reciepentcell =  Reciepentcell;
        this.messagehash = messagehash;
        this.printMessage = printMessage;
        Message.Totalmessage = Totalmessage;
        this.messageNumber = messageNumber;
        this.Textmessage = Textmessage;
        this.sentMessage = sentMessage;
        this.Status = Status;
        
    }

  // Makes a random 10 digit message ID (first digit is never 0)
public String generateMessageID() {
    Random random = new Random();
    // One random number from 1000000000 to 9999999999, so it always has 10 digits
    long number = random.nextLong(1000000000L, 10000000000L);
    // String.valueOf turns the number into text (Oracle, 2024b)
    messageID = String.valueOf(number);
    return messageID;
}

/*public String generateMessageID() {
    Random random = new Random();
    messageID = String.valueOf(1 + random.nextInt(9));
    for (int i = 1; i < 10; i++) {
        messageID = messageID + random.nextInt(10);
    }
    return messageID;
}*/

// Checks that the message ID is 10 characters long
public boolean checkMessageID() {
    return messageID.length() == 10;
}

// Checks the cell number: +27 followed by exactly 9 digits
public static String checkReciepentCell(String Reciepentcell) {
    String regexPattern = "^(\\+27)[0-9]{9}$";

    if (Reciepentcell.matches(regexPattern)) { //	(Oracle, [s.a.])
        return "Cell phone number successfully captured.";
    } else {
        return "Cell phone number is incorrectly formatted or does not contain an international code. "
                + "Please correct the number and try again.";
    }
}


// Checks the message is not longer than 250 characters
public static String checkmessagelenght(String Textmessage) {
    if (Textmessage.length() <= 250) {
        return "Message is ready to send.";
    } else {
        int over = Textmessage.length() - 250;
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
    }
}

// Adds 1 so the message number on screen starts at 1 instead of 0
public static int incrementmessageNumber(int messageNumber) {
    messageNumber = messageNumber + 1;
    return messageNumber;
}

// Builds the hash: first 2 digits of ID : message number : FIRSTWORDLASTWORD
public String createMessagehash() { //(Oracle, 2024b)
    String firstTwo = messageID.substring(0, 2);
    String trimmed = Textmessage.trim();   // removes spaces at the start and end

    String firstWord;
    String lastWord;
    int firstSpace = trimmed.indexOf(" ");
    if (firstSpace == -1) {
        firstWord = trimmed;   // only one word, so first and last are the same
        lastWord = trimmed;
    } else {
        firstWord = trimmed.substring(0, firstSpace);
        lastWord = trimmed.substring(trimmed.lastIndexOf(" ") + 1);
    }
        firstWord = firstWord.replaceAll("[^A-Za-z0-9]", "");   // remove , ? ! etc.
        lastWord = lastWord.replaceAll("[^A-Za-z0-9]", "");

   

    // Capital letters are applied before saving, so every place that shows the hash is in capitals
    messagehash = (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    return messagehash;
}

// Lets the user send, disregard or store the message
public String sentMessage() {
    Scanner input = new Scanner(System.in);
    System.out.println("Choose an option:");
    System.out.println("1) Send Message");
    System.out.println("2) Disregard Message");
    System.out.println("3) Store Message");
    int option = input.nextInt();
    input.nextLine();

        if (option == 1) {
        Status = "Message sent";
        Totalmessage++;
        allSentMessages = allSentMessages + details() + "\n------------------------------\n";
        return "Message successfully sent.";

    } else if (option == 2) {
        Status = "Message disregard";
        return "Press 0 to delete the message.";

    } else if (option == 3) {
        storeMessage();
        Status = "Messaged Stored";
        return "Message successfully stored";

    } else {
        return "invalid option, please select 1,2 or 3";
    }
   
    }

// Returns all the messages sent while the program is running
public static String printMessages() {
    if (allSentMessages.equals("")) {
        return "No messages have been sent yet.";
    }
    return allSentMessages;

}

// Returns how many messages have been sent
public static int returnTotalMessages() {
    return Totalmessage;
}
// METHOD FOR FIXING FEEDBACK
public void storeMessage() { //(Bray, 2017; Oracle, 2024a)
    System.out.println("storeMessage was called");

    // Escape slashes and quotes so they don't break the JSON
    String safeMessage = Textmessage.replace("\\", "\\\\").replace("\"", "\\\""); //(Bray, 2017; Oracle, 2024b)
    String json = "{\"messageID\":\"" + messageID + "\","
            + "\"messageHash\":\"" + messagehash + "\","
            + "\"recipient\":\"" + Reciepentcell + "\","
            + "\"message\":\"" + safeMessage + "\"}";

    try {  //(Oracle, 2024a)
        // Read what is already in the file (if it exists)
        File file = new File("storedMessages.json");
        System.out.println("Saving to: " + file.getAbsolutePath());

        String content = "";
        if (file.exists()) {
            content = new String(Files.readAllBytes(file.toPath())).trim();
        }

        // Build the full array text
        int end = content.lastIndexOf("]");

        if (content.isEmpty() || content.equals("[]")) {
            content = "[\n" + json + "\n]";   // first message: start the array
        } else if (end == -1) {
            // old format (objects line by line, no brackets): convert it to an array
            content = "[\n" + content.replace("\r", "").replace("\n", ",\n") + ",\n" + json + "\n]";
        } else {
            // cut off the last ], add a comma, the new object, then close with ]
            content = content.substring(0, end).trim() + ",\n" + json + "\n]";
        }

        FileWriter writer = new FileWriter("storedMessages.json"); // no "true": overwrite with the full array
        writer.write(content);
        writer.close();
    } catch (IOException e) {
        System.out.println("Could not store the message: " + e.getMessage());
    }
}
// Saves the message in storedMessages.json (one JSON object per line)
/*public void storeMessage() { //(Bray, 2017; Oracle, 2024a)
    // Escape slashes and quotes so they don't break the JSON
    String safeMessage = Textmessage.replace("\\", "\\\\").replace("\"", "\\\""); //(Bray, 2017; Oracle, 2024b)
    String json = "{\"messageID\":\"" + messageID + "\","
            + "\"messageHash\":\"" + messagehash + "\","
            + "\"recipient\":\"" + Reciepentcell + "\","
            + "\"message\":\"" + safeMessage + "\"}";
    try {  //(Oracle, 2024a)
        FileWriter writer = new FileWriter("storedMessages.json", true); // true = add to the end
        writer.write(json + "\n");
        writer.close();
    } catch (IOException e) {
        System.out.println("Could not store the message.");
    }
}
*/
// Part 3: getters so the arrays in Partone can be filled with this message's ID and hash
public String getMessageID() {
    return messageID;
}

public String getMessagehash() {
    return messagehash;
}

// Details in the required order: Message ID, Message Hash, Recipient, Message
public String details() {
    return "Message ID: " + messageID
            + "\nMessage Hash: " + messagehash
            + "\nRecipient: " + Reciepentcell
            + "\nMessage: " + Textmessage;
}
}


