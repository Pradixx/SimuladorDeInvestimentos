import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/investimentos';

export interface CriarInvestimentoDTO {
  tipo: string;
  nome: string;
  valorInicial: number;
  taxaJuros: number;
  periodo: number;

  cdiPercentual?: number; 
  selicAnual?: number; 
  taxaPrefixada?: number; 
  ipca?: number;
}

export interface Investimento {
  id: string;
  nome: string;
  valorInicial: number;
  taxaJuros: number;
  periodo: number;
  tipo: string;

  tipo_investimento: string;

  cdiPercentual?: number;
  selicAnual?: number;
  taxaPrefixada?: number;
  ipca?: number;
}

export interface RespostaCriacao {
  id: string;
  mensagem: string;
}

export interface RespostaRendimento {
  id: string;
  rendimento: number;
  mensagem: string;
}

@Injectable({
  providedIn: 'root'
})
export class InvestimentosService {

  constructor(private http: HttpClient ) { }

  criarInvestimento(dto: CriarInvestimentoDTO): Observable<RespostaCriacao> {
    return this.http.post<RespostaCriacao>(API_URL, dto );
  }

  listarTodos(): Observable<Investimento[]> {
    return this.http.get<Investimento[]>(`${API_URL}/todos` );
  }

  calcularRendimento(id: string): Observable<RespostaRendimento> {
    return this.http.get<RespostaRendimento>(`${API_URL}/${id}/rendimento` );
  }

  deletarInvestimento(id: string): Observable<void> {
    return this.http.delete<void>(`${API_URL}/${id}` );
  }
}
