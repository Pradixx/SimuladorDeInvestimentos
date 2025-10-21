package com.deigo.SimuladorDeInvestimentos.service;

import com.deigo.SimuladorDeInvestimentos.controller.DTO.CriarInvestimentosDTO;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.CDB;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Investimentos;
import com.deigo.SimuladorDeInvestimentos.infrastructure.repository.InvestimentosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InvestimentosServiceTest {

    @Mock
    private InvestimentosRepository investimentosRepository;

    @InjectMocks
    private InvestimentosService investimentosService;

    private Investimentos investimento;
    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        id = UUID.randomUUID();

        this.investimento = CDB.builder()
                .id(id)
                .nome("CDB Banco XP")
                .valorInicial(1000.0)
                .taxaJuros(10.0)
                .periodo(1)
                .cdiPercentual(0.9)
                .build();
    }

    @Test
    void deveCriarInvestimentoComSucesso() {
        CriarInvestimentosDTO dto = new CriarInvestimentosDTO(
                "CDB", "CDB Banco XP", 1500.0, 10.0, 12
        );

        when(investimentosRepository.saveAndFlush(any(Investimentos.class))).thenReturn(investimento);

        investimentosService.criarInvestimento(dto);

        verify(investimentosRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void deveBuscarInvestimentoPorId() {
        when(investimentosRepository.findById(id)).thenReturn(Optional.of(investimento));

        Investimentos resultado = (Investimentos) investimentosService.buscarInvestimentosPeloId(id);

        assertEquals("CDB Banco XP", resultado.getNome());
        verify(investimentosRepository, times(1)).findById(id);
    }

    @Test
    void deveDeletarInvestimentoPorId() {
        investimentosService.deletarInvestimentoPeloId(id);

        verify(investimentosRepository, times(1)).deleteById(id);
    }

    @Test
    void deveCalcularRendimentoCorretamente() {
        when(investimentosRepository.findById(id)).thenReturn(Optional.of(investimento));

        BigDecimal rendimento = investimentosService.calcularRendimento(id);

        assertNotNull(rendimento);
        BigDecimal expected = new BigDecimal("90.00");
        assertEquals(expected, rendimento);
    }

    @Test
    void deveAtualizarSomenteCamposNaoNulos() {
        when(investimentosRepository.findById(id)).thenReturn(Optional.of(investimento));

        CriarInvestimentosDTO dto = new CriarInvestimentosDTO(
                "CDB",
                "CDB SANTANDER",
                null,
                24.0,
                null
        );

        investimentosService.atualizarInvestimentos(id, dto);
        verify(investimentosRepository, times(1)).saveAndFlush(investimento);
        assertEquals("CDB SANTANDER", investimento.getNome());
        assertEquals(1000.0, investimento.getValorInicial());
        assertEquals(24.0, investimento.getTaxaJuros());
        assertEquals(1, investimento.getPeriodo());
    }
}

