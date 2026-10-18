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



public class MessageTest {

    // Test data from the brief
    // Task 1: +27718693002, "Hi Mike, can you join us for dinner tonight?", Send
    // Message 2: 08575975889, "Hi Keegan, did you receive the payment?", Discard
    private final String message1 = "Hi Mike, can you join us for dinner tonight?";
    private final String message2 = "Hi Keegan, did you receive the payment?";

    private final InputStream originalIn = System.in;

    // Put the keyboard back to normal after each test
    @AfterEach
    public void restoreInput() {
        System.setIn(originalIn);
    }

    // Pretends the user typed something at the keyboard
    private void typeInput(String text) {
        System.setIn(new ByteArrayInputStream((text + "\n").getBytes()));
    }

    // ---------- Recipient number is correctly formatted ----------

    @Test
    public void testRecipientSuccess() {
        assertEquals("Cell phone number successfully captured.",
                Message.checkReciepentCell("+27718693002"));
    }

    @Test
    public void testRecipientFailure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                Message.checkReciepentCell("08575975889"));
    }

    // ---------- Message hash is correct ----------

    @Test
    public void testMessageHashTestCase1() {
        Message msg = new Message("0012345678", "+27718693002", "", "",
                0, 0, message1, "", "");
        assertEquals("00:0:HITONIGHT", msg.createMessagehash());
    }

    // The remaining hashes are tested in a loop
    @Test
    public void testMessageHashLoop() {
        for (int number = 0; number < 2; number++) {

            String text;
            String id;
            String expected;

            if (number == 0) {
                text = message1;
                id = "0012345678";
                expected = "00:0:HITONIGHT";
            } else {
                text = message2;
                id = "0112345678";
                expected = "01:1:HIPAYMENT";
            }

            Message msg = new Message(id, "+27718693002", "", "",
                    0, number, text, "", "");
            assertEquals(expected, msg.createMessagehash());
        }
    }

    // ---------- Message should not be more than 250 characters ----------

    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message is ready to send.",
                Message.checkmessagelenght(message1));
    }

    @Test
    public void testMessageLengthFailure() {
        String longMessage = "a".repeat(260);   // 10 over the limit
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                Message.checkmessagelenght(longMessage));
    }

    // ---------- Message ID is created ----------

    @Test
    public void testMessageIDCreated() {
        Message msg = new Message("", "+27718693002", "", "",
                0, 0, message1, "", "");
        String id = msg.generateMessageID();

        // "Message ID generated: <Message ID>"
        String output = "Message ID generated: " + id;

        assertTrue(msg.checkMessageID());
        assertEquals(10, id.length());
        assertEquals("Message ID generated: " + id, output);
    }

    // ---------- MessageSent ----------

    // 1) User selected 'Send Message'
    @Test
    public void testSentMessageSend() {
        Message msg = new Message("0012345678", "+27718693002", "00:0:HITONIGHT", "",
                0, 0, message1, "", "");
        int totalBefore = Message.returnTotalMessages();

        typeInput("1");
        assertEquals("Message successfully sent.", msg.sentMessage());

        // Return total number sent
        assertEquals(totalBefore + 1, Message.returnTotalMessages());
    }

    // 2) User selected 'Disregard Message'
    @Test
    public void testSentMessageDisregard() {
        Message msg = new Message("0112345678", "08575975889", "01:1:HIPAYMENT", "",
                0, 1, message2, "", "");
        int totalBefore = Message.returnTotalMessages();

        typeInput("2");
        assertEquals("Press 0 to delete the message.", msg.sentMessage());

        // A disregarded message is not counted as sent
        assertEquals(totalBefore, Message.returnTotalMessages());
    }

    // 3) User selected 'Store Message'
    @Test
    public void testSentMessageStore() {
        Message msg = new Message("0012345678", "+27718693002", "00:0:HITONIGHT", "",
                0, 0, message1, "", "");

        typeInput("3");
        assertEquals("Message successfully stored.", msg.sentMessage());
    }
}
    

