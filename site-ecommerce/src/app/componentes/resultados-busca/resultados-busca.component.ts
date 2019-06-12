import { Component, OnInit, Input } from '@angular/core';
import { Vestimenta, Usuario, ItemEstoque } from 'src/dominio';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute, Router, NavigationExtras } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Url } from 'src/app/utilidades/url';
import { DomSanitizer } from '@angular/platform-browser';
import { UsuarioService } from 'src/app/servicos/usuario.service';

@Component({
  selector: 'app-resultados-busca',
  templateUrl: './resultados-busca.component.html',
  styleUrls: ['./resultados-busca.component.css']
})
export class ResultadosBuscaComponent implements OnInit {
  
  formPesquisarVestimentas: FormGroup
  urlVestimenta: string
  urlItemEstoque: string
  filtro: string
  usuarioLogado: Usuario
  vestimentas: Vestimenta[]
  itensEstoque: ItemEstoque[]

  constructor(
    private http: HttpClient, 
    private fb: FormBuilder, 
    private activedRoute: ActivatedRoute, 
    private sanitizer: DomSanitizer,
    private route: Router,
    private usuarioService: UsuarioService
  ) {
    this.activedRoute.queryParams.subscribe(params => {
      this.filtro = params["filtro"];
    });
   }

  ngOnInit() {
    this.urlVestimenta = new Url().url.vestimenta
    this.urlItemEstoque = new Url().url.itemEstoque
    this.formPesquisarVestimentas = this.fb.group({
      descricao: ['']
    });
    this.buscarItensEstoque()
    this.usuarioLogado = JSON.parse(localStorage.getItem('currentUser'));
  }

  pesquisarVestimentas(form?: FormControl) {
    console.log(this.itensEstoque)
    if(form != undefined)
      this.filtro = form.value.descricao

    this.http.post<Vestimenta[]>(this.urlVestimenta + "/pesquisarVestimentas/" + this.filtro, {
      "descricao": this.filtro
    }).subscribe(
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

  buscarItensEstoque() {
    this.itensEstoque = undefined
    this.http.get<ItemEstoque[]>(this.urlItemEstoque)
    .subscribe(
      (response) => {
        this.itensEstoque = response
        console.log(this.itensEstoque)
        this.pesquisarVestimentas()
        //
      },
      (error) => { 
        console.log(error)
      }
    )
  }


  filtrarDisponiveisEstoque() {
    console.log(this.vestimentas)
    console.log(this.itensEstoque)
    // for complexo pra caralho mas basicamente ele varre o array que tem
    // todos os itens em estoque, e depois varre todos os itens dos produtos
    // que foram pesquisados, verifica 1 a 1 pra saber se o produto pesquisado
    // está em estoque, se estiver verifica a quantidade do produto encontrado
    // para se ela for <= 0 não mostrar na listagem
    for(var i = 0; i <= this.itensEstoque.length - 1; i++) {
      for(var j = 0; j <= this.vestimentas.length -1; j++) {
        if(this.itensEstoque[i].vestimenta.id == this.vestimentas[j].id) {
          if(this.itensEstoque[i].quantidade <= 0) 
            this.vestimentas.splice(j, 1)
        }
      }
    }
    console.log(this.vestimentas)
  }

  visualizarProduto(vestimenta: Vestimenta) {
    let navigationExtras: NavigationExtras = {
      queryParams: {
        "idVestimenta": vestimenta.id
      }
    };
    this.route.navigate(["/detalhes-produto"], navigationExtras);
  }
  // return a base64 image to an image
  sanitize(url: string) {
    //return url;
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }

  logout() {
    this.usuarioService.logout()
    this.ngOnInit()
  }
  
  voltarHome() {
    this.route.navigate(["/"])
  }

}
