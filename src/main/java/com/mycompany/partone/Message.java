/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.partone;

import java.util.Random;
/**
 *
 * @author fhumu
 */
public class Message {
    private String messageID;
    private String Reciepentcell;
    private String messagehash;
    private String printMessage;
    private String Totalmessage;
    private String messageNumber;

public Message(String messageID,String Reciepentcell, String messagehash,
        String printMessage,String Totalmessage,String messageNumber){
        
        this.messageID = messageID; 
        this.Reciepentcell =  Reciepentcell;
        this.messagehash = messagehash;
        this.printMessage = printMessage;
        this.Totalmessage = Totalmessage;
        this.messageNumber = messageNumber;
        
    }

  public  String generateMessageID() {
  Random random = new Random();
        String messageID = String.valueOf( 1+ random.nextInt(9)); // first digit 1-9 so it is always 10 digits
        for (int i = 1; i < 10; i++) {
            messageID = messageID + random.nextInt(10);
        }
        return messageID;
    }
 
    // Message ID must not be more than ten characters
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }
public String checkReciepentCell(){
    String regexPattern = "^(\\+27)[0-9]{1,10}$"; //(Stack Overflow, 2025),(NumberCrunch Programmers, 2024)
    
    if (Reciepentcell.matches(regexPattern)){//regex is a  JavaScript regular expression used to validate a standard 10-digit phone number format
       return "Cell phone number is successfully added";
    }else{
       
        return "Cell phone number has the incorrect number of numbers or does not contain international code";
    }
   
}
 public String createMessagehash(){
      String firstTwo = messageID.substring(0, 2);
        String trimmed = messageText.trim(); //removes spaces at the start and end
        String[] words = trimmed.split("\\s+"); // splits the message into arrays
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
 }
public String sentMessage(){
    
}
public String printMessage(){
    
}
public int returnTotalMessage(){
    
}
}



