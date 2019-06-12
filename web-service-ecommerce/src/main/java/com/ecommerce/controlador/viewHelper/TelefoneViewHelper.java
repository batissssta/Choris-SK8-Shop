package com.ecommerce.controlador.viewHelper;

import com.ecommerce.controlador.Resultado;
import com.ecommerce.dao.TelefoneDao;
import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.dominio.Telefone;
import com.ecommerce.dto.TelefoneDTO;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TelefoneViewHelper implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(String json) throws IOException {

        Telefone telefone = new Telefone();

        Gson gson = new Gson();
        TelefoneDTO telefoneDTO = gson.fromJson(json, TelefoneDTO.class);
        String operacao = telefoneDTO.getOperacao();

        if(telefoneDTO.getId() != null) {
            telefone.setId(telefoneDTO.getId());
        }

        if(operacao.equals("salvar") || operacao.equals("alterar")) {
            if(telefoneDTO.getNumero() != null)
                telefone.setNumero(telefoneDTO.getNumero());
            else
                telefone.setNumero("");

            if(telefoneDTO.getCliente() != null)
                telefone.setCliente(telefoneDTO.getCliente());
            else {
                Cliente cliente = new Cliente();
                cliente.setNome("");
                telefone.setCliente(cliente);
            }

        }

        return telefone;
    }

    @Override
    public void setView(Resultado resultado, String json, HttpServletResponse response) throws IOException {
        Telefone telefone = (Telefone) resultado.getEntidades().get(0);

        Gson gson = new Gson();
        TelefoneDTO telefoneDTO = gson.fromJson(json, TelefoneDTO.class);
        String operacao = telefoneDTO.getOperacao();

        if(operacao.equals("consultar")) {

            List<Telefone> telefones = new ArrayList<>();
            for(EntidadeDominio entidade : resultado.getEntidades()) {
                telefones.add((Telefone) entidade);
            }

            String telefonesJson = new Gson().toJson(telefones);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(telefonesJson);
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
                out.print("{'mensagem':'Telefone cadastrado com sucesso'}");
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
                out.print("{'mensagem':'Telefone altrerado com sucesso'}");
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
                resultado.setMsg("Telefone desativado com sucesso!");
            } else {
                resultado.setMsg(resultado.getMsg());
            }

        }
    }
}
