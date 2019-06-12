import { Component, OnInit } from '@angular/core';
import { ChartjsModule } from '@ctrl/ngx-chartjs';
import { HttpClient } from '@angular/common/http';
import { Url } from 'src/app/utilidades/url';
import * as $ from 'jquery';

@Component({
  selector: 'app-graficos',
  templateUrl: './graficos.component.html',
  styleUrls: ['./graficos.component.css']
})
export class GraficosComponent implements OnInit {
  urlVestimenta = new Url().url.vestimenta
  data: any
  data2: any
  meses = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"]
  labels = []
  produtos = []
  quantidade1 = []
  quantidade2 = []
  quantidade3 = []
  produtosFaturados = []
  quantidadeFaturada = []
  vestimentas = []
  quantidadeMesesIntervalo = 0
  constructor(private http: HttpClient) {
  }

  buscarVestimentas() {
    this.vestimentas = undefined
    this.http.get<any>(this.urlVestimenta).subscribe(
      (response) => {
        this.vestimentas = response
        console.log(this.vestimentas)
      },
      (error) => {
        console.log(error)
      }
    )
  }
  
  mudaFiltragem(idVestimenta:any) {
    alert(idVestimenta)
  }

  ngOnInit() {
    this.buscarVestimentas()

    this.labels = []
    this.quantidade1 = []    
    this.quantidade2 = []
    this.quantidade3 = []

    // descobrir os produtos mais vendidos no ultimo ano
    let dataInicial = new Date()
    let dataFinal = new Date()
    dataFinal.setDate(dataInicial.getDate() + 1)
    dataInicial.setFullYear(dataFinal.getFullYear() - 1)
    this.http.post<any>(this.urlVestimenta+"/produtos-mais-vendidos", {
      dataInicial: this.formatDate(dataInicial),
      dataFinal: this.formatDate(dataFinal)
    }).subscribe(
      (response) => {
        this.produtos = response
        console.log(this.produtos)
        this.retornaQuantidade()
      },
      (error) => { 
        console.log(error)
      }
    )
    
  }

  retornaQuantidade() {
    let dataInicial = new Date()
    let dataFinal = new Date()
    dataFinal.setDate(dataInicial.getDate() + 1)
    dataInicial.setDate(dataInicial.getDate() + 1)
    dataInicial.setFullYear(dataFinal.getFullYear() - 1)
    var dataFinalContador = new Date()
      if(this.produtos != undefined) {
      dataFinalContador.setMonth(dataFinalContador.getMonth() + 1)
      dataFinalContador.setFullYear(dataFinalContador.getFullYear() - 1)
      dataFinalContador.setDate(dataFinalContador.getDate() + 1)
      // quantidade vendida no ultimo ano do produto separada por meses 1
      
      for(dataInicial; dataInicial < dataFinal; dataInicial.setMonth(dataInicial.getMonth() + 1)) {
        this.labels.push(dataFinalContador.getDate() + " de " + this.meses[dataFinalContador.getMonth()] + " de " + dataFinalContador.getFullYear())
        dataFinalContador.setMonth(dataFinalContador.getMonth() + 1) 
      }

      dataInicial.setFullYear(dataInicial.getFullYear() - 1)

      this.http.post<any>(this.urlVestimenta+"/quantidade-produtos-mais-vendidos", {
          idVestimenta: this.produtos[0].id,
          dataInicial: this.formatDate(dataInicial),
          dataFinal: this.formatDate(dataFinal)
        }).subscribe(
          (response) => {
            this.quantidade1 = response
            this.chamada1()
          },
          (error) => { 
            console.log(error)
          }
        )

        this.http.post<any>(this.urlVestimenta+"/quantidade-produtos-mais-vendidos", {
          idVestimenta: this.produtos[1].id,
          dataInicial: this.formatDate(dataInicial),
          dataFinal: this.formatDate(dataFinal)
        }).subscribe(
          (response) => {
            this.quantidade2 = response
            this.chamada2()
          },
          (error) => { 
            console.log(error)
          }
        )

        this.http.post<any>(this.urlVestimenta+"/quantidade-produtos-mais-vendidos", {
          idVestimenta: this.produtos[2].id,
          dataInicial: this.formatDate(dataInicial),
          dataFinal: this.formatDate(dataFinal)
        }).subscribe(
          (response) => {
            this.quantidade3 = response
            this.chamada3()
          },
          (error) => { 
            console.log(error)
          }
        )
       
    }
    
  }
  

  aplicaFiltrosVendidos() {
    var dataInicial: Date = new Date()
    dataInicial.setTime(Date.parse($('#dataInicial').val().toString()))  
    var dataFinal: Date = new Date()
    dataFinal.setTime(Date.parse($('#dataFinal').val().toString()))


    this.labels = []
    this.quantidade1 = []    
    this.quantidade2 = []
    this.quantidade3 = []

    var variavelASerIterada = new Date() 
    variavelASerIterada.setTime(Date.parse(dataInicial.toString()))

    var dataInicialReferencia = new Date() 
    dataInicialReferencia.setTime(Date.parse(dataInicial.toString()))


    // fazer a consulta por meses do ano
    if(dataInicial.getMonth() != dataFinal.getMonth()) {
      
      variavelASerIterada.setMonth(variavelASerIterada.getMonth() + 1)
      for(dataInicial; dataInicial < dataFinal; dataInicial.setMonth(dataInicial.getMonth() + 1)) {
        if(variavelASerIterada.getMonth() + 1 == dataFinal.getMonth() && variavelASerIterada.getFullYear() == dataFinal.getFullYear()) {
          variavelASerIterada.setDate(dataFinal.getDate() + 1)
        }
        this.labels.push(variavelASerIterada.getDate() + " de " + this.meses[variavelASerIterada.getMonth() - 1] + " de " + variavelASerIterada.getFullYear())
        variavelASerIterada.setMonth(variavelASerIterada.getMonth() + 1)
      }

      this.http.post<any>(this.urlVestimenta+"/quantidade-produtos-mais-vendidos", {
          idVestimenta: this.produtos[0].id,
          dataInicial: this.formatDate(dataInicialReferencia),
          dataFinal: this.formatDate(dataFinal)
        }).subscribe(
          (response) => {
            this.quantidade1 = response
            this.chamada1()
          },
          (error) => { 
            console.log(error)
          }
        )

        this.http.post<any>(this.urlVestimenta+"/quantidade-produtos-mais-vendidos", {
          idVestimenta: this.produtos[1].id,
          dataInicial: this.formatDate(dataInicialReferencia),
          dataFinal: this.formatDate(dataFinal)
        }).subscribe(
          (response) => {
            this.quantidade2 = response
            this.chamada2()
          },
          (error) => { 
            console.log(error)
          }
        )

        this.http.post<any>(this.urlVestimenta+"/quantidade-produtos-mais-vendidos", {
          idVestimenta: this.produtos[2].id,
          dataInicial: this.formatDate(dataInicialReferencia),
          dataFinal: this.formatDate(dataFinal)
        }).subscribe(
          (response) => {
            this.quantidade3 = response
            this.chamada3()
          },
          (error) => { 
            console.log(error)
          }
        )
    }
      
    // fazer a consulta por dias do mes
    else {
      variavelASerIterada.setDate(variavelASerIterada.getDate() + 1)
      for(dataInicial; dataInicial < dataFinal; dataInicial.setDate(dataInicial.getDate() + 1)) {
        this.labels.push(variavelASerIterada.getDate() + " de " + this.meses[variavelASerIterada.getMonth()] + " de " + variavelASerIterada.getFullYear())
        variavelASerIterada.setDate(variavelASerIterada.getDate() + 1) 

      }
      this.http.post<any>(this.urlVestimenta+"/quantidade-produtos-mais-vendidos", {
          idVestimenta: this.produtos[0].id,
          dataInicial: this.formatDate(dataInicialReferencia),
          dataFinal: this.formatDate(dataFinal)
        }).subscribe(
          (response) => {
            this.quantidade1 = response
            this.chamada1()
          },
          (error) => { 
            console.log(error)
          }
        )

        this.http.post<any>(this.urlVestimenta+"/quantidade-produtos-mais-vendidos", {
          idVestimenta: this.produtos[1].id,
          dataInicial: this.formatDate(dataInicialReferencia),
          dataFinal: this.formatDate(dataFinal)
        }).subscribe(
          (response) => {
            this.quantidade2 = response
            this.chamada2()
          },
          (error) => { 
            console.log(error)
          }
        )

        this.http.post<any>(this.urlVestimenta+"/quantidade-produtos-mais-vendidos", {
          idVestimenta: this.produtos[2].id,
          dataInicial: this.formatDate(dataInicialReferencia),
          dataFinal: this.formatDate(dataFinal)
        }).subscribe(
          (response) => {
            this.quantidade3 = response
            this.chamada3()
          },
          (error) => { 
            console.log(error)
          }
        )
    }

  }

  chamada1() {
    if(this.produtos != undefined && this.quantidade1 != undefined)
      this.montaGrafico()
  }

  chamada2() {
    if(this.produtos != undefined && this.quantidade2 != undefined)
      this.montaGrafico()
      console.log(this.produtos)
  }

  chamada3() {
    if(this.produtos != undefined && this.quantidade3 != undefined)
      this.montaGrafico()
  }

  montaGrafico(){
    this.data = {
      labels: this.labels,
      datasets: [
        {
          label: this.produtos[0].categoriaVestimenta.nome + " " + this.produtos[0].marca.nome+ " " +this.produtos[0].cor,
          data: this.quantidade1,
          fill: true,
          backgroundColor: [
            'rgba(233,79,55,0.6)'
          ],
          borderColor: [
            'rgba(233,79,55,0.6)'
          ],
          borderWidth: 1,
        } ,
        {
          label: this.produtos[1].categoriaVestimenta.nome + " " + this.produtos[1].marca.nome+ " " +this.produtos[1].cor,
          data: this.quantidade2,
          fill: true,
          backgroundColor: [
            'rgba(82,170,94,0.6)'
          ],
          borderColor: [
            'rgba(82,170,94,0.6)'
          ],
          borderWidth: 1,
        } ,
        {
          label: this.produtos[2].categoriaVestimenta.nome + " " + this.produtos[2].marca.nome+ " " +this.produtos[2].cor,
          data: this.quantidade3,
          fill: true,
          backgroundColor: [
            'rgba(58,174,216, 0.6)'
          ],
          borderColor: [
            'rgba(58,174,216, 0.6)'
          ],
          borderWidth: 1,
        }
      ], 
    };
  }

  /*montaSegundoGrafico(){
    this.data2 = {
      labels: this.labels,
      datasets: [
        {
          label: this.produtosFaturados[0].categoriaVestimenta.nome + " " + this.produtosFaturados[0].marca.nome+ " " +this.produtosFaturados[0].cor,
          data: this.quantidadeFaturada.slice(0,12),
          fill: true,
          backgroundColor: [
            'rgba(233,79,55,0.6)'
          ],
          borderColor: [
            'rgba(233,79,55,0.6)'
          ],
          borderWidth: 1,
        }, {
          label: this.produtosFaturados[1].categoriaVestimenta.nome + " " + this.produtosFaturados[1].marca.nome+ " " +this.produtosFaturados[1].cor,
          data: this.quantidadeFaturada.slice(12,24),
          fill: true,
          backgroundColor: [
            'rgba(82,170,94,0.6)'
          ],
          borderColor: [
            'rgba(82,170,94,0.6)'
          ],
          borderWidth: 1,
        }, {
          label: this.produtosFaturados[2].categoriaVestimenta.nome + " " + this.produtosFaturados[2].marca.nome+ " " +this.produtosFaturados[2].cor,
          data: this.quantidadeFaturada.slice(24,36),
          fill: true,
          backgroundColor: [
            'rgba(58,174,216, 0.6)'
          ],
          borderColor: [
            'rgba(58,174,216, 0.6)'
          ],
          borderWidth: 1,
        },
      ],
    };
  }*/

  formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
  }

}
