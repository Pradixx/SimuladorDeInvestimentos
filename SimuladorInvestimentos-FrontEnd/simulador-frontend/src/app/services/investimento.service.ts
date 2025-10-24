import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

export interface Investimento {
  id?: string;
  nome: string;
  valorInicial: number;
  taxaJuros: number;
  periodo: number;
  tipo: string;
}

@Injectable({
  providedIn: 'root'
})
export class InvestimentosService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<Investimento[]> {
    return this.http.get<Investimento[]>(`${this.apiUrl}/todos`);
  }

  criar(invest: Investimento): Observable<any> {
    return this.http.post(this.apiUrl, invest);
  }

  calcularRendimento(id: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}/rendimento`);
  }

  deletar(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}?id=${id}`);
  }
}