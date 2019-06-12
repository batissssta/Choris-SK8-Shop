import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InicialComponent } from './componentes/inicial/inicial.component';
import { ClienteComponent } from './componentes/cliente/cliente.component';
import { FormularioCadastroClienteComponent } from './componentes/cliente/formulario-cadastro-cliente/formulario-cadastro-cliente.component';
import { FormularioCadastroClienteServletComponent } from './componentes/cliente-servlet/formulario-cadastro-cliente-servlet/formulario-cadastro-cliente-servlet.component';
import { FormVestimentaComponent } from './componentes/vestimenta/form-vestimenta/form-vestimenta.component';
import { ResultadosBuscaComponent } from './componentes/resultados-busca/resultados-busca.component';
import { DetalhesProdutoComponent } from './componentes/detalhes-produto/detalhes-produto.component';
import { LoginComponent } from './componentes/login/login.component';
import { CarrinhoComponent } from './componentes/carrinho/carrinho.component';
import { FinalizarVendaComponent } from './componentes/finalizar-venda/finalizar-venda.component';
import { VisualizarPedidosComponent } from './componentes/visualizar-pedidos/visualizar-pedidos.component';
import { GerenciarEnderecosComponent } from './componentes/gerenciar-enderecos/gerenciar-enderecos.component';
import { GerenciarCartoesComponent } from './componentes/gerenciar-cartoes/gerenciar-cartoes.component';
import { GerenciarPedidosComponent } from './componentes/gerenciar-pedidos/gerenciar-pedidos.component';
import { GerenciarEstoqueComponent } from './componentes/gerenciar-estoque/gerenciar-estoque.component';
import { GraficosComponent } from './componentes/graficos/graficos.component';

const routes: Routes = [
  // Inicial
  {
      path: '',
      component: InicialComponent
  },
  // Clientes
  {
      path: 'clientes',
      component: ClienteComponent
  },
  // Formulario cliente
  {
    path: 'formulario-cliente',
    component: FormularioCadastroClienteComponent
  },
  // Formulario cliente servlet
  {
    path: 'formulario-cliente-servlet',
    component: FormularioCadastroClienteServletComponent
  },
  // Formulario vestimenta
  {
    path: 'formulario-vestimenta',
    component: FormVestimentaComponent
  },
  // Resultados da busca
  {
    path: 'resultados-busca',
    component: ResultadosBuscaComponent
  },
  // Detalhes do produto
  {
    path: 'detalhes-produto',
    component: DetalhesProdutoComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'carrinho',
    component: CarrinhoComponent
  },
  {
    path: 'finalizarVenda',
    component: FinalizarVendaComponent
  },
  {
    path: 'visualizarPedidos',
    component: VisualizarPedidosComponent
  },
  {
    path: 'gerenciar-enderecos',
    component: GerenciarEnderecosComponent
  },
  {
    path: 'gerenciar-cartoes',
    component: GerenciarCartoesComponent
  },
  {
    path: 'gerenciar-pedidos',
    component: GerenciarPedidosComponent
  },
  {
    path: 'gerenciar-estoque',
    component: GerenciarEstoqueComponent
  }, 
  {
    path: 'graficos',
    component: GraficosComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
