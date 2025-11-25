package com.deigo.agente_ia.agente.service;
import com.deigo.agente_ia.agente.model.Conversation;
import com.deigo.agente_ia.agente.repository.ConversationRepository;
import com.deigo.agente_ia.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgenteService {

    private final ConversationRepository conversationRepository;

    private static final String PRIMO_RICO_SYSTEM_PROMPT = """
        Você é o Thiago Nigro, o 'Primo Rico'. Sua missão é fornecer conselhos de investimento e rebalancear 
        carteiras com base na metodologia ARCA (Ações, Renda Fixa, Criptoativos, Ativos Internacionais).
        Seu tom de voz é direto, didático, inspirador e focado em alta performance e 'virada de chave'.
        REGRAS DE REBALANCEAMENTO: Foco em plano de ação estruturado em JSON para o sistema cliente.
        """;

    public AgenteService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public RebalanceamentoCompletoResponse rebalancearCarteira(RebalanceamentoRequest request) {
        long startTime = System.currentTimeMillis();

        String userPrompt = String.format(
                "Minha alocação atual é: Ações (%.1f%%), Renda Fixa (%.1f%%), Criptoativos (%.1f%%), " +
                        "Ativos Internacionais (%.1f%%). Meu objetivo é: %s. \n\n" +
                        "Responda EXCLUSIVAMENTE em formato JSON com o objeto 'plano' " +
                        "(SugestaoRebalanceamento) e o 'conselhoMotivacional'.",
                request.percentualAtualAcoes(), request.percentualAtualRendaFixa(),
                request.percentualAtualCriptoativos(), request.percentualAtualAtivosInternacionais(),
                request.objetivo()
        );

        String rawResponse = callLlmApi(userPrompt, "REBALANCEAMENTO");

        RebalanceamentoCompletoResponse response = parseRebalanceamentoResponse(rawResponse);

        return response;
    }

    public AgenteResponsePainel responderChat(ChatRequestPainel request) {
        long startTime = System.currentTimeMillis();

        String userPrompt = "Pergunta do usuário: " + request.mensagemDoUsuario();

        String rawResponse = callLlmApi(userPrompt, "CHAT_DICAS");

        saveConversation(
                request.userID(),
                "CHAT_DICAS",
                userPrompt,
                rawResponse,
                System.currentTimeMillis() - startTime
        );

        return new AgenteResponsePainel(rawResponse);
    }

    private String callLlmApi(String userPrompt, String contextType) {

        System.out.printf("[LLM CALL - %s]: %s\n", contextType, userPrompt);

        if ("REBALANCEAMENTO".equals(contextType)) {
            return "{\"plano\": {\"acoes\": 25, \"rendaFixa\": 35, \"criptoativos\": 10, \"ativosInternacionais\": 30}, \"conselhoMotivacional\": \"A excelência está nos detalhes. Foque no longo prazo!\"}";
        } else {
            return "É hora de ter uma 'virada de chave' no seu conhecimento financeiro. Para sua dúvida: " + userPrompt.substring(18) + "...";
        }
    }

    private RebalanceamentoCompletoResponse parseRebalanceamentoResponse(String rawResponse) {

        return new RebalanceamentoCompletoResponse(
                new SugestaoRebalanceamento(25, 35, 10, 30),
                "Sua alocação é o seu mapa para a liberdade. Vamos rebalancear com foco total no longo prazo e na metodologia ARCA. O foco está nos Ativos Internacionais. Aja agora!"
        );
    }

    private void saveConversation(String userId, String contextType, String userMessage, String agentResponse, long durationMs) {
        Conversation log = new Conversation(
                null,
                userId,
                LocalDateTime.now(),
                contextType,
                userMessage,
                agentResponse,
                durationMs
        );
        conversationRepository.save(log);
        System.out.println("Conversa logada no MongoDB: " + contextType);
    }
}
