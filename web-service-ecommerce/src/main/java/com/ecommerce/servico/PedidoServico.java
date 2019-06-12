package com.ecommerce.servico;

import com.ecommerce.dao.ClienteDao;
import com.ecommerce.dao.CupomTrocaDao;
import com.ecommerce.dao.IDao;
import com.ecommerce.dominio.*;
import com.ecommerce.dto.CupomTrocaDTO;
import com.ecommerce.negocio.IStrategy;
import com.ecommerce.repositorio.PedidoRepositorio;
import com.ecommerce.repositorio.StatusPedidoRepositorio;
import com.ecommerce.util.ConstantesStatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PedidoServico {

    private Map<String, List<IStrategy>> regrasNegocio = new HashMap<>();
    //private ValidaValorDeVenda validaValorDeVenda = new ValidaValorDeVenda();
    private StringBuilder sb = new StringBuilder();

    @Autowired
    PedidoRepositorio pedidoRepositorio;

    @Autowired
    StatusPedidoRepositorio statusPedidoRepositorio;

    @Autowired
    ItemPedidoServico itemPedidoServico;

    @Autowired
    ItemEstoqueServico itemEstoqueServico;

    public PedidoServico() {
        regrasNegocio.put("salvar", Arrays.asList(
                //validaValorDeVenda
                )
        );
        regrasNegocio.put("alterar", Arrays.asList(
                // regras de negocio aqui
                )
        );
    }

    private void executarRegras(EntidadeDominio entidade, List<IStrategy> rnsEntidade) {
        for (IStrategy rn : rnsEntidade) {
            String msg = rn.processar(entidade);
            if (msg != null) {
                sb.append(msg);
            }
        }
    }

    public String salvar(Pedido pedido) {
        String mensagem = "";
        sb.setLength(0);
        List<IStrategy> rns = regrasNegocio.get("salvar");
        executarRegras(pedido, rns);
        if (sb.length() == 0) {

            // Setando o status inicial ao novo pedido cadastrado
            StatusPedido statusPedido = statusPedidoRepositorio.findAllByDescricao(ConstantesStatusPedido.EM_PROCESSAMENTO);
            pedido.setStatusPedido(statusPedido);

            if(pedido.getCartao().getId() == null)
                pedido.setCartao(null);

            if(pedido.getCupomTroca().getId() == null)
                pedido.setCupomTroca(null);
            else {
                // gambiarra fodidamente fodida por um erro
                CupomTrocaDao cupomTrocaDao = new CupomTrocaDao();
                CupomTroca cupomTrocaBd = cupomTrocaDao.buscarPorId(pedido.getCupomTroca().getId());
                cupomTrocaBd.setValor(cupomTrocaBd.getValor() - pedido.getValor());
                ClienteDao clienteDao = new ClienteDao();
                Cliente cliente = new Cliente();
                cliente.setUsuario(new Usuario());
                cliente.getUsuario().setId(pedido.getUsuario().getId());
                List<EntidadeDominio> clienteBd = clienteDao.consultar(cliente);
                cupomTrocaBd.setCliente((Cliente)clienteBd.get(0));
                cupomTrocaDao.alterar(cupomTrocaBd);
            }

            // retornando o pedido salvo
            Pedido pedidoBd = pedidoRepositorio.saveAndFlush(pedido);

            // adicionando o pedido salvo aos itens do pedido
            List<ItemPedido> itensPedido = pedido.getItensPedido();
            for(ItemPedido item : itensPedido) {
                item.setPedido(pedidoBd);
                itemPedidoServico.salvar(item);
            }

            // alterando a quantidade em estoque dos protudos de um pedido
            for(ItemPedido item : pedidoBd.getItensPedido()) {
                ItemEstoque itemEstoqueBd = itemEstoqueServico.buscarPorIdVestimenta(item.getVestimenta().getId());
                ItemEstoque itemARemoverEstoque = new ItemEstoque();
                itemARemoverEstoque.setQuantidade(itemEstoqueBd.getQuantidade() - item.getQuantidade());
                itemARemoverEstoque.setVestimenta(itemEstoqueBd.getVestimenta());
                itemEstoqueServico.editar(itemARemoverEstoque, itemEstoqueBd.getId());
            }

        } else {
            mensagem = (sb.toString());
        }
        return mensagem;
    }

    public String editar(Pedido pedido, Long id) {
        String mensagem = "";
        sb.setLength(0);
        List<IStrategy> rns = regrasNegocio.get("alterar");
        executarRegras(pedido, rns);
        if(sb.length() == 0) {
            // pegando id do status que chegou
            StatusPedido statusPedido = statusPedidoRepositorio.findAllByDescricao(pedido.getStatusPedido().getDescricao());
            pedido.setStatusPedido(statusPedido);

            Pedido pedidoBd = buscarPorId(id);
            pedidoBd.setStatusPedido(statusPedido);
            pedidoRepositorio.save(pedidoBd);
        }
        else {
            mensagem = (sb.toString());
        }
        return mensagem;
    }

    public void excluir(long id) {
        pedidoRepositorio.delete(id);
    }

    public List<Pedido> consultar() {
        return pedidoRepositorio.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepositorio.findOne(id);
    }

    public List<Pedido> buscarPorIdUsuario(Long id) {
        return pedidoRepositorio.findByUsuarioId(id);
    }


}
