package com.ecommerce.servico;

import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.dominio.ItemEstoque;
import com.ecommerce.negocio.IStrategy;
import com.ecommerce.negocio.vestimenta.ValidaValorDeVenda;
import com.ecommerce.repositorio.ItemEstoqueRepositorio;
import com.ecommerce.repositorio.VestimentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemEstoqueServico {
    private Map<String, List<IStrategy>> regrasNegocio = new HashMap<>();
    //private ValidaValorDeVenda validaValorDeVenda = new ValidaValorDeVenda();
    private StringBuilder sb = new StringBuilder();

    @Autowired
    ItemEstoqueRepositorio itemEstoqueRepositorio;

    public ItemEstoqueServico() {
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

    public String salvar(ItemEstoque itemEstoque) {
        String mensagem = "";
        sb.setLength(0);
        List<IStrategy> rns = regrasNegocio.get("salvar");
        executarRegras(itemEstoque, rns);
        if (sb.length() == 0) {
            itemEstoqueRepositorio.saveAndFlush(itemEstoque);
        } else {
            mensagem = (sb.toString());
        }
        return mensagem;
    }

    public String editar(ItemEstoque itemEstoque, Long id) {
        String mensagem = "";
        sb.setLength(0);
        List<IStrategy> rns = regrasNegocio.get("alterar");
        executarRegras(itemEstoque, rns);
        if(sb.length() == 0) {
            itemEstoque.setId(id);
            itemEstoqueRepositorio.save(itemEstoque);
        }
        else {
            mensagem = (sb.toString());
        }
        return mensagem;
    }

    public void excluir(long id) {
        itemEstoqueRepositorio.delete(id);
    }

    public List<ItemEstoque> consultar() {
        return itemEstoqueRepositorio.findAll();
    }

    public ItemEstoque buscarPorId(Long id) {
        return itemEstoqueRepositorio.findOne(id);
    }

    public ItemEstoque buscarPorIdVestimenta(Long id) {
        return itemEstoqueRepositorio.findByVestimenta_Id(id);
    }

}
