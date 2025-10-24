package com.deigo.SimuladorDeInvestimentos.service;

import com.deigo.SimuladorDeInvestimentos.controller.DTO.CriarInvestimentosDTO;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.CDB;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Investimentos;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Poupanca;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.TesouroDireto;
import com.deigo.SimuladorDeInvestimentos.infrastructure.repository.InvestimentosRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;


@Service
public class InvestimentosService {

    private final InvestimentosRepository investimentosRepository;

    public InvestimentosService(InvestimentosRepository investimentosRepository) {
        this.investimentosRepository = investimentosRepository;
    }

    //POST
    public void salvaInvestimento(CriarInvestimentosDTO criarInvestimentosDTO) {
        Investimentos investimentos = criarEntidade(criarInvestimentosDTO);
        investimentosRepository.saveAndFlush(investimentos);
    }

    //GET
    public Investimentos buscarInvestimentosPeloId(UUID id) {
        return investimentosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investimento não encontrado"));
    }

    //DELETE
    public void deletarInvestimentoPeloId (@RequestParam UUID id) {
        investimentosRepository.deleteById(id);
    }

    //PUT
    public UUID atualizarInvestimentos(UUID id, CriarInvestimentosDTO dto) {
        Investimentos investimento = investimentosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investimento não encontrado"));

        // Atualiza só o que veio no DTO
        if (dto.nome() != null) investimento.setNome(dto.nome());
        if (dto.valorInicial() != null) investimento.setValorInicial(dto.valorInicial());
        if (dto.taxaJuros() != null) investimento.setTaxaJuros(dto.taxaJuros());
        if (dto.periodo() != null) investimento.setPeriodo(dto.periodo());

        investimentosRepository.saveAndFlush(investimento);
        return investimento.getId();
    }

    private Investimentos criarEntidade(CriarInvestimentosDTO dto) {
        if (dto.tipo() == null || dto.tipo().isBlank()) {
            throw new IllegalArgumentException("O tipo de investimento é obrigatório.");
        }

        return switch (dto.tipo().toUpperCase()) {
            case "CDB" -> CDB.builder()
                    .nome(dto.nome())
                    .valorInicial(dto.valorInicial())
                    .taxaJuros(dto.taxaJuros())
                    .periodo(dto.periodo())
                    .cdiPercentual(0.9)
                    .build();

            case "POUPANCA" -> Poupanca.builder()
                    .nome(dto.nome())
                    .valorInicial(dto.valorInicial())
                    .taxaJuros(dto.taxaJuros())
                    .periodo(dto.periodo())
                    .selicAnual(13.0)
                    .build();

            case "TESOURODIRETO" -> TesouroDireto.builder()
                    .nome(dto.nome())
                    .valorInicial(dto.valorInicial())
                    .taxaJuros(dto.taxaJuros())
                    .periodo(dto.periodo())
                    .taxaPrefixada(11.0)
                    .ipca(6.0)
                    .build();

            default -> throw new IllegalArgumentException("Tipo de investimento inválido");
        };
    }

    public UUID criarInvestimento(CriarInvestimentosDTO criarInvestimentosDTO) {
        var investimento = criarEntidade(criarInvestimentosDTO);
        investimentosRepository.saveAndFlush(investimento);
        return investimento.getId();
    }

    //GET
    public List<Investimentos> listarTodosInvestimentos() {
        return investimentosRepository.findAll();
    }

    //GET
    public BigDecimal calcularRendimento(UUID investimentoId) {
        Object investimento = investimentosRepository.findById(investimentoId).orElseThrow(
                () -> new RuntimeException("Id não encontrado"));

        double rendimento;

        if (investimento instanceof CDB cdb) {
            double taxaDecimal = cdb.getTaxaJuros() / 100.0;

            double taxaEfetiva = taxaDecimal * cdb.getCdiPercentual();

            double montante = cdb.getValorInicial() * Math.pow(1 + taxaEfetiva, cdb.getPeriodo());
            rendimento = montante - cdb.getValorInicial();

        } else if (investimento instanceof Poupanca poupanca) {
            double taxaEfetiva = poupanca.getSelicAnual() / 12 / 100;
            double montante = poupanca.getValorInicial() * Math.pow(1 + taxaEfetiva, poupanca.getPeriodo());
            rendimento = montante - poupanca.getValorInicial();

        } else if (investimento instanceof TesouroDireto tesouro) {
            double taxaEfetiva = (tesouro.getTaxaPrefixada() + tesouro.getIpca()) / 100;
            double montante = tesouro.getValorInicial() * Math.pow(1 + taxaEfetiva, tesouro.getPeriodo());
            rendimento = montante - tesouro.getValorInicial();

        } else {
            throw new IllegalArgumentException("Tipo de investimento desconhecido");
        }

        return BigDecimal.valueOf(rendimento).setScale(2, RoundingMode.HALF_UP);
    }
}
