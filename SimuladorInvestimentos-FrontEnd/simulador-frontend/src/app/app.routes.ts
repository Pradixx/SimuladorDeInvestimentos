import { Routes } from '@angular/router';
import { CalcularRendimentoComponent } from './components/calcular-rendimento/calcular-rendimento';
import { RegistrarInvestimentoComponent } from './components/registrar-investimento/registrar-investimento.component';
import { ListaInvestimentosComponent } from './components/lista-investimentos/lista-investimentos.component'; // <-- Importe o novo componente

export const routes: Routes = [
  {
    path: 'registrar-investimento',
    component: RegistrarInvestimentoComponent,
    title: 'Registrar Investimento',
  },
  {
    path: 'calcular-rendimento',
    component: CalcularRendimentoComponent,
    title: 'Calcular Rendimento',
  },
  {
    path: 'lista-investimentos', // Rota usada pelo botão do dashboard
    component: ListaInvestimentosComponent, // <-- Componente de listagem
    title: 'Todos Investimentos',
  },
  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: '**', redirectTo: '/' }, 
];