import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ItemPedido } from 'src/dominio';
import { Url } from '../../utilidades/url';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-carrinho',
  templateUrl: './carrinho.component.html',
  styleUrls: ['./carrinho.component.css']
})
export class CarrinhoComponent implements OnInit {

  itensCarrinho: Array<any>
  valorTotalVenda: number
  valoresVestimenta: Array<any>
  frete: number = 19.99
  urlItemEstoque: string = new Url().url.itemEstoque
  quantidade: Array<any>

  constructor(private sanitizer: DomSanitizer, private http: HttpClient) { }

  ngOnInit() {
    this.itensCarrinho = new Array()
    this.itensCarrinho = JSON.parse(localStorage.getItem('produtosCarrinho'))
    this.guardarValorVestimenta()
    this.calculaValorTotalVenda()
    this.buscaQuantidadeDisponivelEstoque()
  }

  
  sanitize(url: string) {
    //return url;
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }

  remover(i: number) {
    let vestimentas: Array<any> = []
    vestimentas = new Array()
    vestimentas = JSON.parse(localStorage.getItem('produtosCarrinho'))
    vestimentas.splice(i, 1)
    localStorage.setItem(('produtosCarrinho'), JSON.stringify(vestimentas))
    this.ngOnInit()
  }

  buscaQuantidadeDisponivelEstoque() {
    this.quantidade = new Array
    console.log(this.itensCarrinho[0].vestimenta.id)
    for(var i = 0; i <= this.itensCarrinho.length - 1; i++) {
      console.log("AA")
      this.http.get(this.urlItemEstoque + "/vestimenta/" + this.itensCarrinho[i].vestimenta.id).subscribe(
        (response) => {
          this.quantidade.push(response)
        },
        (error) => {
          console.log(error.error.text)
        }
      )
    }
    console.log(this.quantidade)
  }

  aumentaQuantidade(i: number) {
    if(this.itensCarrinho[i].quantidade != this.quantidade[i].quantidade) {
      this.itensCarrinho[i].quantidade ++
      this.itensCarrinho[i].vestimenta.valor += this.valoresVestimenta[i]
      localStorage.setItem('produtosCarrinho', JSON.stringify(this.itensCarrinho))
      this.calculaValorTotalVenda()
    }
  }

  diminuiQuantidade(i: number) {
    this.itensCarrinho[i].quantidade --
    this.itensCarrinho[i].vestimenta.valor -= this.valoresVestimenta[i]
    if(this.itensCarrinho[i].quantidade <= 0) {
      this.itensCarrinho[i].quantidade = 1
      this.itensCarrinho[i].vestimenta.valor = this.valoresVestimenta[i]
    }
    localStorage.setItem('produtosCarrinho', JSON.stringify(this.itensCarrinho))
    this.calculaValorTotalVenda()
  }

  
  guardarValorVestimenta() {
    this.valoresVestimenta = new Array()
    for(var i = 0; i <= this.itensCarrinho.length - 1; i++) {
      var valor = this.itensCarrinho[i].vestimenta.valor
      this.valoresVestimenta.push(valor)
    }
    console.log(this.valoresVestimenta)
  }


  calculaValorTotalVenda() {
    this.valorTotalVenda = 0
    for(var i = 0; i <= this.itensCarrinho.length - 1; i++) {
      this.valorTotalVenda += this.itensCarrinho[i].vestimenta.valor
    }
    this.valorTotalVenda += this.frete
    localStorage.setItem('valorTotalVenda', JSON.stringify(this.valorTotalVenda))
  }
}
