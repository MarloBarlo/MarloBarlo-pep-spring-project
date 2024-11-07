package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Message;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long>{

    //Message createMessage(Message message);

    //List<Message> findAllMessages();

    Message findMessageByMessageId(int id);

    Message deleteMessageByMessageId(int id);

    @Modifying
    @Query("UPDATE Message SET message_text= :messageText WHERE message_id = :messageId")
    Message updatMessageByMessageId (String messageText, int messageId);

    List<Message> findMessagesByPostedBy(int postedBy);


}
