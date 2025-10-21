package com.deigo.SimuladorDeInvestimentos.controller;

import com.deigo.SimuladorDeInvestimentos.controller.DTO.CriarInvestimentosDTO;
import com.deigo.SimuladorDeInvestimentos.infrastructure.entitys.Investimentos;
import com.deigo.SimuladorDeInvestimentos.service.InvestimentosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/investimentos")
@RequiredArgsConstructor
public class InvestimentosController {

    private final InvestimentosService investimentosService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> criar(@RequestBody CriarInvestimentosDTO dto) {
        UUID id = investimentosService.criarInvestimento(dto);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("id", id);
        resposta.put("mensagem", "Investimento criado com sucesso!");

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarInvestimentoPorId (@PathVariable UUID id) {
        return ResponseEntity.ok(investimentosService.buscarInvestimentosPeloId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarInvestimentoPorId (@PathVariable UUID id) {
        investimentosService.deletarInvestimentoPeloId(id);
        return ResponseEntity.ok().build();
    }

    public record SucessoDTO(UUID id, String mensagem) {}

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarInvestimentoPorId (@PathVariable UUID id, @RequestBody CriarInvestimentosDTO criarInvestimentosDTO) {
        investimentosService.atualizarInvestimentos(id, criarInvestimentosDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/rendimento")
    public ResponseEntity<Map<String, Object>> calcularRendimento(@PathVariable UUID id) {
        BigDecimal rendimento = investimentosService.calcularRendimento(id);

        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("rendimento", rendimento);
        response.put("mensagem", "Rendimento calculado com sucesso!");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Investimentos>> listarTodos() {
        return ResponseEntity.ok(investimentosService.listarTodosInvestimentos());
    }
}
