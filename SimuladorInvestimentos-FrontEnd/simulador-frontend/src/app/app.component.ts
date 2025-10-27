import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Simulador de Investimentos';
  autor = 'Diego Prado'; 

  constructor(private router: Router) {}

  public navegarParaRegistrarInvestimento(): void {
    console.log('Navegando para Registro de Investimento...');
    this.router.navigate(['/registrar-investimento']);
  }

  public navegarParaCalcularRendimento(): void {
    console.log('Navegando para Cálculo de Rendimento...');
    this.router.navigate(['/calcular-rendimento']);
  }

  public navegarParaTodosInvestimentos(): void {
    console.log('Navegando para Todos Investimentos...');
    this.router.navigate(['/lista-investimentos']);
  }
}