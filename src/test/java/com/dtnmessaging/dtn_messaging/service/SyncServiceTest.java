package com.dtnmessaging.dtn_messaging.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.dtnmessaging.dtn_messaging.repository.MessageRepository;
import com.dtnmessaging.dtn_messaging.repository.NodeRepository;
import com.dtnmessaging.dtn_messaging.model.Message;
import com.dtnmessaging.dtn_messaging.model.Node;
import com.dtnmessaging.dtn_messaging.model.MessageStatus;
import com.dtnmessaging.dtn_messaging.model.Priority;
import java.util.List;
import java.util.UUID;
import java.time.Instant;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

public class SyncServiceTest {
    UUID tNode = UUID.randomUUID();
    // shared
    UUID logicalId = UUID.randomUUID();
    UUID nodeA = UUID.randomUUID();
    UUID nodeB = UUID.randomUUID();
    MessageRepository mockMR = mock(MessageRepository.class);
    NodeRepository mockNR = mock(NodeRepository.class);
    SyncService syncService = new SyncService(mockMR, mockNR);
    Node node = new Node(tNode, "TestNode", false);

    @Test
    public void offlineNoSync() {
        // returns offline node at look up
        when(mockNR.findById(tNode)).thenReturn(Optional.of(node));
        syncService.syncNode(tNode);
        // verify sync checked for pending messages
        verify(mockMR, never()).findByNodeIDAndStatus(any(), any());
    }

    // message a and b with shared logicalids for conflic setup
    Message messageA = new Message(UUID.randomUUID(), logicalId, nodeA, "first message", MessageStatus.PENDING,
            Priority.MEDIUM, Instant.now().minusSeconds(60));
    // message b is the latest, so should win
    Message messageB = new Message(UUID.randomUUID(), logicalId, nodeB, "Edited message", MessageStatus.PENDING,
            Priority.MEDIUM, Instant.now());

    @Test
    // conflict resolution
    public void pickLatestMessageCR() {
        // online node, sync should run
        Node connectNode = new Node(nodeA, "NodeA", true);
        when(mockNR.findById(nodeA)).thenReturn(Optional.of(connectNode));
        // mock first node node has a pending message
        when(mockMR.findByNodeIDAndStatus(nodeA, MessageStatus.PENDING)).thenReturn(List.of(messageA));
        // triggers cr due to mock messages sharing same logicalid
        when(mockMR.findByLogicalID(logicalId)).thenReturn(List.of(messageA, messageB));
        syncService.syncNode(nodeA);
        // latest message wins and gets synced
        assertEquals(MessageStatus.SYNCED, messageB.getStatus());
        // older message loses and gets status put as superseded
        assertEquals(MessageStatus.SUPERSEDED, messageA.getStatus());

    }

}