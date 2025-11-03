import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { InvestimentosService, CriarInvestimentoDTO } from '../../services/investimento.service';

@Component({
  selector: 'app-registrar-investimento',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './registrar-investimento.html',
  styleUrl: './registrar-investimento.css'
})
export class RegistrarInvestimento implements OnInit {
  formInvestimento!: FormGroup;
  tiposInvestimento = ['CDB', 'Poupanca', 'TesouroDireto'];
  mensagem = '';
  tipoMensagem: 'sucesso' | 'erro' = 'sucesso';

  constructor(
    private fb: FormBuilder,
    private investimentosService: InvestimentosService
  ) { }

  ngOnInit(): void {
    this.formInvestimento = this.fb.group({
      tipo: [this.tiposInvestimento[0], Validators.required],
      nome: ['', Validators.required],
      valorInicial: ['', [Validators.required, Validators.min(0.01)]],
      taxaJuros: ['', [Validators.required, Validators.min(0)]],
      periodo: ['', [Validators.required, Validators.min(1)]]
    });
  }

  registrar() {
    if (this.formInvestimento.invalid) {
      this.mensagem = 'Por favor, preencha todos os campos corretamente.';
      this.tipoMensagem = 'erro';
      return;
    }

    const dto: CriarInvestimentoDTO = this.formInvestimento.value;

    this.investimentosService.criarInvestimento(dto).subscribe({
      next: (resposta) => {
        this.mensagem = `Sucesso! ${resposta.mensagem} (ID: ${resposta.id})`;
        this.tipoMensagem = 'sucesso';
        this.limpar();
      },
      error: (erro) => {
        this.mensagem = 'Erro ao registrar investimento. Verifique a conexão com a API.';
        this.tipoMensagem = 'erro';
        console.error('Erro:', erro);
      }
    });
  }

  limpar() {
    this.formInvestimento.reset({
      tipo: this.tiposInvestimento[0],
      nome: '',
      valorInicial: '',
      taxaJuros: '',
      periodo: ''
    });
    this.mensagem = '';
  }
}
