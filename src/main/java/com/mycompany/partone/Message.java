/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.partone;

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
    private String Totalmessage;
    private int messageNumber;
    private String Textmessage;
    private String sentMessage;
    
    //private Static String Totalmessage = 0;
    //private static String printMessage = ""; 
    // private Static String print

public Message(String messageID,String Reciepentcell, String messagehash,
        String printMessage,String Totalmessage,int messageNumber,String Textmessage
         ,String sentMessage){
        
        this.messageID = messageID; 
        this.Reciepentcell =  Reciepentcell;
        this.messagehash = messagehash;
        this.printMessage = printMessage;
        this.Totalmessage = Totalmessage;
        this.messageNumber = messageNumber;
        this.Textmessage = Textmessage;
        this.sentMessage = sentMessage;
        
    }

  public  String generateMessageID() {
  Random random = new Random();
        messageID = String.valueOf( 1+ random.nextInt(9)); // first digit 1-9 so it is always 10 digits
        for (int i = 1; i < 10; i++) {
            messageID = messageID + random.nextInt(10);
        }
        return messageID;
    }
 
    // Message ID must not be more than ten characters
    public boolean checkMessageID() {
        return messageID.length() == 10;
    }
public String checkReciepentCell(){
    String regexPattern = "^(\\+27)[0-9]{1,10}$"; //(Stack Overflow, 2025),(NumberCrunch Programmers, 2024)
    
    if (Reciepentcell.matches(regexPattern)){//regex is a  JavaScript regular expression used to validate a standard 10-digit phone number format
       return "Cell phone number is successfully added";
    }else{
       
        return "Cell phone number has the incorrect number of numbers or does not contain international code";
    }
}
   public String checkmessagelenght(){
   if (Textmessage.length() <=250) {
     return  "Message is accepted.";
   }else{
       int over = Textmessage.length()-250;
       return "Message is" +over+ "Character too long";
       
                 
   }
   }
 public String createMessagehash(){
      String firstTwo = messageID.substring(0, 2);
        String trimmed = Textmessage.trim(); //removes spaces at the start and end
        
       
        
        
        String firstWord ;
        int firstSpace = trimmed.indexOf("");
        if(firstSpace == -1){
            firstWord = trimmed;
        }else{
            firstWord = trimmed.substring(0,firstSpace);
        }
       
          
        int lastSpace = trimmed.indexOf("");
        String lastWord = trimmed.substring(+1);
         
        
        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        
        
 }
// System.out.println("1) Send  2) Disregard  3) Store");
             //   int action = Integer.parseInt(input.nextLine());
               // System.out.println(msg.sentMessage());
//
              //  if (action == 1 || action == 3) {
                 //   System.out.println(msg.printMessage());
                  //  entered++;
            //    }
 
public String sentMessage(){
        Scanner input = new Scanner(System.in);
        System.out.println("Choose an option:");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message");
        int option = input.nextInt();
        input.nextLine();

        if(option==1){
         
         return "Message successfully sent";
        }else if(option==2){
            
        return "Press 0 to delete the message";
        }else if(option==3){
            
         return "Message successfully stored";
        }else{
            return "invalid option, please select 1,2 or 3";
        }

}
public String printMessage(){
    
}
public int returnTotalMessage(){
    
}
public String Storedmessage(){
    
}
}



