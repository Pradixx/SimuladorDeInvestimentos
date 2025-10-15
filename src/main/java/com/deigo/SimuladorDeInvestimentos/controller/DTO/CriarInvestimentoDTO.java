package com.deigo.SimuladorDeInvestimentos.controller.DTO;

public record CriarInvestimentoDTO(String tipo, String nome, double valorInicial, double taxaJuros, int periodo) {
}
