import { Component, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { RegistrarInvestimento } from '../registrar-investimento/registrar-investimento';
import { CalcularRendimento } from '../calcular-rendimento/calcular-rendimento';
import { ListarInvestimentos } from '../listar-investimentos/listar-investimentos';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RegistrarInvestimento,
    CalcularRendimento,
    ListarInvestimentos
  ],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
})
export class Dashboard implements OnInit {
  abaAtiva: string = 'registrar';

  @ViewChild('outlet', { read: ViewContainerRef, static: true })
  outlet!: ViewContainerRef;

  constructor(private router: Router) {}

  ngOnInit() {
    this.selecionarAba(this.abaAtiva);
  }

  async selecionarAba(aba: string) {
    this.abaAtiva = aba;
    this.outlet.clear();

    try {
      if (aba === 'registrar') {
        const m = await import('../registrar-investimento/registrar-investimento');
        this.outlet.createComponent(m.RegistrarInvestimento);
      } else if (aba === 'calcular') {
        const m = await import('../calcular-rendimento/calcular-rendimento');
        this.outlet.createComponent(m.CalcularRendimento);
      } else if (aba === 'listar') {
        const m = await import('../listar-investimentos/listar-investimentos');
        this.outlet.createComponent(m.ListarInvestimentos);
      }
    } catch (err) {
      console.error('Erro ao carregar componente dinamicamente:', err);
      const el = document.createElement('div');
      el.textContent = 'Erro ao carregar a aba. Veja o console.';
      this.outlet.element.nativeElement.appendChild(el);
    }
  }

  voltarParaHome() {
    this.router.navigate(['/']);
  }
}
