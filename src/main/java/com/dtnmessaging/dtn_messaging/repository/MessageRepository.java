package com.dtnmessaging.dtn_messaging.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dtnmessaging.dtn_messaging.model.Message;
import com.dtnmessaging.dtn_messaging.model.MessageStatus;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    // list instead of optional - messages can share the same node and status combo
    List<Message> findByNodeIdAndStatus(UUID nodeId, MessageStatus status);

    // list instead of optional - logicalid to be shared by many messages
    List<Message> findByLogicalID(UUID logicalID);
}
