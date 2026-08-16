package com.dtnmessaging.dtn_messaging.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dtnmessaging.dtn_messaging.model.Message;
import com.dtnmessaging.dtn_messaging.model.MessageStatus;
import com.dtnmessaging.dtn_messaging.model.Node;
import com.dtnmessaging.dtn_messaging.repository.MessageRepository;
import com.dtnmessaging.dtn_messaging.repository.NodeRepository;

@Service
public class SyncService {
    private final MessageRepository messageRepository;
    private final NodeRepository nodeRepository;

    public void syncNode(UUID nodeID) {
        // get the node and check if theres connectivity
        Node nr = nodeRepository.findById(nodeID).orElseThrow();
        if (!nr.isConnectivity()) {
            // if false there is nothing to sync - return
            return;
        }
        // fetch pending messages for current node
        List<Message> messages = messageRepository.findByNodeIdAndStatus(nodeID, MessageStatus.PENDING);
        ;
    }

    public SyncService(MessageRepository messageRepository, NodeRepository nodeRepository) {
        this.messageRepository = messageRepository;
        this.nodeRepository = nodeRepository;
    }
}
