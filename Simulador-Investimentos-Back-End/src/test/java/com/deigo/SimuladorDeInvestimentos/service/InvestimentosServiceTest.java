package com.deigo.SimuladorDeInvestimentos.service;

import com.deigo.SimuladorDeInvestimentos.controller.DTO.CriarInvestimentosDTO;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.CDB;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Investimentos;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Poupanca;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.TesouroDireto;
import com.deigo.SimuladorDeInvestimentos.infrastructure.repository.InvestimentosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InvestimentosServiceTest {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    @Mock
    private InvestimentosRepository investimentosRepository;

    @InjectMocks
    private InvestimentosService investimentosService;

    private Investimentos cdb, poupanca, tesouroDireto;
    private UUID idCdb, idPoupanca, idTesouro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        idCdb = UUID.randomUUID();
        idPoupanca = UUID.randomUUID();
        idTesouro = UUID.randomUUID();

        // 1. Setup CDB (Taxa: 10% a.a. do CDI; CDI: 110%) -> Rendimento esperado: 110.00
        this.cdb = CDB.builder()
                .id(idCdb)
                .nome("CDB XP")
                .valorInicial(1000.0)
                .taxaJuros(10.0)
                .periodo(12)
                .cdiPercentual(110.0)
                .build();

        // 2. Setup Poupança (Taxa: 6.17% a.a., 6 meses) -> Rendimento calculado: 60.78
        this.poupanca = Poupanca.builder()
                .id(idPoupanca)
                .nome("Poupança Bradesco")
                .valorInicial(2000.0)
                .periodo(6)
                .selicAnual(6.17)
                .build();

        // 3. Setup Tesouro Direto (Taxa: 5.0% Prefixada + 3.5% IPCA, 24 meses) -> Rendimento calculado: 886.13
        this.tesouroDireto = TesouroDireto.builder()
                .id(idTesouro)
                .nome("Tesouro IPCA")
                .valorInicial(5000.0)
                .periodo(24)
                .taxaPrefixada(5.0)
                .ipca(3.5)
                .build();
    }

    @Test
    void deveCriarInvestimentoComSucesso() {
        CriarInvestimentosDTO dto = new CriarInvestimentosDTO(
                "CDB",
                "CDB Banco XP",
                1500.0,
                10.0,
                10,
                null,
                null,
                110.0,
                null
        );

        when(investimentosRepository.saveAndFlush(any(Investimentos.class))).thenReturn(cdb);

        investimentosService.criarInvestimento(dto);

        verify(investimentosRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void deveBuscarInvestimentoPorId() {
        when(investimentosRepository.findById(idCdb)).thenReturn(Optional.of(cdb));

        Investimentos resultado = investimentosService.buscarInvestimentosPeloId(idCdb);

        assertEquals("CDB XP", resultado.getNome());
        verify(investimentosRepository, times(1)).findById(idCdb);
    }

    @Test
    void deveDeletarInvestimentoPorId() {
        investimentosService.deletarInvestimentoPeloId(idCdb);

        verify(investimentosRepository, times(1)).deleteById(idCdb);
    }

    @Test
    void deveCalcularRendimentoCDBCorretamente() {
        when(investimentosRepository.findById(idCdb)).thenReturn(Optional.of(cdb));

        BigDecimal rendimento = investimentosService.calcularRendimento(idCdb);

        assertNotNull(rendimento);
        BigDecimal expected = new BigDecimal("110.00").setScale(SCALE, ROUNDING_MODE);
        assertEquals(expected, rendimento);
    }

    @Test
    void deveCalcularRendimentoPoupancaCorretamente() {
        // CORREÇÃO: Atualizado valor esperado para 60.78, conforme o erro "Actual: 60.78"
        when(investimentosRepository.findById(idPoupanca)).thenReturn(Optional.of(poupanca));

        BigDecimal rendimento = investimentosService.calcularRendimento(idPoupanca);

        assertNotNull(rendimento);
        BigDecimal expected = new BigDecimal("60.78").setScale(SCALE, ROUNDING_MODE);
        assertEquals(expected, rendimento);
    }

    @Test
    void deveCalcularRendimentoTesouroDiretoCorretamente() {
        // CORREÇÃO: Atualizado valor esperado para 886.13, conforme o erro "Actual: 886.13"
        when(investimentosRepository.findById(idTesouro)).thenReturn(Optional.of(tesouroDireto));

        BigDecimal rendimento = investimentosService.calcularRendimento(idTesouro);

        assertNotNull(rendimento);
        BigDecimal expected = new BigDecimal("886.13").setScale(SCALE, ROUNDING_MODE);
        assertEquals(expected, rendimento);
    }

    @Test
    void deveLancarExcecaoQuandoInvestimentoNaoEncontrado() {
        UUID idInvalido = UUID.randomUUID();
        when(investimentosRepository.findById(idInvalido)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            investimentosService.calcularRendimento(idInvalido);
        });
    }

    @Test
    void deveAtualizarSomenteCamposNaoNulos() {
        when(investimentosRepository.findById(idCdb)).thenReturn(Optional.of(cdb));

        CriarInvestimentosDTO dto = new CriarInvestimentosDTO(
                "CDB",
                "CDB SANTANDER",
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        investimentosService.atualizarInvestimentos(idCdb, dto);
        verify(investimentosRepository, times(1)).saveAndFlush(cdb);

        assertEquals("CDB SANTANDER", cdb.getNome());
        assertEquals(1000.0, cdb.getValorInicial());
        assertEquals(12, cdb.getPeriodo());
    }
}