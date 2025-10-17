package com.deigo.SimuladorDeInvestimentos.controller.DTO;

public record CriarInvestimentosDTO(String tipo, String nome, double valorInicial, double taxaJuros, int periodo) {
}
