/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.partone;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests written with JUnit 5 (JUnit, [s.a.])
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessageTest {

    // Put the keyboard back to normal after each test
    private final InputStream originalIn = System.in;

    @AfterEach
    public void restoreInput() {
        System.setIn(originalIn);
    }

    // Prints a heading so each test is easy to find in the Output window
    private void heading(String title) {
        System.out.println("\n===== " + title + " =====");
    }

    // ---------- Test Data: Task 1 ----------
    @Test
    @Order(1)
    public void testTask1() {
        heading("TEST DATA TASK 1 (Send)");
        String recipient = "+27718693002";
        String text = "Hi Mike, can you join us for dinner tonight?";

        // Message number starts at 0, so the first message is number 0
        Message msg = new Message("", recipient, "", "", 0, 0, text, "", "");

        assertEquals("Cell phone number successfully captured.",
                Message.checkReciepentCell(recipient));
        System.out.println("Recipient check: PASSED (" + recipient + ")");

        assertEquals("Message is ready to send.", Message.checkmessagelenght(text));
        System.out.println("Message length check: PASSED");

        // MessageID is auto generated (10 digits)
        String id = msg.generateMessageID();
        assertTrue(msg.checkMessageID());
        assertEquals(10, id.length());
        System.out.println("Message ID: " + id);

        // Message hash is auto generated
        String hash = msg.createMessagehash();
        assertEquals(id.substring(0, 2) + ":0:HITONIGHT", hash);
        System.out.println("Message Hash: " + hash);

        // User selects Send (types 1)
        int totalBefore = Message.returnTotalMessages();
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        String result = msg.sentMessage();
        assertEquals("Message successfully sent.", result);
        System.out.println("SendMessage result: " + result);

        // Return total number sent goes up by 1
        assertEquals(totalBefore + 1, Message.returnTotalMessages());
        System.out.println("Return total number sent: " + Message.returnTotalMessages());

        // All the details in the required order
        System.out.println(msg.details());
    }

    // ---------- Test Data for Message 2 ----------
    @Test
    @Order(2)
    public void testMessage2() {
        heading("TEST DATA MESSAGE 2 (Discard)");
        String recipient = "08575975889";
        String text = "Hi Keegan, did you receive the payment?";

        // Message 2 is the second message, so its number is 1
        Message msg = new Message("", recipient, "", "", 0, 1, text, "", "");

        // Recipient number has no international code, so it must fail
        String cellResult = Message.checkReciepentCell(recipient);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                cellResult);
        System.out.println("Recipient check: " + cellResult);

        assertEquals("Message is ready to send.", Message.checkmessagelenght(text));
        System.out.println("Message length check: PASSED");

        // MessageID is auto generated (10 digits)
        String id = msg.generateMessageID();
        assertTrue(msg.checkMessageID());
        assertEquals(10, id.length());
        System.out.println("Message ID: " + id);

        // Message hash is auto generated
        String hash = msg.createMessagehash();
        assertEquals(id.substring(0, 2) + ":1:HIPAYMENT", hash);
        System.out.println("Message Hash: " + hash);

        // User selects Discard (types 2)
        int totalBefore = Message.returnTotalMessages();
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        String result = msg.sentMessage();
        assertEquals("Press 0 to delete the message.", result);
        System.out.println("SendMessage result: " + result);

        // The total does not change for a discarded message
        assertEquals(totalBefore, Message.returnTotalMessages());
        System.out.println("Return total number sent: " + Message.returnTotalMessages());

        System.out.println(msg.details());
    }

    // ---------- Message should not be more than 250 characters ----------
    @Test
    @Order(3)
    public void testMessageLengthSuccess() {
        heading("MESSAGE LENGTH: SUCCESS");
        String text = "Hi Mike, can you join us for dinner tonight?";
        String result = Message.checkmessagelenght(text);
        assertEquals("Message is ready to send.", result);
        System.out.println("Message length " + text.length() + " -> " + result);
    }

    @Test
    @Order(4)
    public void testMessageLengthFailure() {
        heading("MESSAGE LENGTH: FAILURE");
        String longMessage = "a".repeat(260);   // 10 characters over the limit
        int over = longMessage.length() - 250;  // X = 10

        String result = Message.checkmessagelenght(longMessage);
        assertEquals("Message exceeds 250 characters by " + over + "; please reduce the size.", result);
        System.out.println("Message length " + longMessage.length() + " -> " + result);
    }

    // ---------- The recipient number is correctly formatted ----------
    @Test
    @Order(5)
    public void testRecipientSuccess() {
        heading("RECIPIENT: SUCCESS");
        String result = Message.checkReciepentCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
        System.out.println("+27718693002 -> " + result);
    }

    @Test
    @Order(6)
    public void testRecipientFailure() {
        heading("RECIPIENT: FAILURE");
        String result = Message.checkReciepentCell("08575975889");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                result);
        System.out.println("08575975889 -> " + result);
    }

    // ---------- Message hash is correct ----------
    @Test
    @Order(7)
    public void testMessageHashTestCase1() {
        heading("MESSAGE HASH: TEST CASE 1");
        // The ID is set to start with 00 so the expected hash is always the same
        Message msg = new Message("0012345678", "+27718693002", "", "",
                0, 0, "Hi Mike, can you join us for dinner tonight?", "", "");

        String hash = msg.createMessagehash();
        assertEquals("00:0:HITONIGHT", hash);
        System.out.println("Message Hash: " + hash);
    }

    @Test
    @Order(8)
    public void testMessageHashLoop() {
        heading("MESSAGE HASH: LOOP");
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

            String hash = msg.createMessagehash();
            assertEquals(expected, hash);
            System.out.println("Message " + number + " hash: " + hash);
        }
    }

    // ---------- Message ID is created ----------
    @Test
    @Order(9)
    public void testMessageIDCreated() {
        heading("MESSAGE ID IS CREATED");
        Message msg = new Message("", "+27718693002", "", "",
                0, 0, "Hi Mike, can you join us for dinner tonight?", "", "");

        String id = msg.generateMessageID();
        String output = "Message ID generated: " + id;

        assertTrue(msg.checkMessageID());
        assertEquals(10, id.length());
        assertTrue(id.matches("[0-9]{10}"));
        assertTrue(output.startsWith("Message ID generated: "));
        assertEquals(32, output.length());   // 22 characters of text + 10 digits
        System.out.println(output);
    }

    // ---------- MessageSent ----------
    @Test
    @Order(10)
    public void testMessageSentSend() {
        heading("MESSAGE SENT: 1) SEND");
        Message msg = new Message("0012345678", "+27718693002", "00:0:HITONIGHT", "",
                0, 0, "Hi Mike, can you join us for dinner tonight?", "", "");

        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        String result = msg.sentMessage();
        assertEquals("Message successfully sent.", result);
        System.out.println("Result: " + result);
    }

    @Test
    @Order(11)
    public void testMessageSentDisregard() {
        heading("MESSAGE SENT: 2) DISREGARD");
        Message msg = new Message("0112345678", "08575975889", "01:1:HIPAYMENT", "",
                0, 1, "Hi Keegan, did you receive the payment?", "", "");

        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        String result = msg.sentMessage();
        assertEquals("Press 0 to delete the message.", result);
        System.out.println("Result: " + result);
    }

    @Test
    @Order(12)
    public void testMessageSentStore() {
        heading("MESSAGE SENT: 3) STORE");
        Message msg = new Message("0012345678", "+27718693002", "00:0:HITONIGHT", "",
                0, 0, "Hi Mike, can you join us for dinner tonight?", "", "");

        System.setIn(new ByteArrayInputStream("3\n".getBytes()));
        String result = msg.sentMessage();
        assertEquals("Message successfully stored.", result);
        System.out.println("Result: " + result);
    }
}
    

    

