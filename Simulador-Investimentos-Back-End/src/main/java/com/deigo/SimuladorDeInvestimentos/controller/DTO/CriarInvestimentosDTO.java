package com.deigo.SimuladorDeInvestimentos.controller.DTO;

public record CriarInvestimentosDTO(String tipo,
                                    String nome,
                                    Double valorInicial,
                                    Double taxaJuros,
                                    Integer periodo,
                                    Double taxaPrefixada,
                                    Double ipca,
                                    Double cdiPercentual,
                                    Double selicAnual
) {
}
