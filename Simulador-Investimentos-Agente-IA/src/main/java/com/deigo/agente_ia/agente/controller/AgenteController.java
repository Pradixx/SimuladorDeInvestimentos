package com.deigo.agente_ia.agente.controller;

import com.deigo.agente_ia.agente.service.AgenteService;
import com.deigo.agente_ia.dto.AgenteResponsePainel;
import com.deigo.agente_ia.dto.ChatRequestPainel;
import com.deigo.agente_ia.dto.RebalanceamentoCompletoResponse;
import com.deigo.agente_ia.dto.RebalanceamentoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/agente-ia/")
public class AgenteController {

    private final AgenteService agenteService;

    public AgenteController(AgenteService agenteService) {
        this.agenteService = agenteService;
    }

    @PostMapping("/rebalanceamento/arca")
    public ResponseEntity<RebalanceamentoCompletoResponse> rebalancear(
            @RequestBody RebalanceamentoRequest request) {

        RebalanceamentoCompletoResponse response = agenteService.rebalancearCarteira(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/chat/pergunta")
    public ResponseEntity<AgenteResponsePainel> chat(
            @RequestBody ChatRequestPainel request) {

        AgenteResponsePainel response = agenteService.responderChat(request);
        return ResponseEntity.ok(response);
    }
}
