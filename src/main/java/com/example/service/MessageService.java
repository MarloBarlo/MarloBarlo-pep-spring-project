package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.MessageRepository;

import com.example.entity.Message;
import java.util.List;

import javax.transaction.Transactional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    //create message
    public Message createMessage(Message message){
        if(message.getMessageText() == null || message.getMessageText().isEmpty() ||
        message.getMessageText().length() > 255 || messageRepository.findMessagesByPostedBy(message.getPostedBy()) == null ){
            throw new IllegalArgumentException("Error with the message");
        }
        return messageRepository.save(message);
    }

    //get all messages
    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    //get message by messageId
    public Message getMessageByMessageId(int id){
        return messageRepository.findMessageByMessageId(id);
    }

    //delete message by messageId
    @Transactional
    public Integer deleteMessageByMessageId(int id){
        if (messageRepository.findMessageByMessageId(id) == null) {
            throw new IllegalArgumentException("message not found");
        }
        return messageRepository.deleteMessageByMessageId(id); 
    }

    //update message by id
    @Transactional
    public Message updateMessageById(String messageText, int messageId) {
        Message oldMessage = messageRepository.findMessageByMessageId(messageId);
        if (oldMessage == null || messageText.length() > 255 ||
            messageText.isEmpty() || messageText == null) {
                throw new IllegalArgumentException("message not found or message text error");
            }

        //update the message
        messageRepository.updatMessageByMessageId(messageText, messageId);
        return messageRepository.findMessageByMessageId(messageId);
    }

    //get all messages by postedby
    public List<Message> findMessagesByPostedBy(int postedBy) {
        return messageRepository.findMessagesByPostedBy(postedBy);
    }

}
