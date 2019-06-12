/*package com.ecommerce.servico;

import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.negocio.IStrategy;
import com.ecommerce.negocio.cliente.ValidaCPF;
import com.ecommerce.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClienteServico {

    private Map<String, List<IStrategy>> regrasNegocio = new HashMap<>();
    private StringBuilder sb = new StringBuilder();

    @Autowired
    ClienteRepositorio clienteRepositorio;

    public ClienteServico() {
        regrasNegocio.put("salvar", Arrays.asList(
                new ValidaCPF()
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

    public String salvar(Cliente cliente) {
        String mensagem = "";
        sb.setLength(0);
        List<IStrategy> rns = regrasNegocio.get("salvar");
        executarRegras(cliente, rns);
        if (sb.length() == 0) {
            clienteRepositorio.saveAndFlush(cliente);
        } else {
            mensagem = (sb.toString());
        }
        return mensagem;
    }

    public String editar(Cliente cliente, Long id) {
        String mensagem = "";
        sb.setLength(0);
        List<IStrategy> rns = regrasNegocio.get("salvar");
        executarRegras(cliente, rns);
        if(sb.length() == 0) {
            Cliente clienteBd = buscarPorId(id);
            cliente.setId(id);
            clienteRepositorio.save(cliente);
        }
        else {
            mensagem = (sb.toString());
        }
        return mensagem;
    }

    public void excluir(long id) {
        clienteRepositorio.delete(id);
    }

    public List<Cliente> consultar() {
        return clienteRepositorio.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepositorio.findOne(id);
    }

}*/
