import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Url } from 'src/app/utilidades/url';
import { Cartao, Cliente } from 'src/dominio';

@Component({
  selector: 'app-gerenciar-cartoes',
  templateUrl: './gerenciar-cartoes.component.html',
  styleUrls: ['./gerenciar-cartoes.component.css']
})
export class GerenciarCartoesComponent implements OnInit {
  formCartao: FormGroup
  cartao: any
  cartoes: any
  cliente: any
  usuario: any
  urlCliente: string
  urlCartao: string
  operacao: string
  mensagemErro: string
  constructor(private fb: FormBuilder, private http: HttpClient) { }

  ngOnInit() {
    this.operacao = 'salvar'
    this.urlCliente = new Url().url.cliente
    this.urlCartao = new Url().url.cartao
    
    this.formCartao = this.fb.group({
      numeroCartaoCredito: ['', Validators.required],
      bandeiraCartaoCredito: ['Selecione...', Validators.required],
      nomeImpressoCartaoCredito: ['', Validators.required],
      codigoSegurancaCartao: ['', Validators.required],
      validadeCartaoCredito: ['', Validators.required],
      cartaoPreferido: ['', Validators.required],
      idCartao: []
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
      const cartao = new Cartao()
      cartao.id = this.operacao == 'salvar' ? null : form.value.idCartao
      cartao.numeroCartao = form.value.numeroCartaoCredito
      cartao.bandeiraCartao = form.value.bandeiraCartaoCredito
      cartao.nomeImpresso = form.value.nomeImpressoCartaoCredito
      cartao.codigoSeguranca = form.value.codigoSegurancaCartao
      cartao.validade = form.value.validadeCartaoCredito
      cartao.preferido = form.value.cartaoPreferido
      cartao.cliente = this.cliente[0]
      console.log(cartao)
     
      // request
      this.http.post(this.urlCartao, {
        id: cartao.id,
        numeroCartao: cartao.numeroCartao,
        bandeiraCartao: cartao.bandeiraCartao,
        nomeImpresso: cartao.nomeImpresso,
        codigoSeguranca: cartao.codigoSeguranca,
        validade: cartao.validade,
        preferido: cartao.preferido,
        cliente: {id: cartao.cliente.id},
        operacao: this.operacao
      }).subscribe(
        (response) => {
          console.log(response)
          this.formCartao.reset()
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
        this.adicionaCartao(response)
        console.log(response)
      },
      (error) => { 
        console.log(error)
      }
    )
  }
  
  retornaCliente(cliente) {
    this.cliente = cliente
    return this.cliente.cartaos
  }


  adicionaCartao(cliente) {
    this.cartoes = cliente[0].cartoes
    console.log(this.cartoes)
  }

  editar(cartao: any) {
    this.formCartao.get('numeroCartaoCredito').setValue(cartao.numeroCartao)
    this.formCartao.get('bandeiraCartaoCredito').setValue(cartao.bandeiraCartao)
    this.formCartao.get('nomeImpressoCartaoCredito').setValue(cartao.nomeImpresso)
    this.formCartao.get('codigoSegurancaCartao').setValue(cartao.codigoSeguranca)
    this.formCartao.get('idCartao').setValue(cartao.id)
    this.formCartao.get('validadeCartaoCredito').setValue(this.formatDate(cartao.dataValidade))
    this.formCartao.get('cartaoPreferido').setValue(cartao.preferido)
    this.formCartao.get('idCartao').setValue(cartao.id)
    this.operacao = 'alterar'
    console.log(cartao)
  }

  cancelarEdicao(form: FormGroup){
    form.reset()
    this.operacao = 'salvar'
  }

  excluir(id: number) {
    this.http.post(this.urlCartao, {
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
