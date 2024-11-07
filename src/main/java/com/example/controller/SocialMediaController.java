package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    // User Registration
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        if (account.getUsername() == null || account.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (accountService.getAccountByUsername(account.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    // User Login
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account existingAccount = accountService.login(account.getUsername(), account.getPassword());
        if (existingAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(existingAccount);
    }

    // Create Message
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if (message.getMessageText() == null || message.getMessageText().isEmpty() || message.getMessageText().length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (accountService.getAccountByUsername(message.getPostedBy().toString()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        //message.setTimePostedEpoch(System.currentTimeMillis());
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    // Retrieve All Messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.findAllMessages();
        return ResponseEntity.ok(messages);
    }

    // Retrieve Message by ID
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        Message message = messageService.getMessageByMessageId(messageId);
        return ResponseEntity.ok(message);
    }

    // Delete Message by ID
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Void> deleteMessageById(@PathVariable int messageId) {
        messageService.deleteMessageByMessageId(messageId);
        return ResponseEntity.ok().build();
    }

    // Update Message Text
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message updatedMessage) {
        Message existingMessage = messageService.getMessageByMessageId(messageId);
        if (existingMessage == null || updatedMessage.getMessageText() == null || updatedMessage.getMessageText().isEmpty() || updatedMessage.getMessageText().length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        existingMessage.setMessageText(updatedMessage.getMessageText());
        messageService.createMessage(existingMessage);
        return ResponseEntity.ok(1); // Return the number of rows updated
    }

    // Retrieve Messages by User
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser (@PathVariable int accountId) {
        List<Message> messages = messageService.findMessagesByPostedBy(accountId);
        return ResponseEntity.ok(messages);
    }
}
