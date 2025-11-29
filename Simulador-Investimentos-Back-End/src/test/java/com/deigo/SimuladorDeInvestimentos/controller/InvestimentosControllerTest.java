package com.deigo.SimuladorDeInvestimentos.controller;

import com.deigo.SimuladorDeInvestimentos.config.TokenService;
import com.deigo.SimuladorDeInvestimentos.controller.DTO.CriarInvestimentosDTO;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.CDB;
import com.deigo.SimuladorDeInvestimentos.service.InvestimentosService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class InvestimentosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvestimentosService investimentosService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/simulador";

    @Test
    @WithMockUser(roles = "USER")
    void deveCriarInvestimento() throws Exception {
        UUID id = UUID.randomUUID();
        CriarInvestimentosDTO dto = new CriarInvestimentosDTO(
                "TesouroDireto", "Tesouro Direto Selic", 2000.0, 5.0, 6, null, 5.0, 0.0, 6.0
        );

        Mockito.when(investimentosService.criarInvestimento(any(CriarInvestimentosDTO.class))).thenReturn(id);

        mockMvc.perform(post(BASE_URL) // Corrigido para /api/simulador
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.mensagem").value("Investimento criado com sucesso!"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveBuscarInvestimentoPorId() throws Exception {
        UUID id = UUID.randomUUID();
        CDB investimento = CDB.builder()
                .id(id)
                .nome("Tesouro Direto")
                .build();

        Mockito.when(investimentosService.buscarInvestimentosPeloId(id)).thenReturn(investimento);

        mockMvc.perform(get(BASE_URL + "/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Tesouro Direto"));

        Mockito.verify(investimentosService).buscarInvestimentosPeloId(id);
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveDeletarInvestimento() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete(BASE_URL + "/{id}", id.toString()))
                .andExpect(status().isOk());

        Mockito.verify(investimentosService).deletarInvestimentoPeloId(id);
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveAtualizarInvestimento() throws Exception {
        UUID id = UUID.randomUUID();

        CriarInvestimentosDTO dto = new CriarInvestimentosDTO(
                "TESOURODIRETO",
                "Tesouro IPCA Atualizado",
                1000.0,
                null,
                null,
                null,
                4.5,
                1.5,
                12.0
        );

        Mockito.when(investimentosService.atualizarInvestimentos(eq(id), any(CriarInvestimentosDTO.class))).thenReturn(id);

        mockMvc.perform(put(BASE_URL + "/{id}", id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        Mockito.verify(investimentosService).atualizarInvestimentos(eq(id), any(CriarInvestimentosDTO.class));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveCalcularRendimento() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(investimentosService.calcularRendimento(id)).thenReturn(new BigDecimal("150.00"));

        mockMvc.perform(get(BASE_URL + "/" + id + "/rendimento"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rendimento").value(150.00))
                .andExpect(jsonPath("$.mensagem").value("Rendimento calculado com sucesso!"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deveListarTodosInvestimentos() throws Exception {
        CDB investimento = CDB.builder().nome("CDB Teste").build();

        Mockito.when(investimentosService.listarTodosInvestimentos())
                .thenReturn(Collections.singletonList(investimento));

        mockMvc.perform(get(BASE_URL + "/todos")) // Corrigido para /api/simulador/todos
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("CDB Teste"));
    }
}