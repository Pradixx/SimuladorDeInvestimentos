package com.deigo.SimuladorDeInvestimentos.controller;

import com.deigo.SimuladorDeInvestimentos.controller.DTO.CriarInvestimentosDTO;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Investimentos;
import com.deigo.SimuladorDeInvestimentos.service.InvestimentosService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvestimentosController.class)
class InvestimentosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvestimentosService investimentosService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarInvestimento() throws Exception {
        UUID id = UUID.randomUUID();
        CriarInvestimentosDTO dto = new CriarInvestimentosDTO(
                "TesouroDireto", "Tesouro Direto Selic", 2000.0, 5.0, 6
        );

        Mockito.when(investimentosService.criarInvestimento(any(CriarInvestimentosDTO.class))).thenReturn(id);

        mockMvc.perform(post("/investimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.mensagem").value("Investimento criado com sucesso!"));
    }

    @Test
    void deveBuscarInvestimentoPorId() throws Exception {
        UUID id = UUID.randomUUID();
        Investimentos investimento = new Investimentos() {
            @Override
            public BigDecimal calcularRendimento() {
                return null;
            }
        };
        investimento.setId(id);
        investimento.setNome("Tesouro Direto");

        Mockito.when(investimentosService.buscarInvestimentosPeloId(id)).thenReturn(investimento);

        mockMvc.perform(get("/investimentos/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Tesouro Direto"));

        Mockito.verify(investimentosService).buscarInvestimentosPeloId(id);
    }

    @Test
    void deveDeletarInvestimento() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/investimentos/{id}", id.toString()))
                .andExpect(status().isOk());

        Mockito.verify(investimentosService).deletarInvestimentoPeloId(id);
    }

    @Test
    void deveAtualizarInvestimento() throws Exception {
        UUID id = UUID.randomUUID();
        CriarInvestimentosDTO dto = new CriarInvestimentosDTO("TesouroDireto", "Tesouro IPCA", 1000.0, 4.5, 12);

        Mockito.when(investimentosService.atualizarInvestimentos(eq(id), any(CriarInvestimentosDTO.class))).thenReturn(id);

        mockMvc.perform(put("/investimentos/{id}", id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        Mockito.verify(investimentosService).atualizarInvestimentos(eq(id), any(CriarInvestimentosDTO.class));
    }

    @Test
    void deveCalcularRendimento() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(investimentosService.calcularRendimento(id)).thenReturn(new BigDecimal("150.00"));

        mockMvc.perform(get("/investimentos/" + id + "/rendimento"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rendimento").value(150.00))
                .andExpect(jsonPath("$.mensagem").value("Rendimento calculado com sucesso!"));
    }

    @Test
    void deveListarTodosInvestimentos() throws Exception {
        Investimentos investimento = new Investimentos() {
            @Override
            public BigDecimal calcularRendimento() {
                return null;
            }
        };
        investimento.setNome("Tesouro Direto");

        Mockito.when(investimentosService.listarTodosInvestimentos())
                .thenReturn(Collections.singletonList(investimento));

        mockMvc.perform(get("/investimentos/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Tesouro Direto"));
    }
}
