package com.deigo.SimuladorDeInvestimentos.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue("CDB")

public class CDB extends Investimentos {

    @Column(name = "taxa_juros")
    private double taxaJuros;

    @Column(name = "cdi_percentual")
    private double cdiPercentual;

    @Override
    public BigDecimal calcularRendimento() {

        double taxaEfetiva = getTaxaJuros() * cdiPercentual;

        double montante = getValorInicial() * Math.pow(1 + taxaEfetiva, getPeriodo());
        double rendimento = montante - getValorInicial();

        return BigDecimal.valueOf(rendimento).setScale(2, RoundingMode.HALF_UP);
    }
}
