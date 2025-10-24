package com.deigo.SimuladorDeInvestimentos.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "investimentos")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_investimento")

public abstract class Investimentos {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor_inicial")
    private double valorInicial;

    @Column(name = "taxa_juros")
    private double taxaJuros;

    @Column(name = "periodo")
    private int periodo;

    @Column(name = "tipo")
    private String tipo;

    public abstract BigDecimal calcularRendimento ();
}
