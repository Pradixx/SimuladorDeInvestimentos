import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CriarInvestimentoPayload {
  nome: string;
  tipo: string;
  valorInicial: number;
  aporteMensal: number;
  taxaAnual: number;
  prazoMeses: number;
}

export interface Investimento {
  id: string; 
  nome: string;
  tipo: string;
  valorInicial: number;
  aporteMensal: number;
  taxaAnual: number;
  prazoMeses: number;
}

export interface ApiResponse {
  id: string;
  mensagem?: string;
  rendimento?: number;
}

@Injectable({
  providedIn: 'root'
})
export class InvestimentoService {
  private apiUrl = 'http://localhost:8080/investimentos';

  constructor(private http: HttpClient) { }

  criarInvestimento(payload: CriarInvestimentoPayload): Observable<ApiResponse> {
    console.log('Enviando dados para o Java:', payload);
    return this.http.post<ApiResponse>(this.apiUrl, payload);
  }

  listarTodos(): Observable<Investimento[]> {
    return this.http.get<Investimento[]>(`${this.apiUrl}/todos`);
  }

  buscarPorId(id: string): Observable<Investimento> {
    return this.http.get<Investimento>(`${this.apiUrl}/${id}`);
  }

  atualizarInvestimento(id: string, payload: CriarInvestimentoPayload): Observable<void> {
    
    return this.http.put<void>(`${this.apiUrl}/${id}`, payload);
  }

  deletarPorId(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  calcularRendimento(id: string): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.apiUrl}/${id}/rendimento`);
  }
}