package com.deigo.agente_ia.dto;

public record RebalanceamentoRequest (
    String userID,
    double percentualAtualAcoes,
    double percentualAtualRendaFixa,
    double percentualAtualCriptoativos,
    double percentualAtualAtivosInternacionais,
    String objetivo
) {}
