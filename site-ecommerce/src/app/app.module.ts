import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClienteComponent } from './componentes/cliente/cliente.component';
import { InicialComponent } from './componentes/inicial/inicial.component';
import { FormularioCadastroClienteComponent } from './componentes/cliente/formulario-cadastro-cliente/formulario-cadastro-cliente.component';
import { ClienteServletComponent } from './componentes/cliente-servlet/cliente-servlet.component';
import { FormularioCadastroClienteServletComponent } from './componentes/cliente-servlet/formulario-cadastro-cliente-servlet/formulario-cadastro-cliente-servlet.component';
import { FormsModule } from '@angular/forms';
import { VestimentaComponent } from './componentes/vestimenta/vestimenta.component';
import { FormVestimentaComponent } from './componentes/vestimenta/form-vestimenta/form-vestimenta.component';
import { ResultadosBuscaComponent } from './componentes/resultados-busca/resultados-busca.component';
import { DetalhesProdutoComponent } from './componentes/detalhes-produto/detalhes-produto.component';
import { LoginComponent } from './componentes/login/login.component';
import { UsuarioService } from './servicos/usuario.service';
import { CarrinhoComponent } from './componentes/carrinho/carrinho.component';
import { FinalizarVendaComponent } from './componentes/finalizar-venda/finalizar-venda.component';
import { VisualizarPedidosComponent } from './componentes/visualizar-pedidos/visualizar-pedidos.component';
import { MenuComponent } from './componentes/menu/menu.component';
import { GerenciarEnderecosComponent } from './componentes/gerenciar-enderecos/gerenciar-enderecos.component';
import { GerenciarCartoesComponent } from './componentes/gerenciar-cartoes/gerenciar-cartoes.component';
import { GerenciarPedidosComponent } from './componentes/gerenciar-pedidos/gerenciar-pedidos.component';
import { AlertService } from './servicos/alert.service';
import { ModalModule } from 'ngx-bootstrap/modal';
import { DataTablesModule } from 'angular-datatables';
import { GerenciarEstoqueComponent } from './componentes/gerenciar-estoque/gerenciar-estoque.component';
import { GraficosComponent } from './componentes/graficos/graficos.component';
import { ChartjsModule } from '@ctrl/ngx-chartjs';


@NgModule({
  declarations: [
    AppComponent,
    ClienteComponent,
    InicialComponent,
    FormularioCadastroClienteComponent,
    ClienteServletComponent,
    FormularioCadastroClienteServletComponent,
    VestimentaComponent,
    FormVestimentaComponent,
    ResultadosBuscaComponent,
    DetalhesProdutoComponent,
    LoginComponent,
    CarrinhoComponent,
    FinalizarVendaComponent,
    VisualizarPedidosComponent,
    MenuComponent,
    GerenciarEnderecosComponent,
    GerenciarCartoesComponent,
    GerenciarPedidosComponent,
    GerenciarEstoqueComponent,
    GraficosComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    ModalModule.forRoot(),
    DataTablesModule,
    ChartjsModule
  ],
  providers: [UsuarioService, AlertService],
  bootstrap: [AppComponent],
  exports: [ModalModule]
})
export class AppModule { }
