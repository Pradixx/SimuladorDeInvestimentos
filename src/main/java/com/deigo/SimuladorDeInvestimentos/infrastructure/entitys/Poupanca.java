package com.deigo.SimuladorDeInvestimentos.infrastructure.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DiscriminatorValue("Poupanca")

public class Poupanca extends Investimentos{

    @Column(name = "selic_anual")
    private double selicAnual;

    @Override
    public BigDecimal calcularRendimento() {

        double taxaMensal;

        if (selicAnual <= 0.085) {
            taxaMensal = (selicAnual * 0.70) / 12;
        } else {
            taxaMensal = 0.005;
        }

        double montante = getValorInicial() * Math.pow(1 + taxaMensal, getPeriodo());
        double rendimento = montante - getValorInicial();

        return BigDecimal.valueOf(rendimento).setScale(2, RoundingMode.HALF_UP);
    }
}
