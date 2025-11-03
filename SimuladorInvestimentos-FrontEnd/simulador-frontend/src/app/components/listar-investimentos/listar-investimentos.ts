import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InvestimentosService, Investimento } from '../../services/investimento.service';

@Component({
  selector: 'app-listar-investimentos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './listar-investimentos.html',
  styleUrl: './listar-investimentos.css'
})
export class ListarInvestimentos implements OnInit {
  investimentos: Investimento[] = [];
  carregando = true;
  erroAoCarregar = false;
  mensagem = '';
  tipoMensagem: 'sucesso' | 'erro' = 'sucesso';

  constructor(private investimentosService: InvestimentosService) { }

  ngOnInit(): void {
    this.carregarInvestimentos();
  }

  carregarInvestimentos() {
    this.carregando = true;
    this.erroAoCarregar = false;
    this.mensagem = '';

    this.investimentosService.listarTodos().subscribe({
      next: (data) => {
        this.investimentos = data;
        this.carregando = false;
      },
      error: (erro) => {
        this.erroAoCarregar = true;
        this.mensagem = 'Erro ao carregar investimentos. Verifique a conexão com a API.';
        this.tipoMensagem = 'erro';
        this.carregando = false;
        console.error('Erro:', erro);
      }
    });
  }

  deletarInvestimento(id: string) {
    if (confirm('Tem certeza que deseja deletar este investimento?')) {
      this.investimentosService.deletarInvestimento(id).subscribe({
        next: () => {
          this.mensagem = 'Investimento deletado com sucesso!';
          this.tipoMensagem = 'sucesso';
          this.carregarInvestimentos(); // Recarrega a lista
        },
        error: (erro) => {
          this.mensagem = 'Erro ao deletar investimento. Verifique a conexão com a API.';
          this.tipoMensagem = 'erro';
          console.error('Erro:', erro);
        }
      });
    }
  }

  copiarId(id: string) {
    navigator.clipboard.writeText(id).then(() => {
      this.mensagem = 'ID copiado para a área de transferência!';
      this.tipoMensagem = 'sucesso';
      setTimeout(() => this.mensagem = '', 3000); // Limpa a mensagem após 3 segundos
    }).catch(err => {
      this.mensagem = 'Erro ao copiar ID.';
      this.tipoMensagem = 'erro';
      console.error('Erro ao copiar:', err);
    });
  }
}
