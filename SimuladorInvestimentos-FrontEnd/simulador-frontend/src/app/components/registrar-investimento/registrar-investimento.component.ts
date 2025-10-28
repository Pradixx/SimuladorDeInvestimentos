import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InvestimentoService, CriarInvestimentoPayload } from '../../services/investimento.service'; // <-- Importe o Service

@Component({
  selector: 'app-registrar-investimento',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registrar-investimento.html',
  styleUrls: ['./registrar-investimento.css']
})
export class RegistrarInvestimentoComponent implements OnInit {

  novoInvestimento: CriarInvestimentoPayload = {
    nome: '',
    tipo: 'Renda Fixa',
    valorInicial: 0,
    aporteMensal: 0,
    taxaAnual: 0,
    prazoMeses: 12
  };

  tiposInvestimento: string[] = ['Renda Fixa', 'Ações', 'Fundos Imobiliários', 'Tesouro Direto', 'Criptoativos'];
  
  constructor(private investimentoService: InvestimentoService) { }

  ngOnInit(): void { }

  salvarInvestimento(): void {
    if (this.validarFormulario()) {
      this.investimentoService.criarInvestimento(this.novoInvestimento).subscribe({
        next: (resposta) => {
          alert(`Sucesso! ID: ${resposta.id} - Mensagem: ${resposta.mensagem}`);
          this.resetarFormulario();
        },
        error: (erro) => {
          console.error('Erro ao registrar investimento:', erro);
          alert(`Erro ao registrar: ${erro.message || 'Verifique a conexão com o backend Java.'}`);
        }
      });
    } else {
      alert('Por favor, preencha todos os campos obrigatórios corretamente.');
    }
  }

  validarFormulario(): boolean {
    return this.novoInvestimento.nome.trim() !== '' &&
           this.novoInvestimento.valorInicial >= 0 &&
           this.novoInvestimento.prazoMeses > 0 &&
           this.novoInvestimento.taxaAnual >= 0;
  }

  resetarFormulario(): void {
    this.novoInvestimento = {
      nome: '',
      tipo: 'Renda Fixa',
      valorInicial: 0,
      aporteMensal: 0,
      taxaAnual: 0,
      prazoMeses: 12
    };
  }
}