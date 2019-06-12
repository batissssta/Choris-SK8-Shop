import { Component, OnInit, TemplateRef } from '@angular/core';
import { ItemEstoque, Marca, CategoriaVestimenta } from '../../../dominio';
import { Url } from '../../utilidades/url';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-gerenciar-estoque',
  templateUrl: './gerenciar-estoque.component.html',
  styleUrls: ['./gerenciar-estoque.component.css']
})
export class GerenciarEstoqueComponent implements OnInit {

  itensEstoque: ItemEstoque[]
  itemEstoque: any
  urlItemEstoque: string
  modalRef: BsModalRef
  formEntradaBaixa: FormGroup
  quantidade: any

  constructor(private http: HttpClient, private sanitizer: DomSanitizer, private modalService: BsModalService, private fb: FormBuilder) { }

  ngOnInit() {
    this.urlItemEstoque = new Url().url.itemEstoque
    this.buscarItensEstoque()
    this.formEntradaBaixa = this.fb.group({
      quantidade: ['', Validators.required]
    })
  }
  
  buscarItensEstoque() {
    this.itensEstoque = undefined
    this.http.get<ItemEstoque[]>(this.urlItemEstoque).subscribe(
      (response) => {
        this.itensEstoque = response
        console.log(this.itensEstoque)
      },
      (error) => {
        console.log(error)
      }
    )
  }

  // return a base64 image to an image
  sanitize(url: string) {
    //return url;
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }

  openModal(template: TemplateRef<any>, itemEstoque: any) {
    this.modalRef = this.modalService.show(template); 
    this.itemEstoque = itemEstoque
    console.log(this.itemEstoque)
  }

  atualizaQuantidade(form: FormGroup) {
    this.quantidade = this.itemEstoque.quantidade + form.value.quantidade
    if(this.quantidade <= 0) 
      this.quantidade = 0
    console.log(this.quantidade)
  }

  onSubmit(form: FormGroup) {
    this.http.post(this.urlItemEstoque+"/editar/"+this.itemEstoque.id, {
      vestimenta: {
        id: this.itemEstoque.vestimenta.id,
        cor: this.itemEstoque.vestimenta.cor,
        marca: this.itemEstoque.vestimenta.marca.id,
        tamanho: this.itemEstoque.vestimenta.tamanho,
        genero: this.itemEstoque.vestimenta.genero,
        categoriaVestimenta: this.itemEstoque.vestimenta.categoriaVestimenta.id,
        imagem: this.itemEstoque.vestimenta.imagem,
        valor: this.itemEstoque.vestimenta.valor
      },
      id: this.itemEstoque.id,
      quantidade: this.quantidade
    }).subscribe(
      (response) => {
        console.log(response)
        this.buscarItensEstoque()
        this.modalRef.hide()
        this.formEntradaBaixa.reset()
      },
      (error) => {
        console.log(error.error.text)
      }
    )
  }

}
