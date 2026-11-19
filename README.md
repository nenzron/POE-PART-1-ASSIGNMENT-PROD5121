# QuickChat: Part 3 (Store Data and Display)

Student name:Ronewa Nenzhelele
Student number: ST10510734
Module: PROG5121 Programming 1A

Part 3 adds the final features to the QuickChat app. It builds on Part 1 (registration and login) and Part 2 (sending, disregarding and storing messages). The app now keeps the messages in **arrays** (no `ArrayList`), reads the stored messages from a **JSON file** into an array, and has a fourth main menu option, **Stored Messages**.

---

## 1. How to run

**In NetBeans**
1. Open the `Partone` project (**File > Open Project**).
2. Right-click the project and choose **Clean and Build**.
3. Right-click the project and choose **Run**.
4. Register and log in, then use the menu.

**Run the unit tests**
- In NetBeans: right-click the project and choose **Test**.
- With Maven: `mvn -B test`

The stored messages are saved in `storedMessages.json`, which is created in the project folder (next to `pom.xml`) the first time a message is stored.

---

## 2. Main menu

| Option | What it does |
|---|---|
| 1) Send Messages | Enter the messages. Each one can be sent, disregarded or stored. |
| 2) Show recently sent messages | Shows "Coming Soon", as in the Part 2 brief. |
| 3) Quit | Closes the app. |
| 4) Stored Messages | **New in Part 3.** Opens the Stored Messages menu (section 4). |

---

## 3. The arrays

All the arrays are normal Java arrays. Their size is not hard-coded: it comes from the number of messages the user types in, or from the number of messages in the JSON file, in the same way as `new String[golfmember]` in class (Farrell, 2022, p. 267). The arrays that belong together are **parallel arrays**: position 0 in every array is the same message, position 1 is the next message, and so on (Farrell, 2022, p. 284).

| Array | Contents | Filled |
|---|---|---|
| Sent Messages (`sentMessages`, with `sentRecipients` and `sentHashes`) | All the messages that were sent, with who they were sent to and their hash | When the user chooses **Send** |
| Disregarded Messages (`disregardedMessages`) | All the messages that were disregarded | When the user chooses **Disregard** |
| Stored Messages (`storedIDs`, `storedHashes`, `storedRecipients`, `storedMessages`) | All the messages stored in `storedMessages.json` | When the Stored Messages menu opens (read from the file) |
| Message Hash (`messageHashes`) | The hash of every message: the ones already in the file plus the new ones | Sent, disregarded and stored messages |
| Message ID (`messageIDs`) | The ID of every message: the ones already in the file plus the new ones | Sent, disregarded and stored messages |

The Sent, Disregarded, Message Hash and Message ID arrays are in `Message.java`, created by `createArrays(...)`. The Stored Messages arrays are made in the Stored Messages menu in `Partone.java`.

---

## 4. Stored Messages menu (option 4)

When the menu opens, `storedMessages.json` is read into the Stored Messages arrays and the messages that were read are displayed. The user can then choose:

| Menu option | Brief | Method (in `Message.java`) |
|---|---|---|
| 1 | Display the sender and recipient of all stored messages | `displaySenderAndRecipient` |
| 2 | Display the longest stored message | `displayLongestMessage` |
| 3 | Search for a message ID and display the recipient and message | `searchByMessageID` |
| 4 | Search for all the messages stored or sent for a particular recipient | `searchByRecipient` |
| 5 | Delete a message using the message hash | `findPosition`, `removeAt`, `saveStoredMessages`, `deletedMessageText` |
| 6 | Display a report with the full details of the messages | `displaySentReport` and `displayReport` |
| 7 | Back to the main menu | |

**How each one works**
- **Longest message:** a loop remembers the position of the longest message. That position is then used in the other parallel arrays to show the ID, hash and recipient.
- **Search for a message ID:** the ID array is searched with a loop that stops when the value is found. The position found is used in the recipient and message arrays.
- **Search for a recipient:** the Stored Messages arrays and the Sent Messages arrays are both searched. A recipient can have many messages, so the loops check every position.
- **Delete using the hash:** the hash array is searched, and the same position is removed from all four parallel arrays (a new, smaller array is made each time, because an array cannot change size). The smaller arrays are then saved to the file, so the message is also gone from `storedMessages.json`. If two messages have the same hash, the first one is deleted.
- **Reports:** one report lists the sent messages (message hash, recipient, message). The other lists the stored messages (sender, ID, hash, recipient, message). The sender is the user who is logged in.

---

## 5. The JSON file

`storedMessages.json` is one JSON array with one message object per line:

```json
[
{"messageID":"0112345678","messageHash":"01:1:WHERETIME","recipient":"+27838884567","message":"Where are you? You are late! I have asked you to be on time."},
{"messageID":"0412345678","messageHash":"04:4:OKYOU","recipient":"+27838884567","message":"Ok, I am leaving without you."}
]
```

**Reading the file into an array (`readStoredLines` and `getJsonValue`)**
1. The whole file is read into one `String` with `Files.readAllBytes`.
2. The text is cut into lines. The lines that contain a message (they contain `{`) are counted.
3. An array is made with exactly that size and each message line is copied into it.
4. For every line, `getJsonValue` cuts out the value of one key (`messageID`, `messageHash`, `recipient` or `message`) with `indexOf` and `substring`, and removes the escape characters that were added when the message was saved.

---

## 6. Unit tests

All the tests are in `src/test/java/com/mycompany/partone/MessageTest.java`. The Part 3 tests use this test data:

| Message | Recipient | Message text | Flag |
|---|---|---|---|
| 1 | +27834557896 | Did you get the cake? | Sent |
| 2 | +27838884567 | Where are you? You are late! I have asked you to be on time. | Stored |
| 3 | +27834484567 | Yohoooo, I am at your gate. | Disregard |
| 4 | 0838884567 | It is dinner time! | Sent |
| 5 | +27838884567 | Ok, I am leaving without you. | Stored |

| Test | What it checks |
|---|---|
| `testPart3Message1Sent` | Message 1: valid number and length, hash, and it is sent |
| `testPart3Message2Stored` | Message 2: hash, and it is saved in the JSON file |
| `testPart3Message3Disregard` | Message 3: hash, and it is disregarded, not sent |
| `testPart3Message4Recipient` | Message 4: the number without +27 is refused |
| `testPart3Message5Stored` | Message 5: hash, and it is saved in the JSON file |
| `testPart3SentMessagesArray` | The Sent Messages array contains "Did you get the cake?" and "It is dinner time!" |
| `testPart3LongestMessage` | The longest message (message 2) is displayed with its details |
| `testPart3SearchMessageID` | Searching the ID 0838884567 returns "It is dinner time!" |
| `testPart3SearchRecipient` | Searching +27838884567 returns messages 2 and 5 |
| `testPart3DeleteMessageUsingHash` | Deleting message 2 by its hash removes it from the arrays and the file |
| `testPart3DisplayReport` | The reports show the hash, recipient and message |

The tests for messages 2 and 5 save to the real `storedMessages.json`. The delete test uses its own test file, so it does not change your stored messages.

---

## 7. Automated tests (GitHub Actions)

The workflow file is `.github/workflows/TestJava.yml`. On every push and pull request, GitHub builds the project and runs all the unit tests with `mvn -B test`. A green tick on the **Actions** tab means every test passed.

---

## 8. Notes

- The menus expect a number. Typing letters instead of a number stops the program, as in the earlier parts.
- A message number or cell number has to be valid (cell numbers start with +27 and have 9 more digits) before a message can be sent.

---

## 9. References

- Bray, T. (ed.), 2017. *The JavaScript Object Notation (JSON) Data Interchange Format* (RFC 8259). https://www.rfc-editor.org/rfc/rfc8259
- Farrell, J., 2022. *Java Programming*. 10th ed. Boston: Cengage Learning. Chapter 8 Arrays (pp. 267-291).
- Oracle, 2024a. *FileWriter (Java Platform SE 8)*. https://docs.oracle.com/javase/8/docs/api/java/io/FileWriter.html
- Oracle, 2024b. *String (Java Platform SE 8)*. https://docs.oracle.com/javase/8/docs/api/java/lang/String.html
- Oracle, 2024c. *Files (Java Platform SE 8)*. https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html

The access dates are in the reference list at the bottom of `Partone.java`.
