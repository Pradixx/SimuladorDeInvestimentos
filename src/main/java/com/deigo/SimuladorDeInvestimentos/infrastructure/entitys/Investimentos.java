package com.deigo.SimuladorDeInvestimentos.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "investimentos")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_investimento")

public abstract class Investimentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor-inicial")
    private double valorInicial;

    @Column(name = "taxa-juros")
    private double taxaJuros;

    @Column(name = "periodo")
    private int periodo;

    public abstract BigDecimal calcularRendimento ();
}
