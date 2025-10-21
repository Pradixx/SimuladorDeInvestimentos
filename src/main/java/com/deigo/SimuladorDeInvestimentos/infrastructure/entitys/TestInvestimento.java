package com.deigo.SimuladorDeInvestimentos.infrastructure.entitys;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("TEST")
public class TestInvestimento extends Investimentos {
    @Override
    public BigDecimal calcularRendimento() {
        return BigDecimal.TEN;
    }
}