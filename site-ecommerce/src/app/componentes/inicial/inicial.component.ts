import { Component, OnInit, TemplateRef } from '@angular/core';
import { Vestimenta, Marca, CategoriaVestimenta, Usuario, ItemEstoque } from 'src/dominio';
import { Url } from 'src/app/utilidades/url';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';
import { FormGroup, FormBuilder } from '@angular/forms';
import { NavigationExtras, Router } from '@angular/router';
import { UsuarioService } from '../../servicos/usuario.service';

import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';


@Component({
  selector: 'app-inicial',
  templateUrl: './inicial.component.html',
  styleUrls: ['./inicial.component.css']
})
export class InicialComponent implements OnInit {
  
  vestimentas: Vestimenta[]
  marcas: Marca[]
  categorias: CategoriaVestimenta[]
  itensEstoque: ItemEstoque[]
  
  urlVestimenta: string
  urlItemEstoque: string
  urlMarca: string
  urlCategoriaVestimenta: string

  modalRef: BsModalRef

  constructor(
    private http: HttpClient, 
    private fb: FormBuilder, 
    private sanitizer: DomSanitizer, 
    private route: Router,
    private usuarioService: UsuarioService,
    private modalService: BsModalService
  ) { }

  ngOnInit() {
    this.urlVestimenta = new Url().url.vestimenta
    this.urlMarca = new Url().url.marca
    this.urlCategoriaVestimenta = new Url().url.categoriaVestimenta
    this.urlItemEstoque = new Url().url.itemEstoque
    this.buscarMarcas()
    this.buscarCategorias()
    this.buscarItensEstoque()
  }

  buscarMarcas() {
    this.marcas = undefined
    this.http.get<Marca[]>(this.urlMarca)
    .subscribe(
      (response) => {
        this.marcas = response
        console.log(this.marcas)
      },
      (error) => { 
        console.log(error)
      }
    )
  }

  buscarCategorias() {
    this.categorias = undefined
    this.http.get<CategoriaVestimenta[]>(this.urlCategoriaVestimenta)
    .subscribe(
      (response) => {
        this.categorias = response
        console.log(this.categorias)
      },
      (error) => { 
        console.log(error)
      }
    )
  }

  buscarItensEstoque() {
    this.itensEstoque = undefined
    this.http.get<ItemEstoque[]>(this.urlItemEstoque)
    .subscribe(
      (response) => {
        this.itensEstoque = response
        console.log(this.itensEstoque)
        this.buscarVestimentas()
      },
      (error) => { 
        console.log(error)
      }
    )
  }

  buscarVestimentas() {
    this.vestimentas = undefined
    this.http.get<Vestimenta[]>(this.urlVestimenta).subscribe(
      (response) => {
        this.vestimentas = response
        console.log(this.vestimentas)
        this.filtrarDisponiveisEstoque()
      },
      (error) => {
        console.log(error)
      }
    )
  }

  filtrarDisponiveisEstoque() {
    console.log(this.vestimentas)
    for(var i = 0; i <= this.itensEstoque.length - 1; i++) {
      if(this.itensEstoque[i].quantidade <= 0 && this.itensEstoque[i].vestimenta.id == this.vestimentas[i].id) {
        // removendo o produto da listagem de produtos
        // caso a quantidade dele em estoque seja menor ou igual a 0
        this.vestimentas.splice(i, 1)
      }
    }
    console.log(this.vestimentas)
  }

  // return a base64 image to an image
  sanitize(url: string) {
    //return url;
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }
  
  visualizarProduto(vestimenta: Vestimenta) {
    let navigationExtras: NavigationExtras = {
      queryParams: {
        "idVestimenta": vestimenta.id
      }
    };
    this.route.navigate(["/detalhes-produto"], navigationExtras);
  }

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template); 
  }

}
