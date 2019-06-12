import { Component, OnInit, TemplateRef } from '@angular/core';
import { Pedido, ItemPedido } from '../../../dominio';
import { HttpClient } from '@angular/common/http';
import { Url } from '../../utilidades/url';
import { ConstantesStatusPedido } from 'src/app/utilidades/constantesStatusPedido';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import * as $ from 'jquery';

@Component({
  selector: 'app-visualizar-pedidos',
  templateUrl: './visualizar-pedidos.component.html',
  styleUrls: ['./visualizar-pedidos.component.css']
})
export class VisualizarPedidosComponent implements OnInit {

  pedidos: any
  itensPedido: any = []
  urlPedido: string
  urlTroca: string
  usuario: any
  statusPedidoConstantes: any
  statusPedido: any
  listStatus: any
  modalRef: BsModalRef
  pedidoOperacao: any
  itensTroca: Array<any>
  aplicaCor: Array<any>
  motivoTroca: string

  constructor(private http: HttpClient, private modalService: BsModalService) { }

  ngOnInit() {
    this.urlPedido = new Url().url.pedido
    this.urlTroca = new Url().url.troca
    this.usuario = JSON.parse(localStorage.getItem('currentUser'))
    console.log(this.usuario[0].id)
    this.findPedidosByIdUsuario(this.usuario[0].id)
    this.statusPedidoConstantes = new ConstantesStatusPedido().statusPedido;
    this.itensTroca = new Array()
    this.aplicaCor = new Array
  }

  findPedidosByIdUsuario(idUsuario) {
    this.pedidos = undefined
    this.http.post<Pedido[]>(this.urlPedido + "/buscarPorIdUsuario/" + idUsuario, {
      "usuario": {id: idUsuario}
    }).subscribe(
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

  cancelarPedido(pedido: Pedido) {
    pedido.statusPedido.descricao = this.statusPedidoConstantes.CANCELADO
    this.http.post(this.urlPedido+"/editar/"+pedido.id, {
      id: pedido.id,
      statusPedido: {descricao:pedido. statusPedido.descricao}
    }).subscribe(
      (response) => {
        console.log(response)
        this.modalRef.hide()
      },
      (error) => {
        console.log(error)
      }
    )
  }
  
  openModal(template: TemplateRef<any>, pedido: Pedido) {
    this.modalRef = this.modalService.show(template); 
    this.pedidoOperacao = pedido
  }


  itemSelecionado(id, item) {
    if($(`#check`+id).is(":checked") == true) {
      this.itensTroca.push(item)
    } else {
      for(let indice = 0; indice <= this.itensTroca.length-1; indice++) {
        if(id == this.itensTroca[indice].id) {
          this.itensTroca.splice(indice, 1)
        }
      }
    }
  }

  mostraOpcaoSelecionada() {
    alert($('#motivo').val())
  }

  solicitarTroca() {
    this.http.post(this.urlTroca+"/salvar", {
      motivo: $('#motivo').val(),
      pedido: {id: this.pedidoOperacao.id},
      itensTroca: this.itensTroca
    }).subscribe(
      (response) => {
        console.log(response)
        this.modalRef.hide()
        this.ngOnInit()
      },
      (error) => {
        console.log(error)
      }
    )
  }

}