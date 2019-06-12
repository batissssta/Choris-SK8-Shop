package com.ecommerce.controlador.viewHelper;

import com.ecommerce.controlador.Resultado;
import com.ecommerce.dominio.Cartao;
import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.CupomTroca;
import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.dto.CartaoDTO;
import com.ecommerce.dto.CupomTrocaDTO;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CupomTrocaViewHelper implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(String json) throws IOException {
        CupomTroca cupomTroca = new CupomTroca();

        Gson gson = new Gson();
        CupomTrocaDTO cupomTrocaDTO = gson.fromJson(json, CupomTrocaDTO.class);
        String operacao = cupomTrocaDTO.getOperacao();

        if(cupomTrocaDTO.getId() != null) {
            cupomTroca.setId(cupomTrocaDTO.getId());
        }

        if(operacao.equals("salvar") || operacao.equals("alterar")) {
            if(cupomTrocaDTO.getValor() != null)
                cupomTroca.setValor(cupomTrocaDTO.getValor());
            else
                cupomTroca.setValor(0d);
            if(cupomTrocaDTO.getCliente() != null)
                cupomTroca.setCliente(cupomTrocaDTO.getCliente());
            else {
                Cliente cliente = new Cliente();
                cliente.setNome("");
                cupomTroca.setCliente(cliente);
            }

        }
        return cupomTroca;
    }

    @Override
    public void setView(Resultado resultado, String json, HttpServletResponse response) throws IOException {
        CupomTroca cupomTroca = (CupomTroca) resultado.getEntidades().get(0);

        Gson gson = new Gson();
        CartaoDTO cartaoDTO = gson.fromJson(json, CartaoDTO.class);
        String operacao = cartaoDTO.getOperacao();

        if(operacao.equals("consultar")) {

            List<CupomTroca> cuponsTroca = new ArrayList<>();
            for(EntidadeDominio entidade : resultado.getEntidades()) {
                cuponsTroca.add((CupomTroca) entidade);
            }

            String cuponsTrocaJson = new Gson().toJson(cuponsTroca);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(cuponsTrocaJson);
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
                out.print("{'mensagem':'Cupom cadastrado com sucesso'}");
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
                out.print("{'mensagem':'Cupom altrerado com sucesso'}");
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
                resultado.setMsg("Cupom desativado com sucesso!");
            } else {
                resultado.setMsg(resultado.getMsg());
            }

        }
    }

}

