package com.deigo.SimuladorDeInvestimentos.infrastructure.entitys;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public abstract class Investimentos {

    private String nome;

    private double valorInicial;

    private double taxaJuros;

    private int periodo;
}
