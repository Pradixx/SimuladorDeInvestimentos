package com.deigo.SimuladorDeInvestimentos.repository;

import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Investimentos;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.TestInvestimento;
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
    void deveSalvarEBuscarInvestimento() {
        TestInvestimento investimento = new TestInvestimento();
        investimento.setTipo("CDB");
        investimento.setNome("CDB Banco XP");
        investimento.setValorInicial(1500.0);
        investimento.setTaxaJuros(10.0);
        investimento.setPeriodo(12);

        Investimentos salvo = investimentosRepository.save(investimento);
        assertNotNull(salvo.getId());

        Optional<Investimentos> encontrado = investimentosRepository.findById(salvo.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("CDB Banco XP", encontrado.get().getNome());
    }

    @Test
    void deveDeletarInvestimento() {
        TestInvestimento investimento = new TestInvestimento();
        investimento.setTipo("TesouroDireto");
        investimento.setNome("Tesouro Direto Selic");
        investimento.setValorInicial(2000.0);
        investimento.setTaxaJuros(5.0);
        investimento.setPeriodo(6);


        Investimentos salvo = investimentosRepository.save(investimento);
        UUID id = salvo.getId();

        investimentosRepository.deleteById(id);

        Optional<Investimentos> resultado = investimentosRepository.findById(id);
        assertFalse(resultado.isPresent());
    }
}
