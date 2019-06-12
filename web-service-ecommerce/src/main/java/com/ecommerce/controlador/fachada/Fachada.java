package com.ecommerce.controlador.fachada;

import com.ecommerce.controlador.Resultado;
import com.ecommerce.dao.*;
import com.ecommerce.dominio.*;
import com.ecommerce.negocio.IStrategy;
import com.ecommerce.negocio.cliente.CriptografaSenha;
import com.ecommerce.negocio.cliente.ValidaCPF;
import com.ecommerce.negocio.cliente.ValidaConfirmaSenha;
import com.ecommerce.negocio.cliente.ValidaPadraoSenha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fachada implements IFachada {
    private Map<String, IDao> daos;
    private Map<String, Map<String, List<IStrategy>>> regrasNegocio;
    private StringBuilder sb = new StringBuilder();
    private Resultado resultado;

    public Fachada() {
        daos = new HashMap<String, IDao>();
        daos.put(Cliente.class.getName(), new ClienteDao());
        daos.put(Usuario.class.getName(), new UsuarioDao());
        daos.put(Endereco.class.getName(), new EnderecoDao());
        daos.put(Cartao.class.getName(), new CartaoDao());
        daos.put(CupomTroca.class.getName(), new CupomTrocaDao());

        regrasNegocio = new HashMap<String, Map<String, List<IStrategy>>>();

        //repositorios.put(Cliente.class.getName(), new ClienteRepositorio());
        ValidaCPF validaCPF = new ValidaCPF();
        ValidaPadraoSenha validaPadraoSenha = new ValidaPadraoSenha();
        CriptografaSenha criptografaSenha = new CriptografaSenha();
        ValidaConfirmaSenha validaConfirmaSenha = new ValidaConfirmaSenha();

        List<IStrategy> rnsClienteSalvar = new ArrayList<IStrategy>();
        rnsClienteSalvar.add(validaCPF);
        rnsClienteSalvar.add(validaPadraoSenha);
        rnsClienteSalvar.add(validaConfirmaSenha);
        rnsClienteSalvar.add(criptografaSenha);

        List<IStrategy> rnsClienteAlterar = new ArrayList<IStrategy>();
        rnsClienteAlterar.add(validaCPF);
        rnsClienteAlterar.add(validaPadraoSenha);
        rnsClienteAlterar.add(validaConfirmaSenha);
        rnsClienteAlterar.add(criptografaSenha);

        Map<String, List<IStrategy>> mapaCliente = new HashMap<String, List<IStrategy>>();
        mapaCliente.put("salvar", rnsClienteSalvar);
        mapaCliente.put("alterar", rnsClienteAlterar);

        regrasNegocio.put(Cliente.class.getName(), mapaCliente);

    }

    private void executarRegras(EntidadeDominio entidade, List<IStrategy> rnsEntidade) {
        for (IStrategy rn : rnsEntidade) {
            String msg = rn.processar(entidade);
            if (msg != null) {
                sb.append(msg);
            }
        }
    }

    @Override
    public Resultado salvar(EntidadeDominio entidade) {
        resultado = new Resultado();
        sb.setLength(0);
        String nomeClasse = entidade.getClass().getName();

        Map<String, List<IStrategy>> mapaEntidade = regrasNegocio.get(nomeClasse);
        List<IStrategy> rnsEntidade = null;
        if(mapaEntidade != null) {
            rnsEntidade = mapaEntidade.get("salvar");
            executarRegras(entidade, rnsEntidade);
        }

        if (sb.length() == 0) {
            IDao dao = daos.get(nomeClasse);
            dao.salvar(entidade);
            resultado.addEntidade(entidade);

        } else {
            resultado.addEntidade(entidade);
            resultado.setMsg(sb.toString());
        }

        return resultado;

    }

    @Override
    public Resultado alterar(EntidadeDominio entidade) {
        resultado = new Resultado();
        sb.setLength(0);
        String nomeClasse = entidade.getClass().getName();

        Map<String, List<IStrategy>> mapaEntidade = regrasNegocio.get(nomeClasse);
        List<IStrategy> rnsEntidade = null;
        if(mapaEntidade != null) {
            rnsEntidade = mapaEntidade.get("alterar");
            executarRegras(entidade, rnsEntidade);
        }

        if (sb.length() == 0) {
            IDao dao = daos.get(nomeClasse);
            dao.alterar(entidade);
            resultado.addEntidade(entidade);

        } else {
            resultado.addEntidade(entidade);
            resultado.setMsg(sb.toString());
        }

        return resultado;
    }

    @Override
    public Resultado excluir(EntidadeDominio entidade) {
        resultado = new Resultado();
        String nomeClasse = entidade.getClass().getName();

        IDao dao = daos.get(nomeClasse);
        resultado.addEntidade(entidade);
        dao.excluir(entidade);

        return resultado;
    }

    @Override
    public Resultado consultar(EntidadeDominio entidade) {

        resultado = new Resultado();
        String nmClasse = entidade.getClass().getName();

        IDao dao = daos.get(nmClasse);
        resultado.setEntidades(dao.consultar(entidade));

        return resultado;

    }
}
