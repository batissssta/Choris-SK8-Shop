package com.ecommerce.controlador.viewHelper;

import com.ecommerce.controlador.Resultado;
import com.ecommerce.dominio.Cartao;
import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.dto.CartaoDTO;
import com.ecommerce.dto.EnderecoDTO;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartaoViewHelper implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(String json) throws IOException {
        Cartao cartao = new Cartao();

        Gson gson = new Gson();
        CartaoDTO cartaoDTO = gson.fromJson(json, CartaoDTO.class);
        String operacao = cartaoDTO.getOperacao();

        if(cartaoDTO.getId() != null) {
            cartao.setId(cartaoDTO.getId());
        }

        if(operacao.equals("salvar") || operacao.equals("alterar")) {
            if(cartaoDTO.getCodigoSeguranca() != null)
                cartao.setCodigoSeguranca(cartaoDTO.getCodigoSeguranca());
            else
                cartao.setCodigoSeguranca("");
            if(cartaoDTO.getNumeroCartao() != null)
                cartao.setNumeroCartao(cartaoDTO.getNumeroCartao());
            else
                cartao.setNumeroCartao("");
            if(cartaoDTO.getDataValidade() != null)
                cartao.setDataValidade(cartaoDTO.getDataValidade());
            else
                cartao.setDataValidade(new Date(0));
            if(cartaoDTO.getPreferido() != null)
                cartao.setPreferido(cartaoDTO.getPreferido());
            else
                cartao.setPreferido(false);
            if(cartaoDTO.getNomeImpresso() != null)
                cartao.setNomeImpresso(cartaoDTO.getNomeImpresso());
            else
                cartao.setNomeImpresso("");
            if(cartaoDTO.getBandeiraCartao() != null)
                cartao.setBandeiraCartao(cartaoDTO.getBandeiraCartao());
            else
                cartao.setBandeiraCartao("");
            if(cartaoDTO.getCliente() != null)
                cartao.setCliente(cartaoDTO.getCliente());
            else {
                Cliente cliente = new Cliente();
                cliente.setNome("");
                cartao.setCliente(cliente);
            }

        }

        return cartao;
    }

    @Override
    public void setView(Resultado resultado, String json, HttpServletResponse response) throws IOException {
        Cartao cartao = (Cartao) resultado.getEntidades().get(0);

        Gson gson = new Gson();
        CartaoDTO cartaoDTO = gson.fromJson(json, CartaoDTO.class);
        String operacao = cartaoDTO.getOperacao();

        if(operacao.equals("consultar")) {

            List<Cartao> cartaos = new ArrayList<>();
            for(EntidadeDominio entidade : resultado.getEntidades()) {
                cartaos.add((Cartao) entidade);
            }

            String cartaosJson = new Gson().toJson(cartaos);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(cartaosJson);
            out.flush();

            //request.setAttribute("resultado", resultado);
        }

        if(operacao.equals("salvar")) {
            /*request.setAttribute("resultado", resultado);
            request.setAttribute("operacao", "salvar");
            request.setAttribute("entidade", "cliente");
            */
            if (resultado.getMsg() == null) {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print("{'mensagem':'Cartão cadastrado com sucesso'}");
                out.flush();

                //resultado.setMsg("Cliente: " + cliente.getNome() + " salvo com sucesso!");
            } else {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print("{'mensagem':'"+resultado.getMsg()+"''}");
                out.flush();
            }

        }

        if (operacao.equals("alterar")) {
            /*request.setAttribute("resultado", resultado);
            request.setAttribute("entidade", "cliente");
            */
            if (resultado.getMsg() == null) {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print("{'mensagem':'Cartão altrerado com sucesso'}");
                out.flush();
            } else {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print("{'mensagem':'"+resultado.getMsg()+"''}");
                out.flush();
            }

        }

        if (resultado.getMsg() == null && operacao.equals("excluir")) {
            /*request.setAttribute("resultado", resultado);
            request.setAttribute("operacao", "excluir");
            request.setAttribute("entidade", "cliente");
            */
            if (resultado.getMsg() == null) {
                resultado.setMsg("Cartão desativado com sucesso!");
            } else {
                resultado.setMsg(resultado.getMsg());
            }

        }
    }
}
