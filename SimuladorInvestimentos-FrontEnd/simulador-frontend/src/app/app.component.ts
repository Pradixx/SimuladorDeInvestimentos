import { Component } from '@angular/core';
import { InvestimentosComponent } from './investimentos/investimentos.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [InvestimentosComponent],
  template: `<app-investimentos></app-investimentos>`
})
export class AppComponent {}