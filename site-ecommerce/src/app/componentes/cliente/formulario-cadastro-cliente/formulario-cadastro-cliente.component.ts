import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Cliente } from '../../../../dominio';

@Component({
  selector: 'app-formulario-cadastro-cliente',
  templateUrl: './formulario-cadastro-cliente.component.html',
  styleUrls: ['./formulario-cadastro-cliente.component.css']
})
export class FormularioCadastroClienteComponent implements OnInit {
  formCliente: FormGroup;
  clientes: Cliente[]
  mensagemErro: string
  operacao: string
  constructor(private http: HttpClient, private fb: FormBuilder) {
  }

  ngOnInit() {
    let responseArray
    this.formCliente = this.fb.group({
      id: [''],
      nome: ['', Validators.required],
      genero: ['', Validators.required],
      dataNascimento: ['', Validators.required],
      cpf: ['', Validators.required],
      email: ['', [Validators.required]],
      senha: ['', [Validators.required]]
    });

    this.buscarClientes()

  }

  buscarClientes() {
    this.clientes = undefined
    this.http.get<Cliente[]>(`http://localhost:8080/clientes`).subscribe(
      (response) => {
        this.clientes = response
        console.log(this.clientes)
      },
      (error) => {
        console.log(error)
      }
    )
  }

  onSubmit(form: FormGroup) {
    this.mensagemErro = undefined

    if(form.valid != true) 
      this.mensagemErro = "Algum dos campos não foi preenchido"
    else {
      const cliente = new Cliente
      cliente.nome = form.value.nome,
      cliente.genero = form.value.genero,
      cliente.dataNascimento = form.value.dataNascimento,
      cliente.cpf = form.value.cpf,
      cliente.email = form.value.email,
      cliente.senha = form.value.senha,
      cliente.status = "ATIVO"
     
      // request
      var url
      if(this.operacao == "edicao")
        url = `http://localhost:8080/clientes/editar/${form.value.id}`
      else 
        url = `http://localhost:8080/clientes/salvar`
      this.http.post(url, 
      {
        nome: cliente.nome,
        genero: cliente.genero,
        dataNascimento: cliente.dataNascimento,
        cpf: cliente.cpf,
        email: cliente.email,
        senha: cliente.senha,
        status: cliente.status
      }).subscribe(
        (response) => {
          console.log(response.toString())
        },
        (error) => {
          console.log(error.error.text)
          this.mensagemErro = error.error.text;
        }
      )
      this.operacao = undefined
    }
    
  }

  editar(cliente: Cliente) {
    this.operacao = "edicao"
    this.formCliente.get('id').setValue(cliente.id)
    this.formCliente.get('nome').setValue(cliente.nome)
    this.formCliente.get('genero').setValue(cliente.genero)
    this.formCliente.get('dataNascimento').setValue(cliente.dataNascimento)
    this.formCliente.get('cpf').setValue(cliente.cpf)
    this.formCliente.get('email').setValue(cliente.email)
    this.formCliente.get('senha').setValue(cliente.senha)
  }

  excluir(id: number) {
    this.http.get(`http://localhost:8080/clientes/excluir/${id}`).subscribe(
        (response) => {
          console.log(response)
          this.buscarClientes()
        },
        (error) => {
          console.log(error.error.text)
          this.mensagemErro = error.error.text;
        }
      )
  }


}
