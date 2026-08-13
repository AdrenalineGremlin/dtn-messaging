package com.dtnmessaging.dtn_messaging.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {
    private @Id @GeneratedValue UUID id;
    // used as a shared identifier that two messages carry
    private UUID logicalID;
    // what node has or created version
    private UUID nodeID;
    private String content;
    @Enumerated(EnumType.STRING)
    private MessageStatus status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    // last write is what gets read
    private Instant lastModified;

}
