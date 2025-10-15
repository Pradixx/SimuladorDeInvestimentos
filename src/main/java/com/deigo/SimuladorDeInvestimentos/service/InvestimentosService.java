package com.deigo.SimuladorDeInvestimentos.service;

import com.deigo.SimuladorDeInvestimentos.controller.DTO.CriarInvestimentoDTO;
import com.deigo.SimuladorDeInvestimentos.infrastructure.repository.InvestimentosRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class InvestimentosService {

    private final InvestimentosRepository investimentosRepository;

    public InvestimentosService(InvestimentosRepository investimentosRepository) {
        this.investimentosRepository = investimentosRepository;
    }


    public UUID criarInvestimentos(CriarInvestimentoDTO criarInvestimentoDTO) {
        return null;
    }
}
