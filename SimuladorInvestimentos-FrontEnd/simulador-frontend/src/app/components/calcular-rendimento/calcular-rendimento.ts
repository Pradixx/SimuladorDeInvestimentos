import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration } from 'chart.js';
import { InvestimentosService } from '../../services/investimento.service';

import {
  Chart,
  LineElement,
  PointElement,
  LineController,
  CategoryScale,
  LinearScale,
  Title,
  Tooltip,
  Legend,
  registerables,
  Filler 
} from 'chart.js';

Chart.register(
  LineElement,
  PointElement,
  LineController,
  CategoryScale,
  LinearScale,
  Title,
  Tooltip,
  Legend
);

@Component({
  selector: 'app-calcular-rendimento',
  standalone: true,
  imports: [CommonModule, FormsModule, BaseChartDirective],
  templateUrl: './calcular-rendimento.html',
  styleUrl: './calcular-rendimento.css',
})
export class CalcularRendimento implements OnInit {
  @ViewChild(BaseChartDirective) chart!: BaseChartDirective;

  investimentoId = '';
  rendimento: number | null = null;
  mensagem = '';
  tipoMensagem: 'sucesso' | 'erro' = 'sucesso';
  carregando = false;
  mostrarGrafico = false;

  chartConfig: ChartConfiguration<'line'> = {
    type: 'line',
    data: {
      labels: [],
      datasets: [
        {
          label: 'Rendimento (R$)',
          data: [],
          borderColor: '#ff6b6b',
          backgroundColor: 'rgba(255, 107, 107, 0.1)',
          borderWidth: 2,
          fill: true,
          tension: 0.4,
          pointBackgroundColor: '#ff6b6c',
          pointBorderColor: '#ffffff',
          pointRadius: 5,
          pointHoverRadius: 7
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: true,
      plugins: {
        legend: {
          labels: {
            color: '#ffffff',
            font: {
              size: 12
            }
          }
        }
      },
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            color: 'rgba(255, 255, 255, 0.7)',
            font: {
              size: 11
            }
          },
          grid: {
            color: 'rgba(255, 107, 107, 0.1)'
          }
        },
        x: {
          ticks: {
            color: 'rgba(255, 255, 255, 0.7)',
            font: {
              size: 11
            }
          },
          grid: {
            color: 'rgba(255, 107, 107, 0.1)'
          }
        }
      }
    }
  };

  constructor(private investimentosService: InvestimentosService) {
    Chart.register(...registerables); 
    Chart.register(Filler);
  }

  ngOnInit() {}

  calcularRendimento() {
    if (!this.investimentoId.trim()) {
      this.mensagem = 'Por favor, informe o ID do investimento.';
      this.tipoMensagem = 'erro';
      return;
    }

    this.carregando = true;
    this.mensagem = '';
    this.rendimento = null;
    this.mostrarGrafico = false;

    this.investimentosService.calcularRendimento(this.investimentoId).subscribe({
      next: (resposta) => {
        this.rendimento = resposta.rendimento;
        this.mensagem = resposta.mensagem;
        this.tipoMensagem = 'sucesso';
        this.gerarGrafico();
        this.carregando = false;
      },
      error: (erro) => {
        this.mensagem = 'Erro ao calcular rendimento. Verifique o ID e a conexão com a API.';
        this.tipoMensagem = 'erro';
        console.error('Erro:', erro);
        this.carregando = false;
      }
    });
  }

  gerarGrafico() {
    if (this.rendimento === null) return;

    // Simular dados de rendimento ao longo do período (exemplo: 12 meses)
    const periodos = 12;
    const labels: string[] = [];
    const dados: number[] = [];

    for (let i = 0; i <= periodos; i++) {
      labels.push(`Mês ${i}`);
      // Simular crescimento linear do rendimento
      dados.push((this.rendimento / periodos) * i);
    }

    this.chartConfig.data.labels = labels;
    this.chartConfig.data.datasets[0].data = dados;

    this.mostrarGrafico = true;

    if (this.chart) {
      this.chart.update();
    }
  }

  limpar() {
    this.investimentoId = '';
    this.rendimento = null;
    this.mensagem = '';
    this.mostrarGrafico = false;
  }
}
