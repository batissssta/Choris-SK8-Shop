package com.ecommerce.controlador.viewHelper;

import com.ecommerce.controlador.Resultado;
import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.Endereco;
import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.dto.EnderecoDTO;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnderecoViewHelper implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(String json) throws IOException {
        Endereco endereco = new Endereco();

        Gson gson = new Gson();
        EnderecoDTO enderecoDTO = gson.fromJson(json, EnderecoDTO.class);
        String operacao = enderecoDTO.getOperacao();

        if(enderecoDTO.getId() != null) {
            endereco.setId(enderecoDTO.getId());
        }

        if(operacao.equals("salvar") || operacao.equals("alterar")) {
            if(enderecoDTO.getLogradouro() != null)
                endereco.setLogradouro(enderecoDTO.getLogradouro());
            else
                endereco.setLogradouro("");
            if(enderecoDTO.getNumero() != null)
                endereco.setNumero(enderecoDTO.getNumero());
            else
                endereco.setNumero("");
            if(enderecoDTO.getCep() != null)
                endereco.setCep(enderecoDTO.getCep());
            else
                endereco.setCep("");
            if(enderecoDTO.getTipoLogradouro() != null)
                endereco.setTipoLogradouro(enderecoDTO.getTipoLogradouro());
            else
                endereco.setTipoLogradouro("");
            if(enderecoDTO.getTipoResidencia() != null)
                endereco.setTipoResidencia(enderecoDTO.getTipoResidencia());
            else
                endereco.setTipoResidencia("");
            if(enderecoDTO.getBairro() != null)
                endereco.setBairro(enderecoDTO.getBairro());
            else
                endereco.setBairro("");
            if(enderecoDTO.getCidade() != null)
                endereco.setCidade(enderecoDTO.getCidade());
            else
                endereco.setCidade("");
            if(enderecoDTO.getEstado() != null)
                endereco.setEstado(enderecoDTO.getEstado());
            else
                endereco.setEstado("");
            if(enderecoDTO.getPais() != null)
                endereco.setPais(enderecoDTO.getPais());
            else
                endereco.setPais("");
            if(enderecoDTO.getTipoEndereco() != null)
                endereco.setTipoEndereco(enderecoDTO.getTipoEndereco());
            else
                endereco.setTipoEndereco("");
            if(enderecoDTO.getCliente() != null)
                endereco.setCliente(enderecoDTO.getCliente());
            else {
                Cliente cliente = new Cliente();
                cliente.setNome("");
                endereco.setCliente(cliente);
            }

        }

        return endereco;
    }

    @Override
    public void setView(Resultado resultado, String json, HttpServletResponse response) throws IOException {
        Endereco endereco = (Endereco) resultado.getEntidades().get(0);

        Gson gson = new Gson();
        EnderecoDTO enderecoDTO = gson.fromJson(json, EnderecoDTO.class);
        String operacao = enderecoDTO.getOperacao();

        if(operacao.equals("consultar")) {

            List<Endereco> enderecos = new ArrayList<>();
            for(EntidadeDominio entidade : resultado.getEntidades()) {
                enderecos.add((Endereco) entidade);
            }

            String enderecosJson = new Gson().toJson(enderecos);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(enderecosJson);
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
                out.print("{'mensagem':'Endereço cadastrado com sucesso'}");
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
                out.print("{'mensagem':'Endereço altrerado com sucesso'}");
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
                resultado.setMsg("Endereço desativado com sucesso!");
            } else {
                resultado.setMsg(resultado.getMsg());
            }

        }
    }
}
