package com.dtnmessaging.dtn_messaging.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dtnmessaging.dtn_messaging.model.Message;
import com.dtnmessaging.dtn_messaging.model.MessageStatus;
import com.dtnmessaging.dtn_messaging.model.Priority;
import com.dtnmessaging.dtn_messaging.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public Message createMessage(String content, UUID nodeID, Priority priority, UUID givenLogicalId) {
        // generate fresh id with randomuuid
        UUID logicalId = (givenLogicalId != null) ? givenLogicalId : UUID.randomUUID();
        // create the message
        Message message = new Message(null, logicalId, nodeID, content, MessageStatus.PENDING, priority, Instant.now());
        // save in repository and return
        return messageRepository.save(message);
    }

    public List<Message> findMessageByNode(UUID nodeId) {
        return messageRepository.findByNodeID(nodeId);
    }

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
}
