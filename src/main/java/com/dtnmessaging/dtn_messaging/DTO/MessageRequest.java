package com.dtnmessaging.dtn_messaging.DTO;

import java.util.UUID;

import com.dtnmessaging.dtn_messaging.model.Priority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {
    private String content;
    private UUID nodeId;
    private Priority priority;
}
