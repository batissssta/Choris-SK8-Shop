package com.ecommerce.servico;

import com.ecommerce.dominio.*;
import com.ecommerce.negocio.IStrategy;
import com.ecommerce.repositorio.StatusPedidoRepositorio;
import com.ecommerce.repositorio.TrocaRepositorio;
import com.ecommerce.util.ConstantesStatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrocaServico {
    private Map<String, List<IStrategy>> regrasNegocio = new HashMap<>();
    private StringBuilder sb = new StringBuilder();

    @Autowired
    StatusPedidoRepositorio statusPedidoRepositorio;

    @Autowired
    TrocaRepositorio trocaRepositorio;

    @Autowired
    ItemTrocaServico itemTrocaServico;

    @Autowired
    PedidoServico pedidoServico;

    public TrocaServico() {
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

    public String salvar(Troca troca) {
        String mensagem = "";
        sb.setLength(0);
        List<IStrategy> rns = regrasNegocio.get("salvar");
        executarRegras(troca, rns);
        if (sb.length() == 0) {

            // Setando o status inicial ao novo pedido cadastrado
            StatusPedido statusPedido = statusPedidoRepositorio.findAllByDescricao(ConstantesStatusPedido.EM_TROCA);
            troca.getPedido().setStatusPedido(statusPedido);
            this.pedidoServico.editar(troca.getPedido(), troca.getPedido().getId());

            // setando o id da troca no itemPedido
            Troca trocaBd = trocaRepositorio.saveAndFlush(troca);

            for(ItemTroca item : trocaBd.getItensTroca()) {
                item.setTroca(trocaBd);
                item.setPedido(trocaBd.getPedido());
                itemTrocaServico.salvar(item);
            }

        } else {
            mensagem = (sb.toString());
        }
        return mensagem;
    }

    public String editar(Pedido pedido, Long id) {
        return null;
    }

    public void excluir(long id) {
    }

    public List<Troca> consultar() {
        return trocaRepositorio.findAll();
    }

    public Troca buscarPorId(Long id) {
        return trocaRepositorio.findOne(id);
    }

    public Troca buscarPorIdPedido(Long id) {
        return trocaRepositorio.findByPedido_Id(id);
    }

}
