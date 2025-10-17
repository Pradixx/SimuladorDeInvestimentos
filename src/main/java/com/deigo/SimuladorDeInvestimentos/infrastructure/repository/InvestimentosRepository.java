package com.deigo.SimuladorDeInvestimentos.infrastructure.repository;

import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Investimentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvestimentosRepository extends JpaRepository<Investimentos, Integer> {
    <T> ScopedValue<T> findById(UUID id);

    void deleteById(UUID id);
}
