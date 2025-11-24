package com.deigo.agente_ia.agente.repository;

import com.deigo.agente_ia.agente.model.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversationRepository extends MongoRepository<Conversation, String> {

    List<Conversation> findByUserId (String userId);
}
