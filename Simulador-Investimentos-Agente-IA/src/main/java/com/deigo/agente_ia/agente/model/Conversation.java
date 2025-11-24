package com.deigo.agente_ia.agente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "conversations")
public class Conversation {

    @Id
    private String id;
    private String userId;
    private LocalDateTime timestamp;
    private String contextType; // "REBALANCEAMENTO" ou "CHAT_DICAS"
    private String userMessage;
    private String agentResponse;
    private long durationMs;
}
