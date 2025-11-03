import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
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
    ListarInvestimentos,
    RouterLink
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  abaAtiva: string = 'registrar';

  constructor(private router: Router) {}

  ngOnInit() {}

  selecionarAba(aba: string) {
    this.abaAtiva = aba;
  }

  voltarParaHome() {
    this.router.navigate(['/']);
  }
}
