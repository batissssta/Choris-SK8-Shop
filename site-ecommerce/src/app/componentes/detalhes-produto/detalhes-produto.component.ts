import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { Url } from 'src/app/utilidades/url';
import { Vestimenta, ItemPedido } from 'src/dominio';

@Component({
  selector: 'app-detalhes-produto',
  templateUrl: './detalhes-produto.component.html',
  styleUrls: ['./detalhes-produto.component.css']
})
export class DetalhesProdutoComponent implements OnInit {
  
  formPesquisarVestimentas: FormGroup
  formComprar: FormGroup
  
  urlVestimenta: string
  urlItemEstoque: string
  idVestimenta: number

  vestimenta: any
  itensCarrinho: Array<any>

  quantidadeDisponivelEstoque: any

  constructor(
    private http: HttpClient, 
    private fb: FormBuilder, 
    private activedRoute: ActivatedRoute, 
    private sanitizer: DomSanitizer,
    private route: Router) { 
  }

  ngOnInit() {
    this.urlVestimenta = new Url().url.vestimenta
    this.urlItemEstoque = new Url().url.itemEstoque
    
    this.activedRoute.queryParams.subscribe(params => {
      this.idVestimenta = params["idVestimenta"];
    });

    this.formPesquisarVestimentas = this.fb.group({
      descricao: ['']
    });

    this.formComprar = this.fb.group({
      tamanho: ['Selecione...']
    });

    this.buscarVestimenta(this.idVestimenta)
    this.itensCarrinho = new Array()
  
  }

  buscarVestimenta(id: number) {
    this.http.get(this.urlVestimenta + "/" + id).subscribe(
      (response) => {
        this.vestimenta = response
        console.log(this.vestimenta)
        this.buscaQuantidadeDisponivelEstoque(this.vestimenta.id)
      },
      (error) => {
        console.log(error.error.text)
      }
    )
  }

  buscaQuantidadeDisponivelEstoque(idVestimenta: number) {
    this.http.get(this.urlItemEstoque + "/vestimenta/" + idVestimenta).subscribe(
      (response) => {
        this.quantidadeDisponivelEstoque = response
        console.log(this.quantidadeDisponivelEstoque)
      },
      (error) => {
        console.log(error.error.text)
      }
    )
  }

  // return a base64 image to an image
  sanitize(url: string) {
    //return url;
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }

  atualizaTamanho(form: FormGroup) {
    this.vestimenta.tamanho = form.value.tamanho
  }

  comprar() {
    if(this.vestimenta.tamanho != undefined) {
      // exemplo do video
      /*if(localStorage.getItem('carrinho') == null) {
        let carrinho: any = []
        carrinho.push(JSON.stringify(this.vestimenta))
      } else {
        let carrinho: any = []
        let index: number = -1
        for(var i = 0; i < carrinho.length; i++) {
          let vestimenta: Vestimenta = JSON.parse(carrinho[i]);
          if(vestimenta.id == this.vestimenta.id) {
            index = i
            break
          }
        }
        if (index == -1) {
          carrinho.push(JSON.stringify(this.vestimenta))
          localStorage.setItem('carrinho', JSON.stringify(carrinho))
        }
        else {
          let vestimenta: Vestimenta = JSON.parse(carrinho[index])
          carrinho[index] = JSON.stringify(vestimenta)
          localStorage.setItem("carrinho", JSON.stringify(carrinho))
        }
      }*/
      
      // segunda tentativa
      /*if(localStorage.getItem('produtosCarrinho') != null) {
        console.log("entrou no if")
        let vestimentas: any = []
        vestimentas = JSON.parse(localStorage.getItem('produtosCarrinho'))
        console.log(vestimentas.length)

        vestimentas[2] = this.vestimenta
        localStorage.setItem('produtosCarrinho', JSON.stringify(vestimentas))
      } else {
        localStorage.setItem('produtosCarrinho', JSON.stringify(this.vestimenta))
      }*/
      
      if (localStorage.getItem('produtosCarrinho') != null) {
        this.itensCarrinho = JSON.parse(localStorage.getItem('produtosCarrinho'))
        var item = new ItemPedido()
        item.vestimenta = this.vestimenta
        item.quantidade = 1
        this.itensCarrinho.push(item)
        localStorage.setItem('produtosCarrinho', JSON.stringify(this.itensCarrinho))
      } else {
        var item = new ItemPedido()
        item.quantidade = 1
        item.vestimenta = this.vestimenta
        localStorage.setItem('produtosCarrinho', JSON.stringify([item]))
      }
      
      /*let navigationExtras: NavigationExtras = {
        queryParams: {
          "idVestimenta": this.vestimenta.id,
          "marca": this.vestimenta.marca.nome,
          "categoria": this.vestimenta.categoriaVestimenta.nome,
          "cor": this.vestimenta.cor,
          "valor": this.vestimenta.valor,
          "imagem": this.vestimenta.imagem,
          "tamanho": this.vestimenta.tamanho
        }
      };*/
      this.route.navigate(["/carrinho"]);
    }
  }

}
