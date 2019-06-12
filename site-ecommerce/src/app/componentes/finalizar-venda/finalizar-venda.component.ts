import { Component, OnInit, TemplateRef } from '@angular/core';
import { Usuario, Cliente, Pedido } from 'src/dominio';
import { Route, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Url } from 'src/app/utilidades/url';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-finalizar-venda',
  templateUrl: './finalizar-venda.component.html',
  styleUrls: ['./finalizar-venda.component.css']
})
export class FinalizarVendaComponent implements OnInit {

  urlCliente: string
  urlPedido: string
  usuario: Usuario
  cliente: any
  valorTotalVenda: any
  mensagemErro: string
  modalRef: BsModalRef
  
  formPedido: FormGroup

  constructor(private router: Router, private http: HttpClient, private fb: FormBuilder, private modalService: BsModalService) {
  }

  ngOnInit() {
    this.urlCliente = new Url().url.cliente
    this.urlPedido = new Url().url.pedido
    this.verificaUsuarioAutenticado()
    this.findClienteByIdUsuario(this.usuario[0].id)
    this.valorTotalVenda = JSON.parse(localStorage.getItem('valorTotalVenda'))

    this.formPedido = this.fb.group({
      endereco: ['', Validators.required],
      cartao: [''],
      cupomTroca: [''],
      ativaTemplate: ['']
    });

  }

  verificaUsuarioAutenticado() {
    if(localStorage.getItem('currentUser') != null) 
      this.usuario = JSON.parse(localStorage.getItem('currentUser'))
    else 
      this.router.navigate(['login'])
  }

  findClienteByIdUsuario(id) {
    this.http.post<Cliente>(this.urlCliente, {
      operacao:"consultar",
      usuario: {id: id}
    }).subscribe(
      (response) => {
        this.cliente = response
        this.retornaCliente(response)
        console.log(response)
      },
      (error) => { 
        console.log(error)
      }
    )
  }

  retornaCliente(cliente) {
    this.cliente = cliente
    return this.cliente
  }

  exibeCliente() {
    return this.cliente
  }

  salvarPedido(form: FormGroup, template: TemplateRef<any>, template1: TemplateRef<any>) {
    this.mensagemErro = ""
    if(form.value.cupomTroca && form.value.cupomTroca < this.valorTotalVenda) {
      this.mensagemErro = "Cupom de troca possui valor menor que o valor de venda"
    } else {
      if(form.valid) {
        const pedido = new Pedido()
        pedido.data = new Date(Date.now())
        pedido.valor = JSON.parse(localStorage.getItem('valorTotalVenda'))
        pedido.cartao = form.value.cartao
        pedido.cupomTroca = form.value.cupomTroca
        pedido.endereco = form.value.endereco
        pedido.itens = JSON.parse(localStorage.getItem('produtosCarrinho'))
        pedido.usuario = JSON.parse(localStorage.getItem('currentUser'))
        console.log(pedido)
        this.http.post<Pedido>(this.urlPedido+"/salvar", {
          data: new Date(Date.now()),
          valor: pedido.valor,
          cartao: {id: pedido.cartao ? pedido.cartao : null},
          cupomTroca: {id: pedido.cupomTroca ? pedido.cupomTroca : null},
          endereco: {id: pedido.endereco},
          itensPedido: pedido.itens,
          usuario: pedido.usuario[0]
        }).subscribe(
          (response) => {
            console.log(response)
            this.formPedido.get('ativaTemplate').setValue("ativo")
            localStorage.removeItem('produtosCarrinho')
            this.openModal(template)
          },
          (error) => {
            console.log(error)
          }
        )
      } else {
        this.mensagemErro = "Selecione um endereço e uma forma de pagamento para concluir o pedido"
      }
    }
  }

  fechaMensagemErro() {
    this.mensagemErro = null
  }
  
  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template)
  }

  abrirModal(template) {
    this.modalRef = this.modalService.show(template);
  }

  fecharModal() {
    this.modalRef.hide()
  }

  concluiPedido() {
    this.router.navigate(["/"])
    localStorage.removeItem('produtosCarrinho')
    localStorage.removeItem('valorTotalVenda')
  }

}
