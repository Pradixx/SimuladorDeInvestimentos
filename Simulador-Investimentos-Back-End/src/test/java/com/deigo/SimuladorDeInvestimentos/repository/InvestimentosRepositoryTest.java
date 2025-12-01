package com.deigo.SimuladorDeInvestimentos.repository;

import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.CDB;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Investimentos;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.TesouroDireto;
import com.deigo.SimuladorDeInvestimentos.infrastructure.repository.InvestimentosRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class InvestimentosRepositoryTest {

    @Autowired
    private InvestimentosRepository investimentosRepository;

    @Test
    void deveSalvarEBuscarInvestimentoCDB() {
        CDB investimentoCDB = CDB.builder()
                .nome("CDB Banco XP")
                .valorInicial(1500.0)
                .taxaJuros(10.0)
                .cdiPercentual(105.0)
                .periodo(12)
                .build();

        Investimentos salvo = investimentosRepository.save(investimentoCDB);
        assertNotNull(salvo.getId());

        Optional<Investimentos> encontrado = investimentosRepository.findById(salvo.getId());
        assertTrue(encontrado.isPresent());

        assertTrue(encontrado.get() instanceof CDB);
        assertEquals("CDB Banco XP", encontrado.get().getNome());

        assertEquals(105.0, ((CDB) encontrado.get()).getCdiPercentual());
    }

    @Test
    void deveDeletarInvestimentoTesouroDireto() {
        TesouroDireto investimentoTesouro = TesouroDireto.builder()
                .nome("Tesouro Direto Selic")
                .valorInicial(2000.0)
                .taxaPrefixada(5.0)
                .ipca(3.5)
                .periodo(6)
                .build();


        Investimentos salvo = investimentosRepository.save(investimentoTesouro);
        UUID id = salvo.getId();

        investimentosRepository.deleteById(id);

        Optional<Investimentos> resultado = investimentosRepository.findById(id);
        assertFalse(resultado.isPresent());
    }
}