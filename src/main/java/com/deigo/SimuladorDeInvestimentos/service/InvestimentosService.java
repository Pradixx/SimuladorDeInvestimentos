package com.deigo.SimuladorDeInvestimentos.service;

import com.deigo.SimuladorDeInvestimentos.controller.DTO.CriarInvestimentosDTO;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.CDB;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Investimentos;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Poupanca;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.TesouroDireto;
import com.deigo.SimuladorDeInvestimentos.infrastructure.repository.InvestimentosRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;


@Service
public class InvestimentosService {

    private final InvestimentosRepository investimentosRepository;

    public InvestimentosService(InvestimentosRepository investimentosRepository) {
        this.investimentosRepository = investimentosRepository;
    }

    //POST
    public void salvaInvestimento(Investimentos investimentos) {
        investimentosRepository.saveAndFlush(investimentos);
    }

    //GET
    public Object buscarInvestimentosPeloId(UUID id) {
        return investimentosRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Id não encontrado"));
    }

    //DELETE
    public void deletarInvestimentoPeloId (UUID id) {
        investimentosRepository.deleteById(id);
    }

    //PUT
    public UUID atualizarInvestimentos(UUID id, CriarInvestimentosDTO criarInvestimentosDTO) {
        Object investimentosEntity = investimentosRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Id não encontrado"));

        Investimentos entity = switch (criarInvestimentosDTO.tipo().toUpperCase()) {
            case "CDB" -> {
                var cdb = CDB.builder()
                        .nome(criarInvestimentosDTO.nome())
                        .valorInicial(criarInvestimentosDTO.valorInicial())
                        .taxaJuros(criarInvestimentosDTO.taxaJuros())
                        .periodo(criarInvestimentosDTO.periodo())
                        .cdiPercentual(0.9)
                        .build();
                yield cdb;
            }

            case "POUPANCA" -> {
                var poupanca = Poupanca.builder()
                        .nome(criarInvestimentosDTO.nome())
                        .valorInicial(criarInvestimentosDTO.valorInicial())
                        .taxaJuros(criarInvestimentosDTO.taxaJuros())
                        .periodo(criarInvestimentosDTO.periodo())
                        .selicAnual(13.0)
                        .build();
                yield poupanca;
            }

            case "TESOURODIRETO" -> {
                var tesouro = TesouroDireto.builder()
                        .nome(criarInvestimentosDTO.nome())
                        .valorInicial(criarInvestimentosDTO.valorInicial())
                        .taxaJuros(criarInvestimentosDTO.taxaJuros())
                        .periodo(criarInvestimentosDTO.periodo())
                        .taxaPrefixada(11.0)
                        .ipca(6.0)
                        .build();
                yield tesouro;
            }

            default -> throw new IllegalArgumentException("Tipo de investimento inválido");
        };
        investimentosRepository.saveAndFlush(entity);
        return entity.getId();
    }

    public BigDecimal calcularRendimento(UUID investimentoId) {
        Object investimento = investimentosRepository.findById(investimentoId).orElseThrow(
                () -> new RuntimeException("Id não encontrado"));

        double rendimento;

        if (investimento instanceof CDB cdb) {
            double taxaEfetiva = cdb.getTaxaJuros() * cdb.getCdiPercentual();
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
