package com.deigo.agente_ia.dto;

import lombok.Builder;

@Builder
public record SugestaoRebalanceamento(
        int acoes,
        int rendaFixa,
        int criptoativos,
        int ativosInternacionais
) {}
