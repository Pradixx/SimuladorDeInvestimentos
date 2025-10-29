import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { InvestimentoService, Investimento } from '../../services/investimento.service'; 

@Component({
  selector: 'app-lista-investimentos',
  standalone: true,
  imports: [CommonModule, CurrencyPipe],
  templateUrl: './lista-investimentos.html',
  styleUrls: ['./lista-investimentos.css']
})
export class ListaInvestimentosComponent implements OnInit {

  investimentos: Investimento[] = [];
  carregando: boolean = true;
  erro: string | null = null;

  constructor(private investimentoService: InvestimentoService) { }

  ngOnInit(): void {
    this.carregarInvestimentos();
  }

  carregarInvestimentos(): void {
    this.carregando = true;
    this.erro = null;
    
    this.investimentoService.listarTodos().subscribe({
      next: (dados) => {
        this.investimentos = dados;
        this.carregando = false;
        console.log('Investimentos carregados:', dados);
      },
      error: (e) => {
        this.erro = 'Não foi possível carregar os investimentos. Verifique se a API Java está rodando.';
        this.carregando = false;
        console.error('Erro ao carregar lista de investimentos:', e);
      }
    });
  }

  deletarInvestimento(id: string): void {
    if (confirm('Tem certeza que deseja deletar este investimento?')) {
      this.investimentoService.deletarPorId(id).subscribe({
        next: () => {
          alert('Investimento deletado com sucesso!');
          this.investimentos = this.investimentos.filter(inv => inv.id !== id);
        },
        error: (e) => {
          console.error('Erro ao deletar investimento:', e);
          alert(`Falha ao deletar: ${e.error?.mensagem || 'Erro de comunicação com a API.'}`);
        }
      });
    }
  }

  editarInvestimento(id: string): void {
    alert(`Funcionalidade de Edição (PUT ${id}) a ser implementada!`);
  }
}