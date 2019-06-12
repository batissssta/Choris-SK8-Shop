import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Cliente, Endereco, Telefone, Cartao } from '../../../../dominio';
import { Url } from '../../../utilidades/url' 
@Component({
  selector: 'app-formulario-cadastro-cliente-servlet',
  templateUrl: './formulario-cadastro-cliente-servlet.component.html',
  styleUrls: ['./formulario-cadastro-cliente-servlet.component.css']
})
export class FormularioCadastroClienteServletComponent implements OnInit {
  url: string
  formCliente: FormGroup;
  clientes: Cliente[]
  mensagemErro: string
  operacao: string
  usuario: any
  constructor(private http: HttpClient, private fb: FormBuilder) {
  }

  ngOnInit() {
    this.operacao = 'salvar'
    this.url = new Url().url.cliente
    this.formCliente = this.fb.group({
      id: [''],
      nome: ['', Validators.required],
      genero: ['Selecione...', Validators.required],
      dataNascimento: ['', Validators.required],
      cpf: ['', Validators.required],
      email: ['', Validators.required],
      senha: ['', Validators.required],
      confirmarSenha: ['', Validators.required],
      numeroTelefone: ['', Validators.required],
      idTelefone: [''],
      operacao: ['']
    });

    this.buscarClientes()
    this.usuario = JSON.parse(localStorage.getItem('currentUser'))
    console.log(this.usuario)
  }

  buscarClientes() {
    this.clientes = undefined
    this.http.post<Cliente[]>(this.url, {
      operacao:"consultar"
    }).subscribe(
      (response) => {
        this.clientes = response
        console.log(response)
      },
      (error) => { 
        console.log(error)
      }
    )
  }

  onSubmit(form: FormGroup) {
    this.mensagemErro = undefined

    if(form.valid != true) {
      this.mensagemErro = "Algum dos campos não foi preenchido, preencha todos os campos para concluir o cadastro"
      console.log(form)
    }
      
    else {
      const cliente = new Cliente
      cliente.id = this.operacao == 'salvar' ? null : form.value.id
      cliente.nome = form.value.nome,
      cliente.genero = form.value.genero,
      cliente.dataNascimento = form.value.dataNascimento,
      cliente.cpf = form.value.cpf,
      cliente.email = form.value.email,
      cliente.senha = form.value.senha,
      cliente.confirmarSenha = form.value.confirmarSenha
      
      const telefone = new Telefone
      telefone.numero = form.value.numeroTelefone
      telefone.id = this.operacao == 'salvar' ? null : form.value.idTelefone

      console.log(telefone)
      cliente.telefones = []
      cliente.telefones[0] = telefone

      cliente.status = "ATIVO"

      console.log(cliente)
     
      // request
      this.http.post(this.url, {
        id: cliente.id,
        nome: cliente.nome,
        genero: cliente.genero,
        dataNascimento: cliente.dataNascimento,
        cpf: cliente.cpf,
        email: cliente.email,
        senha: cliente.senha,
        status: cliente.status,
        confirmarSenha: cliente.confirmarSenha,
        telefones: cliente.telefones,
        operacao : this.operacao
      }).subscribe(
        (response) => {
          console.log(response)
          //this.buscarClientes()
          this.formCliente.reset()
        },
        (error) => {
          console.log(error.error.text)
          this.mensagemErro = error.error.text;
        }
      )
    }
    
  }

  editar(cliente: Cliente) {
    this.operacao = "alterar"
    this.formCliente.get('id').setValue(cliente.id)
    this.formCliente.get('nome').setValue(cliente.nome)
    this.formCliente.get('genero').setValue(cliente.genero)
    this.formCliente.get('dataNascimento').setValue(this.formatDate(cliente.dataNascimento))
    this.formCliente.get('cpf').setValue(cliente.cpf)
    this.formCliente.get('email').setValue(cliente.email)
    this.formCliente.get('senha').setValue(cliente.senha)
    this.formCliente.get('confirmarSenha').setValue(cliente.senha)

    this.formCliente.get('numeroTelefone').setValue(cliente.telefones[0].numero)
    this.formCliente.get('idTelefone').setValue(cliente.telefones[0].id)

  }

  excluir(id: number) {
    this.http.post(this.url, {
      id: id, 
      operacao: 'excluir'
    }).subscribe(
        (response) => {
          console.log(response)
        },
        (error) => {
          console.log(error.error.text)
          this.mensagemErro = error.error.text;
        }
      )
    this.buscarClientes()
  }


  formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}


fechaMensagemErro() {
  this.mensagemErro = null
}

}
