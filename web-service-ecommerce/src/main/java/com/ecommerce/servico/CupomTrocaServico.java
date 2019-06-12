package com.ecommerce.servico;

import com.ecommerce.dominio.CupomTroca;
import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.dominio.ItemEstoque;
import com.ecommerce.negocio.IStrategy;
import com.ecommerce.repositorio.CupomTrocaRepositorio;
import com.ecommerce.repositorio.ItemEstoqueRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CupomTrocaServico {
    private Map<String, List<IStrategy>> regrasNegocio = new HashMap<>();
    private StringBuilder sb = new StringBuilder();

    @Autowired
    CupomTrocaRepositorio cupomTrocaRepositorio;

    public CupomTrocaServico() {
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

    public String salvar(CupomTroca cupomTroca) {
        String mensagem = "";
        sb.setLength(0);
        List<IStrategy> rns = regrasNegocio.get("salvar");
        executarRegras(cupomTroca, rns);
        if (sb.length() == 0) {
            cupomTrocaRepositorio.saveAndFlush(cupomTroca);
        } else {
            mensagem = (sb.toString());
        }
        return mensagem;
    }

    public String editar(ItemEstoque itemEstoque, Long id) {
        return null;
    }

    public void excluir(long id) {

    }

    public List<ItemEstoque> consultar() {
        return null;
    }

    public ItemEstoque buscarPorId(Long id) {
        return null;
    }

}
