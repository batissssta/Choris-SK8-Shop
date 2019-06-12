import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Endereco, Cliente } from 'src/dominio';
import { HttpClient } from '@angular/common/http';
import { Url } from 'src/app/utilidades/url';

@Component({
  selector: 'app-gerenciar-enderecos',
  templateUrl: './gerenciar-enderecos.component.html',
  styleUrls: ['./gerenciar-enderecos.component.css']
})
export class GerenciarEnderecosComponent implements OnInit {
  formEndereco: FormGroup
  endereco: any
  enderecos: any
  cliente: any
  usuario: any
  urlCliente: string
  urlEndereco: string
  operacao: string
  mensagemErro: string
  constructor(private fb: FormBuilder, private http: HttpClient) { }

  ngOnInit() {
    this.operacao = 'salvar'
    this.urlCliente = new Url().url.cliente
    this.urlEndereco = new Url().url.endereco
    
    this.formEndereco = this.fb.group({
      logradouro: ['', Validators.required],
      numero: ['', Validators.required],
      cep: ['', Validators.required],
      tipoLogradouro: ['Selecione...', Validators.required],
      tipoResidencia: ['Selecione...', Validators.required],
      bairro: ['', Validators.required],
      cidade: ['', Validators.required],
      estado: ['Selecione...', Validators.required],
      pais: ['Selecione...', Validators.required],
      tipoEndereco: ['Selecione...', Validators.required],
      idEndereco: []
    });

    this.verificaUsuarioAutenticado()
    this.findClienteByIdUsuario(this.usuario[0].id)

  }

  onSubmit(form: FormGroup) {
    
    if(form.valid != true) {
      console.log(form)
      this.mensagemErro = "Algum dos campos não foi preenchido, preencha todos os campos para concluir o cadastro"
    }
      
    else {
      const endereco = new Endereco()
      endereco.id = this.operacao == 'salvar' ? null : form.value.idEndereco
      endereco.logradouro = form.value.logradouro
      endereco.numero = form.value.numero
      endereco.cep = form.value.cep
      endereco.tipoLogradouro = form.value.tipoLogradouro
      endereco.tipoResidencia = form.value.tipoResidencia
      endereco.bairro = form.value.bairro
      endereco.cidade = form.value.cidade
      endereco.estado = form.value.estado
      endereco.pais = form.value.pais
      endereco.tipoEndereco = form.value.tipoEndereco
      endereco.cliente = this.cliente[0]
      console.log(endereco)
     
      // request
      this.http.post(this.urlEndereco, {
        id: endereco.id,
        logradouro: endereco.logradouro,
        numero: endereco.numero,
        cep: endereco.cep,
        tipoLogradouro: endereco.tipoLogradouro,
        tipoResidencia: endereco.tipoResidencia,
        bairro: endereco.bairro,
        cidade: endereco.cidade,
        estado: endereco.estado,
        pais: endereco.pais,
        tipoEndereco: endereco.tipoEndereco,
        cliente: {id: endereco.cliente.id},
        operacao: this.operacao
      }).subscribe(
        (response) => {
          console.log(response)
          this.formEndereco.reset()
        },
        (error) => {
          console.log(error.error.text)
          this.mensagemErro = error.error.text
        }
      )
    }
    
  }


  verificaUsuarioAutenticado() {
    if(localStorage.getItem('currentUser') != null) 
      this.usuario = JSON.parse(localStorage.getItem('currentUser'))
  }

  findClienteByIdUsuario(id) {
    this.http.post<Cliente>(this.urlCliente, {
      operacao:"consultar",
      usuario: {id: id}
    }).subscribe(
      (response) => {
        this.cliente = response
        this.adicionaEndereco(response)
        console.log(response)
      },
      (error) => { 
        console.log(error)
      }
    )
  }
  
  retornaCliente(cliente) {
    this.cliente = cliente
    return this.cliente.enderecos
  }

  editar(endereco: Endereco) {
    this.formEndereco.get('logradouro').setValue(endereco.logradouro)
    this.formEndereco.get('numero').setValue(endereco.numero)
    this.formEndereco.get('cep').setValue(endereco.cep)
    this.formEndereco.get('tipoLogradouro').setValue(endereco.tipoLogradouro)
    this.formEndereco.get('tipoResidencia').setValue(endereco.tipoResidencia)
    this.formEndereco.get('bairro').setValue(endereco.bairro)
    this.formEndereco.get('cidade').setValue(endereco.cidade)
    this.formEndereco.get('estado').setValue(endereco.estado)
    this.formEndereco.get('pais').setValue(endereco.pais)
    this.formEndereco.get('tipoEndereco').setValue(endereco.tipoEndereco)
    this.formEndereco.get('idEndereco').setValue(endereco.id)
    this.operacao = 'alterar'
    console.log(endereco)
  }

  adicionaEndereco(cliente) {
    this.enderecos = cliente[0].enderecos
    console.log(this.enderecos)
  }

  cancelarEdicao(form: FormGroup){
    form.reset()
    this.operacao = 'salvar'
  }

  excluir(id: number) {
    this.http.post(this.urlEndereco, {
      id: id, 
      operacao: 'excluir'
    }).subscribe(
        (response) => {
          console.log(response)
        },
        (error) => {
          console.log(error.error.text)
        }
      )
  }


  fechaMensagemErro() {
    this.mensagemErro = null
  }

}
