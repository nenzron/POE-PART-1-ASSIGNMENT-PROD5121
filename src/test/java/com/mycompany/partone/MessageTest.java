/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.partone;
 

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author fhumu
 */



public class MessageTest { //(JUnit, [s.a.])

    // Put the keyboard back to normal after each test
    private final InputStream originalIn = System.in;

    @AfterEach
    public void restoreInput() {
        System.setIn(originalIn);
    }

    // ---------- Test Data: Task 1 ----------
    // Num Messages: 2
    // Recipient Number: +27718693002
    // Message: "Hi Mike, can you join us for dinner tonight?"
    // SendMessage: Select Send
    // Num sent messages, Message Hash, MessageID, Return total: auto generated

    @Test
    public void testTask1() {
        int numMessages = 2;
        String recipient = "+27718693002";
        String text = "Hi Mike, can you join us for dinner tonight?";

        // Message number starts at 0, so the first message is number 0
        Message msg = new Message("", recipient, "", "", 0, 0, text, "", "");

        // Recipient number is correct
        assertEquals("Cell phone number successfully captured.",
                Message.checkReciepentCell(recipient));

        // Message is not more than 250 characters
        assertEquals("Message is ready to send.", Message.checkmessagelenght(text));

        // MessageID is auto generated (10 digits)
        String id = msg.generateMessageID();
        assertTrue(msg.checkMessageID());
        assertEquals(10, id.length());

        // Message hash is auto generated: first 2 digits of ID : message number : FIRSTLAST
        String expectedHash = id.substring(0, 2) + ":0:HITONIGHT";
        assertEquals(expectedHash, msg.createMessagehash());

        // User selects Send (types 1)
        int totalBefore = Message.returnTotalMessages();
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        assertEquals("Message successfully sent.", msg.sentMessage());

        // Return total number sent goes up by 1
        assertEquals(totalBefore + 1, Message.returnTotalMessages());

        // The number of messages the user said they would send
        assertEquals(2, numMessages);
    }
     // ---------- Test Data for Message 2 ----------
    // Recipient Number: 08575975889 (no international code)
    // Message: "Hi Keegan, did you receive the payment?"
    // SendMessage: Select Discard
    // Num sent messages, Message Hash, MessageID, Return total: auto generated

    @Test
    public void testMessage2() {
        String recipient = "08575975889";
        String text = "Hi Keegan, did you receive the payment?";

        // Message 2 is the second message, so its number is 1
        Message msg = new Message("", recipient, "", "", 0, 1, text, "", "");

        // Recipient number has no international code, so it must fail
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                Message.checkReciepentCell(recipient));

        // Message is not more than 250 characters
        assertEquals("Message is ready to send.", Message.checkmessagelenght(text));

        // MessageID is auto generated (10 digits)
        String id = msg.generateMessageID();
        assertTrue(msg.checkMessageID());
        assertEquals(10, id.length());

        // Message hash is auto generated: first 2 digits of ID : message number : FIRSTLAST
        String expectedHash = id.substring(0, 2) + ":1:HIPAYMENT";
        assertEquals(expectedHash, msg.createMessagehash());

        // User selects Discard (types 2)
        int totalBefore = Message.returnTotalMessages();
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        assertEquals("Press 0 to delete the message.", msg.sentMessage());

        // Return total number sent does not change for a discarded message
        assertEquals(totalBefore, Message.returnTotalMessages());
    }
        // ---------- Test AssertEquals: message should not be more than 250 characters ----------

    // Success: message is 250 characters or less
    @Test
    public void testMessageLengthSuccess() {
        String text = "Hi Mike, can you join us for dinner tonight?";
        assertEquals("Message is ready to send.", Message.checkmessagelenght(text));
    }

    // Failure: message is more than 250 characters
    @Test
    public void testMessageLengthFailure() {
        String longMessage = "a".repeat(260);   // 10 characters over the limit
        int over = longMessage.length() - 250;  // X = 10

        assertEquals("Message exceeds 250 characters by " + over + "; please reduce the size.",
                Message.checkmessagelenght(longMessage));
    }
    // ---------- The recipient number is correctly formatted ----------

    // Success: +27 followed by 9 digits (Task 1 number)
    @Test
    public void testRecipientSuccess() {
        assertEquals("Cell phone number successfully captured.",
                Message.checkReciepentCell("+27718693002"));
    }

    // Failure: no international code (Message 2 number)
    @Test
    public void testRecipientFailure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                Message.checkReciepentCell("08575975889"));
    }
        // ---------- Message hash is correct ----------

    // Test Case 1: the hash must be 00:0:HITONIGHT
    @Test
    public void testMessageHashTestCase1() {
        // The ID is set to start with 00 so the expected hash is always the same
        Message msg = new Message("0012345678", "+27718693002", "", "",
                0, 0, "Hi Mike, can you join us for dinner tonight?", "", "");

        assertEquals("00:0:HITONIGHT", msg.createMessagehash());
    }

    // The remainder of the message hashes, tested in a loop
    @Test
    public void testMessageHashLoop() {
        for (int number = 0; number < 2; number++) {

            String id;
            String text;
            String expected;

            if (number == 0) {
                id = "0012345678";
                text = "Hi Mike, can you join us for dinner tonight?";
                expected = "00:0:HITONIGHT";
            } else {
                id = "0112345678";
                text = "Hi Keegan, did you receive the payment?";
                expected = "01:1:HIPAYMENT";
            }

            Message msg = new Message(id, "+27718693002", "", "",
                    0, number, text, "", "");

            assertEquals(expected, msg.createMessagehash());
        }
    }
        // ---------- Message ID is created ----------

    @Test
    public void testMessageIDCreated() {
        Message msg = new Message("", "+27718693002", "", "",
                0, 0, "Hi Mike, can you join us for dinner tonight?", "", "");

        // Create the ID, then build the text the system should show
        String id = msg.generateMessageID();
        String output = "Message ID generated: " + id;

        // The ID is 10 digits and passes the ID check
        assertTrue(msg.checkMessageID());
        assertEquals(10, id.length());
        assertTrue(id.matches("[0-9]{10}"));

        // The text is "Message ID generated: <Message ID>"
        assertTrue(output.startsWith("Message ID generated: "));
        assertEquals(32, output.length());   // 22 characters of text + 10 digits
    }
        // ---------- MessageSent ----------

    // 1) User selected 'Send Message'
    @Test
    public void testMessageSentSend() {
        Message msg = new Message("0012345678", "+27718693002", "00:0:HITONIGHT", "",
                0, 0, "Hi Mike, can you join us for dinner tonight?", "", "");

        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        assertEquals("Message successfully sent.", msg.sentMessage());
    }

    // 2) User selected 'Disregard Message'
    @Test
    public void testMessageSentDisregard() {
        Message msg = new Message("0112345678", "08575975889", "01:1:HIPAYMENT", "",
                0, 1, "Hi Keegan, did you receive the payment?", "", "");

        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        assertEquals("Press 0 to delete the message.", msg.sentMessage());
    }

    // 3) User selected 'Store Message'
    @Test
    public void testMessageSentStore() {
        Message msg = new Message("0012345678", "+27718693002", "00:0:HITONIGHT", "",
                0, 0, "Hi Mike, can you join us for dinner tonight?", "", "");

        System.setIn(new ByteArrayInputStream("3\n".getBytes()));
        assertEquals("Message successfully stored.", msg.sentMessage());
    }
    
}
    

