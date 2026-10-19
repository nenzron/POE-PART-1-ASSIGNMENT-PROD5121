# QuickChat - Part 2

QuickChat is a console messaging application written in Java. After a user registers and logs in (Part 1), they can enter a set number of messages, then send, disregard or store each one. Stored messages are saved to a JSON file.

- **Name:** [Ronewa Nenzhelele]
- **Student number:** [ST10510734]
- **Module:** [PROG5121]
- **Project:** Partone (Maven, NetBeans), package `com.mycompany.partone`

## Features

- Users can only reach QuickChat after logging in successfully (Part 1).
- Displays the welcome message "Welcome to QuickChat." and a numeric menu:
  1. Send Messages
  2. Show recently sent messages ("Coming Soon.")
  3. Quit
- The menu keeps running until the user chooses 3.
- The user decides how many messages to enter when the application starts, and a `for` loop allows only that number.
- Each message has:
  - a randomly generated 10-digit **message ID**
  - a **message number** taken from the loop counter
  - a **recipient** cell number (`+27` followed by 9 digits)
  - a **message** of 250 characters or less
  - a **message hash** (first two digits of the ID, a colon, the message number, a colon, then the first and last words in capitals, for example `00:0:HITONIGHT`)
- After each message is completed, the user chooses to:
  1. **Send** it ("Message successfully sent.")
  2. **Disregard** it ("Press 0 to delete the message.")
  3. **Store** it to send later ("Message successfully stored.")
- The full details of each sent message are displayed in this order: Message ID, Message Hash, Recipient, Message.
- Once all messages are entered, all sent messages and the total number sent are displayed.

## How to run

**Requirements:** Java 17 (or the version set in `pom.xml`), Maven, and NetBeans (optional).

**In NetBeans**
1. Open the `Partone` project.
2. Right-click the project and choose **Clean and Build**.
3. Right-click `Partone.java` and choose **Run File**.
4. Type your answers in the **Output** window.

**From the command line**
```
mvn clean compile
mvn exec:java -Dexec.mainClass="com.mycompany.partone.Partone"
```

## The Message class

| Method | What it does |
|---|---|
| `generateMessageID()` | Creates a random 10-digit message ID. |
| `checkMessageID()` | Returns `true` if the message ID is the correct length. |
| `checkReciepentCell(String)` | Checks the recipient number is `+27` followed by 9 digits. Returns a success or failure message. |
| `checkmessagelenght(String)` | Checks the message is 250 characters or less. Returns "Message is ready to send." or how many characters it is over. |
| `incrementmessageNumber(int)` | Adds 1 to the loop counter so message numbers start at 1 on screen. |
| `createMessagehash()` | Builds the message hash using string manipulation (`substring`, `indexOf`, `lastIndexOf`, `replaceAll`). |
| `sentMessage()` | Lets the user choose to send, disregard or store the message. |
| `printMessages()` | Returns all the messages sent while the program is running. |
| `returnTotalMessages()` | Returns the total number of messages sent. |
| `storeMessage()` | Saves the message to a JSON file. |
| `details()` | Returns the message details in the order ID, Hash, Recipient, Message. |

## Storing messages in JSON

`storeMessage()` writes one JSON object per line to `storedMessages.json`, which is created in the project's main folder the first time a message is stored. New messages are added to the end of the file, so earlier ones are kept.

```
{"messageID":"4249123326","messageHash":"42:1:HITONIGHT","recipient":"+27718693002","message":"Hi Mike, can you join us for dinner tonight?"}
```

How it works:
1. Quotes and backslashes in the message are escaped so they cannot break the JSON (Bray, 2017; Oracle, 2024b).
2. The four message details are joined into one JSON object (Bray, 2017).
3. `FileWriter` opens `storedMessages.json` in append mode, writes the line and closes the file (Oracle, 2024a).
4. A `try` and `catch` block stops the program crashing if the file cannot be written.

No external library and no arrays are used.

## Unit tests

The tests are in `MessageTest.java` and use JUnit 5 (JUnit, [s.a.]). They follow the test data in the assignment brief.

| Test | What it checks |
|---|---|
| `testTask1` | Task 1 data: valid recipient, message length, ID, hash, Send, and total sent. |
| `testMessage2` | Message 2 data: invalid recipient, ID, hash, Discard, and total unchanged. |
| `testMessageLengthSuccess` | A message of 250 characters or less is accepted. |
| `testMessageLengthFailure` | A message over 250 characters returns how many characters it exceeds. |
| `testRecipientSuccess` | `+27718693002` is accepted. |
| `testRecipientFailure` | `08575975889` is rejected (no international code). |
| `testMessageHashTestCase1` | The hash for Task 1 is `00:0:HITONIGHT`. |
| `testMessageHashLoop` | The remaining hashes are checked in a loop. |
| `testMessageIDCreated` | The message ID is created and is 10 digits. |
| `testMessageSentSend` | Choosing Send returns "Message successfully sent." |
| `testMessageSentDisregard` | Choosing Disregard returns "Press 0 to delete the message." |
| `testMessageSentStore` | Choosing Store returns "Message successfully stored." |

**Run the tests**
- In NetBeans: right-click `MessageTest.java` and choose **Test File** (`Ctrl+F6`).
- From the command line: `mvn test`

The tests also print the auto-generated values (ID, hash and totals) to the Output window.

## Automated tests (GitHub Actions)

`.github/workflows/TestJava.yml` runs the unit tests with Maven on every push and pull request. The results are on the **Actions** tab of the GitHub repository.

## Notes

- Option 2 on the main menu is still in development and shows "Coming Soon."
- Disregarded and stored messages count as entered, but only sent messages count towards the total.
- `storedMessages.json` is created automatically. The store test adds a line to it each time the tests run.

## References

Bray, T. ed., 2017. The JavaScript Object Notation (JSON) data interchange format (RFC 8259). [online] Available at: <https://www.rfc-editor.org/rfc/rfc8259> [Accessed 18 October 2026].

JUnit, [s.a.]. JUnit 5 user guide. [online] Available at: <https://junit.org/junit5/docs/current/user-guide/> [Accessed 18 October 2026].

Oracle, [s.a.]. Lesson: regular expressions. The Java tutorials. [online] Available at: <https://docs.oracle.com/javase/tutorial/essential/regex/index.html> [Accessed 18 October 2026].

Oracle, 2024a. FileWriter (Java Platform SE 8). [online] Available at: <https://docs.oracle.com/javase/8/docs/api/java/io/FileWriter.html> [Accessed 18 October 2026].

Oracle, 2024b. String (Java Platform SE 8). [online] Available at: <https://docs.oracle.com/javase/8/docs/api/java/lang/String.html> [Accessed 18 October 2026].
