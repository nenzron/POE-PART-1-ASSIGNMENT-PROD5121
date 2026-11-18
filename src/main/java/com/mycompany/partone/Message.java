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
// =====================================================================
// PART 3: METHODS THAT WORK WITH THE ARRAYS
// =====================================================================
// All the arrays are normal Java arrays. They are made in Partone with the
// size the user typed in (for example new String[maxMessages]), the same way the golf
// members array is made in class (Farrell, 2022, p. 267).
// The Stored Messages arrays are PARALLEL arrays: position 0 of every array belongs to the
// same message, position 1 to the next message, and so on (Farrell, 2022, p. 284).
// The arrays are passed to the methods below as parameters (Farrell, 2022, p. 289).

// Getters, so Partone can put the ID and the hash of a message into the arrays
public String getMessageID() {
    return messageID;
}

public String getMessagehash() {
    return messagehash;
}

// Reads storedMessages.json (a JSON array with one message object per line) and returns an
// array with ONE LINE FOR EACH STORED MESSAGE. The size of the returned array is the number
// of messages found in the file, so nothing is hard-coded. (Bray, 2017; Oracle, 2024c)
public static String[] readStoredLines(String fileName) { //(Bray, 2017; Oracle, 2024c)
    String content = "";
    try {
        File file = new File(fileName);
        if (file.exists()) {
            // Read the whole file into one String (the same line is used in storeMessage)
            content = new String(Files.readAllBytes(file.toPath()));
        }
    } catch (IOException e) {
        System.out.println("Could not read the stored messages file: " + e.getMessage());
    }

    // split("\n") cuts the text into lines (Farrell, 2022, p. 459; Oracle, 2024b)
    String[] lines = content.split("\n");

    // First loop: count the lines that hold a message object (they contain a "{")
    int count = 0;
    for (int i = 0; i < lines.length; i++) {
        if (lines[i].contains("{")) {
            count++;
        }
    }

    // The array is made exactly as big as the number of messages in the file
    String[] storedLines = new String[count];

    // Second loop: copy the message lines into the new array
    int position = 0;
    for (int i = 0; i < lines.length; i++) {
        if (lines[i].contains("{")) {
            storedLines[position] = lines[i].trim();
            position++;
        }
    }
    return storedLines;
}

// Gets one value out of one line of the JSON file, e.g. getJsonValue(line, "recipient")
// returns +27718693002. The text before and after the value is cut away with indexOf and
// substring (Farrell, 2022, pp. 246-248; Oracle, 2024b).
public static String getJsonValue(String line, String key) { //(Bray, 2017; Oracle, 2024b)
    // Each value starts right after "key":" and the next key (or the end of the object)
    // tells us where it stops. The message is the last key, so it ends at the last "}
    String start = "\"" + key + "\":\"";
    int from = line.indexOf(start);
    if (from == -1) {
        return "";
    }
    from = from + start.length();

    int to;
    if (key.equals("messageID")) {
        to = line.indexOf("\",\"messageHash\"");
    } else if (key.equals("messageHash")) {
        to = line.indexOf("\",\"recipient\"");
    } else if (key.equals("recipient")) {
        to = line.indexOf("\",\"message\"");
    } else {
        to = line.lastIndexOf("\"}");
    }
    if (to == -1 || to < from) {
        return "";
    }

    // Remove the escape characters that storeMessage added (\" becomes " and \\ becomes \)
    return line.substring(from, to).replace("\\\"", "\"").replace("\\\\", "\\");
}

// Saves the stored arrays back to the file as ONE JSON array. This is used after a message
// was deleted, so the message is also gone from storedMessages.json (Bray, 2017; Oracle, 2024a).
public static void saveStoredMessages(String fileName, String[] ids, String[] hashes,
        String[] recipients, String[] messages) { //(Bray, 2017; Oracle, 2024a)
    String content = "[\n";
    for (int i = 0; i < messages.length; i++) {
        // Escape slashes and quotes so they don't break the JSON (same as storeMessage)
        String safeMessage = messages[i].replace("\\", "\\\\").replace("\"", "\\\"");
        content = content + "{\"messageID\":\"" + ids[i] + "\","
                + "\"messageHash\":\"" + hashes[i] + "\","
                + "\"recipient\":\"" + recipients[i] + "\","
                + "\"message\":\"" + safeMessage + "\"}";
        if (i < messages.length - 1) {
            content = content + ",";   // a comma after every object except the last one
        }
        content = content + "\n";
    }
    content = content + "]";

    try {
        FileWriter writer = new FileWriter(fileName);   // no "true": the file is overwritten
        writer.write(content);
        writer.close();
    } catch (IOException e) {
        System.out.println("Could not save the stored messages: " + e.getMessage());
    }
}

// Searches an array for a value and returns the position where it was found, or -1 if it is
// not in the array. This is the "searching an array" for loop from the textbook, and the loop
// stops as soon as the value is found (Farrell, 2022, pp. 284-286).
public static int findPosition(String[] array, String value) {
    int position = -1;                       // -1 means "not found yet"
    for (int x = 0; x < array.length && position == -1; x++) {
        if (array[x].equalsIgnoreCase(value.trim())) {
            position = x;
        }
    }
    return position;
}

// Returns a NEW array that is one element smaller, without the element at the given position.
// An array cannot change size, so the values before and after the removed one are copied into
// a new, smaller array (Farrell, 2022, pp. 289-291, returning an array from a method).
public static String[] removeAt(String[] array, int removePosition) {
    String[] smaller = new String[array.length - 1];
    int newPosition = 0;
    for (int x = 0; x < array.length; x++) {
        if (x != removePosition) {          // copy everything except the removed element
            smaller[newPosition] = array[x];
            newPosition++;
        }
    }
    return smaller;
}

// 2a. Display the sender and recipient of all stored messages
public static String displaySenderAndRecipient(String sender, String[] recipients) {
    if (recipients.length == 0) {
        return "There are no stored messages.";
    }
    String text = "";
    for (int i = 0; i < recipients.length; i++) {
        text = text + (i + 1) + ") Sender: " + sender + " | Recipient: " + recipients[i] + "\n";
    }
    return text;
}

// 2b. Display the longest stored message. The loop remembers the position of the longest
// message so far (like finding the highest value in an array). The SAME position is then used
// in the parallel arrays to show the ID, hash and recipient of that message.
public static String displayLongestMessage(String[] ids, String[] hashes,
        String[] recipients, String[] messages) {
    if (messages.length == 0) {
        return "There are no stored messages.";
    }
    int longest = 0;
    for (int i = 1; i < messages.length; i++) {
        if (messages[i].length() > messages[longest].length()) {
            longest = i;
        }
    }
    return "Longest stored message (" + messages[longest].length() + " characters):\n"
            + "Message ID: " + ids[longest]
            + "\nMessage Hash: " + hashes[longest]
            + "\nRecipient: " + recipients[longest]
            + "\nMessage: " + messages[longest];
}

// 2c. Search for a message ID and display the corresponding recipient and message.
// The message ID array is searched, and the position found is used in the other arrays.
public static String searchByMessageID(String[] ids, String[] recipients,
        String[] messages, String idToFind) {
    int position = findPosition(ids, idToFind);
    if (position == -1) {
        return "No message with the ID " + idToFind.trim() + " was found.";
    }
    return "Recipient: " + recipients[position] + "\nMessage: " + messages[position];
}

// 2d. Search for all the messages for a particular recipient. Two sets of parallel arrays
// are searched: the Stored Messages arrays (read from the JSON file) and the Sent Messages
// arrays (sentCount tells us how many places of the sent arrays are filled). A recipient can
// have many messages, so these loops do NOT stop early: every recipient in the array is checked.
public static String searchByRecipient(String[] storedRecipients, String[] storedMessages,
        String[] sentRecipients, String[] sentMessages, int sentCount, String recipientToFind) {
    String text = "";
    int found = 0;

    // Search the stored messages
    for (int i = 0; i < storedRecipients.length; i++) {
        if (storedRecipients[i].equals(recipientToFind.trim())) {
            found++;
            text = text + found + ") (stored) " + storedMessages[i] + "\n";
        }
    }

    // Search the sent messages
    for (int i = 0; i < sentCount; i++) {
        if (sentRecipients[i].equals(recipientToFind.trim())) {
            found++;
            text = text + found + ") (sent) " + sentMessages[i] + "\n";
        }
    }

    if (found == 0) {
        return "No messages were found for " + recipientToFind.trim() + ".";
    }
    return "Messages for " + recipientToFind.trim() + ":\n" + text;
}

// 2f. Display a report that lists the full details of all the stored messages
public static String displayReport(String sender, String[] ids, String[] hashes,
        String[] recipients, String[] messages) {
    if (messages.length == 0) {
        return "There are no stored messages to report.";
    }
    String text = "===== STORED MESSAGES REPORT =====\n";
    for (int i = 0; i < messages.length; i++) {
        text = text + "\nMessage " + (i + 1) + " of " + messages.length
                + "\nSender: " + sender
                + "\nMessage ID: " + ids[i]
                + "\nMessage Hash: " + hashes[i]
                + "\nRecipient: " + recipients[i]
                + "\nMessage: " + messages[i]
                + "\n------------------------------\n";
    }
    return text + "Total stored messages: " + messages.length;
}


// Details in the required order: Message ID, Message Hash, Recipient, Message
public String details() {
    return "Message ID: " + messageID
            + "\nMessage Hash: " + messagehash
            + "\nRecipient: " + Reciepentcell
            + "\nMessage: " + Textmessage;
}
}


