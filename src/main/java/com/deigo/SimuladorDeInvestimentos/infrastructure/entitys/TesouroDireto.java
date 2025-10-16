package com.deigo.SimuladorDeInvestimentos.infrastructure.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
@DiscriminatorValue("TesouroDireto")

public class TesouroDireto extends Investimentos {

    @Column(name = "taxa_prefixada")
    private double taxaPrefixada;

    @Column(name = "ipca")
    private double ipca;

    @Override
    public BigDecimal calcularRendimento() {

        double taxaMensal = Math.pow(1 + (taxaPrefixada + ipca), 1.0 / 12) -1;

        double montante = getValorInicial() * Math.pow(1 + taxaMensal, getPeriodo());
        double rendimento = montante - getValorInicial();

        return BigDecimal.valueOf(rendimento).setScale(2, RoundingMode.HALF_UP);
    }
}
