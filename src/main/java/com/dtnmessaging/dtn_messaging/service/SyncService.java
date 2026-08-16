package com.dtnmessaging.dtn_messaging.service;

import org.springframework.stereotype.Service;

import com.dtnmessaging.dtn_messaging.repository.MessageRepository;
import com.dtnmessaging.dtn_messaging.repository.NodeRepository;

@Service
public class SyncService {
    private final MessageRepository messageRepository;
    private final NodeRepository nodeRepository;

    public SyncService(MessageRepository messageRepository, NodeRepository nodeRepository) {
        this.messageRepository = messageRepository;
        this.nodeRepository = nodeRepository;
    }
}
