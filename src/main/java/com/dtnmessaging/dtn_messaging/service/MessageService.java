package com.dtnmessaging.dtn_messaging.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dtnmessaging.dtn_messaging.model.Message;
import com.dtnmessaging.dtn_messaging.model.MessageStatus;
import com.dtnmessaging.dtn_messaging.model.Priority;
import com.dtnmessaging.dtn_messaging.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public Message createMessage(String content, UUID nodeID, Priority priority) {
        // generate fresh id with randomuuid
        UUID freshId = UUID.randomUUID();
        // create the message
        Message message = new Message(null, freshId, nodeID, content, MessageStatus.PENDING, priority, Instant.now());
        // save in repository and return
        return messageRepository.save(message);
    }

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
}
