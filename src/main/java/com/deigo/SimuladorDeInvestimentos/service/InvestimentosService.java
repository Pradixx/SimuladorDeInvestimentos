package com.deigo.SimuladorDeInvestimentos.service;

import com.deigo.SimuladorDeInvestimentos.controller.DTO.CriarInvestimentoDTO;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.CDB;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Investimentos;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Poupanca;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.TesouroDireto;
import com.deigo.SimuladorDeInvestimentos.infrastructure.repository.InvestimentosRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvestimentosService {

    private final InvestimentosRepository investimentosRepository;

    public InvestimentosService(InvestimentosRepository investimentosRepository) {
        this.investimentosRepository = investimentosRepository;
    }


    public UUID criarInvestimentos(CriarInvestimentoDTO criarInvestimentoDTO) {

        Investimentos entity = switch (criarInvestimentoDTO.tipo().toUpperCase()) {
            case "CDB" -> {
                var cdb = CDB.builder()
                        .nome(criarInvestimentoDTO.nome())
                        .valorInicial(criarInvestimentoDTO.valorInicial())
                        .taxaJuros(criarInvestimentoDTO.taxaJuros())
                        .periodo(criarInvestimentoDTO.periodo())
                        .cdiPercentual(0.9)
                        .build();
                yield cdb;
            }

            case "POUPANCA" -> {
                var poupanca = Poupanca.builder()
                        .nome(criarInvestimentoDTO.nome())
                        .valorInicial(criarInvestimentoDTO.valorInicial())
                        .taxaJuros(criarInvestimentoDTO.taxaJuros())
                        .periodo(criarInvestimentoDTO.periodo())
                        .selicAnual(13.0)
                        .build();
                yield poupanca;
            }

            case "TESOURODIRETO" -> {
                var tesouro = TesouroDireto.builder()
                        .nome(criarInvestimentoDTO.nome())
                        .valorInicial(criarInvestimentoDTO.valorInicial())
                        .taxaJuros(criarInvestimentoDTO.taxaJuros())
                        .periodo(criarInvestimentoDTO.periodo())
                        .taxaPrefixada(11.0)
                        .ipca(6.0)
                        .build();
                yield tesouro;
            }

            default -> throw new IllegalArgumentException("Tipo de investimento inválido");
        };
        investimentosRepository.save(entity);
        return entity.getId();
    }
}
