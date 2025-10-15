package com.deigo.SimuladorDeInvestimentos.infrastructure.repository;

import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Investimentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestimentosRepository extends JpaRepository<Investimentos, Integer> {
}
