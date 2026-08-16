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

        for (Message m : messages) {
            //check if ther are any messages sharing the same logicalId
            //store the ids in a list
            List<Message> twin = messageRepository.findByLogicalID(m.getLogicalID());
            if (twin.size() == 1) {
                //if none share the id status is synced
                m.setStatus(MessageStatus.SYNCED);
                messageRepository.save(m);
            }.

        }
    }

    public SyncService(MessageRepository messageRepository, NodeRepository nodeRepository) {
        this.messageRepository = messageRepository;
        this.nodeRepository = nodeRepository;
    }
}
