package com.dtnmessaging.dtn_messaging.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dtnmessaging.dtn_messaging.DTO.MessageRequest;
import com.dtnmessaging.dtn_messaging.model.Message;
import com.dtnmessaging.dtn_messaging.service.MessageService;
import com.dtnmessaging.dtn_messaging.service.SyncService;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final SyncService syncService;

    // create message
    @PostMapping()
    public Message createMessage(@RequestBody MessageRequest request) {

        Message message = messageService.createMessage(request.getContent(), request.getNodeId(),
                request.getPriority());
        return message;
    }

    // sync message
    @PostMapping("{nodeId}/sync")
    public void triggerSync(@PathVariable UUID nodeId) {
        syncService.syncNode(nodeId);
    }

    //
    @GetMapping("{nodeId}")
    public List<Message> viewAllMessage(@PathVariable UUID nodeId) {
        return messageService.findMessageByNode(nodeId);
    }

    public MessageController(MessageService messageService, SyncService syncService) {
        this.messageService = messageService;
        this.syncService = syncService;
    }

}
