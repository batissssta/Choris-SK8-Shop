import { Component, OnInit, TemplateRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Url } from 'src/app/utilidades/url';
import { ConstantesStatusPedido } from 'src/app/utilidades/constantesStatusPedido';
import { Pedido, Cliente } from 'src/dominio';
import { Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-gerenciar-pedidos',
  templateUrl: './gerenciar-pedidos.component.html',
  styleUrls: ['./gerenciar-pedidos.component.css']
})
export class GerenciarPedidosComponent implements OnInit {

  pedidos: any
  itensPedido: any = []
  urlPedido: string
  urlTroca: string
  urlCupomTroca: string
  urlCliente: string
  usuario: any
  statusPedidoConstantes: any
  statusPedido: any
  troca: any
  modalRef: BsModalRef
  pedidoOperacao: any
  cliente: any

  constructor(private http: HttpClient, private router: Router, private modalService: BsModalService) { }

  ngOnInit() {
    this.urlPedido = new Url().url.pedido
    this.urlTroca = new Url().url.troca
    this.urlCliente = new Url().url.cliente
    this.urlCupomTroca = new Url().url.cupomTroca
    this.usuario = JSON.parse(localStorage.getItem('currentUser'))
    this.verificaUsuarioLogado()
    this.buscarPedidos()
    this.statusPedidoConstantes = new ConstantesStatusPedido().statusPedido;
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

  verificaUsuarioLogado() {
    console.log(JSON.parse(localStorage.getItem('currentUser')))
    if(this.usuario == null || this.usuario[0].permissao != 'administrador') {
      this.router.navigate(['/'])
    }
  }

  buscarTrocaPorPedido(id) {
    this.http.post<any>(this.urlTroca+"/buscarPorIdPedido/"+id, {
      id: id
    }
    ).subscribe(
    (response) => {
      this.pedidoOperacao = response
      console.log(response)
      this.findClienteByIdUsuario(response.pedido.usuario.id)
    },
    (error) => {
      console.log(error)
    }
  )
  }
  
  openModal(template: TemplateRef<any>, pedido: any) {
    this.modalRef = this.modalService.show(template);
    this.pedidoOperacao = pedido
    console.log(pedido)  
    this.buscarTrocaPorPedido(pedido.id)
    console.log(this.pedidoOperacao)
  }

  openModal2(template: TemplateRef<any>, pedido: any) {
    this.modalRef = this.modalService.show(template);
    this.pedidoOperacao = pedido
  }
  buscarPedidos() {
    this.pedidos = undefined
    this.http.get<Pedido[]>(this.urlPedido
      ).subscribe(
      (response) => {
        this.pedidos = response
        console.log(response)
      },
      (error) => {
        console.log(error)
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

  despacharPedido(pedido: Pedido) {
    pedido.statusPedido.descricao = this.statusPedidoConstantes.EM_TRANSITO
    this.http.post(this.urlPedido+"/editar/"+pedido.id, {
      id: pedido.id,
      statusPedido: {descricao:pedido. statusPedido.descricao}
    }).subscribe(
      (response) => {
        console.log(response)
      },
      (error) => {
        console.log(error)
      }
    )
  }

  confirmarEntrega(pedido: Pedido) {
    pedido.statusPedido.descricao = this.statusPedidoConstantes.ENTREGUE
    this.http.post(this.urlPedido+"/editar/"+pedido.id, {
      id: pedido.id,
      statusPedido: {descricao:pedido. statusPedido.descricao}
    }).subscribe(
      (response) => {
        console.log(response)
      },
      (error) => {
        console.log(error)
      }
    )
  }

  aprovarTroca(pedido: Pedido) {
    pedido.statusPedido.descricao = this.statusPedidoConstantes.TROCADO
    this.http.post(this.urlPedido+"/editar/"+pedido.id, {
      id: pedido.id,
      statusPedido: {descricao:pedido. statusPedido.descricao}
    }).subscribe(
      (response) => {
        console.log(response)
        this.salvarCupomTroca(pedido)
        this.ngOnInit()
      },
      (error) => {
        console.log(error)
      }
    )
  }

  salvarCupomTroca(pedido: any) {
    console.log(pedido)
    var valorTotal = 0
    for(var i = 0; i<= pedido.itensPedido.length-1; i++) {
      valorTotal += pedido.itensPedido[i].vestimenta.valor
    }
    const cliente = this.retornaCliente(this.cliente)[0]
    console.log(cliente.id)
    this.http.post(this.urlCupomTroca, {
      valor: valorTotal,
      cliente: {id: cliente.id},
      operacao: "salvar"
    }).subscribe(
      (response) => {
        console.log(response)
        this.ngOnInit()
      },
      (error) => {
        console.log(error)
      }
    )
    
  }

  reprovarTroca(pedido: Pedido) {
    pedido.statusPedido.descricao = this.statusPedidoConstantes.ENTREGUE
    this.http.post(this.urlPedido+"/editar/"+pedido.id, {
      id: pedido.id,
      statusPedido: {descricao:pedido. statusPedido.descricao}
    }).subscribe(
      (response) => {
        console.log(response)
        this.ngOnInit()
      },
      (error) => {
        console.log(error)
      }
    )
  }

}
