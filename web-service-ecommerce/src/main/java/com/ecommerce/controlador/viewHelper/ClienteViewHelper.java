package com.ecommerce.controlador.viewHelper;

import com.ecommerce.controlador.Resultado;
import com.ecommerce.dominio.*;
import com.ecommerce.dto.ClienteDTO;
import com.ecommerce.dto.EntidadeDTO;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClienteViewHelper implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(String json) throws IOException {

        Cliente cliente = new Cliente();

        Gson gson = new Gson();
        ClienteDTO clienteDTO = gson.fromJson(json, ClienteDTO.class);
        String operacao = clienteDTO.getOperacao();

        if(clienteDTO.getId() != null) {
            cliente.setId(clienteDTO.getId());
        }

        if(operacao.equals("salvar") || operacao.equals("alterar")) {
            Usuario usuario = new Usuario();
            if(clienteDTO.getNome() != null) {
                cliente.setNome(clienteDTO.getNome());
                usuario.setNome(clienteDTO.getNome());
            }

            else
                cliente.setNome("");
            if(clienteDTO.getGenero() != null)
                cliente.setGenero(clienteDTO.getGenero());
            else
                cliente.setGenero("");
            if(clienteDTO.getDataNascimento() != null)
                cliente.setDataNascimento(clienteDTO.getDataNascimento());
            else
                cliente.setDataNascimento(new Date(0));
            if(clienteDTO.getCpf() != null)
                cliente.setCpf(clienteDTO.getCpf());
            else
                cliente.setCpf("");
            if(clienteDTO.getEmail() != null) {
                cliente.setEmail(clienteDTO.getEmail());
                usuario.setEmail(clienteDTO.getEmail());
            }

            else
                cliente.setEmail("");
            if(clienteDTO.getSenha() != null) {
                cliente.setSenha(clienteDTO.getSenha());
                usuario.setSenha(clienteDTO.getSenha());
            }

            else
                cliente.setSenha("");
            if(clienteDTO.getConfirmarSenha() != null)
                cliente.setConfirmarSenha(clienteDTO.getConfirmarSenha());
            else
                cliente.setConfirmarSenha("");
            if(clienteDTO.getStatus() != null)
                cliente.setStatus(clienteDTO.getStatus());
            else
                cliente.setStatus("");
            if(clienteDTO.getEnderecos() != null) {
                cliente.setEnderecos(clienteDTO.getEnderecos());
            }
            else {
                /*List<Endereco> enderecos = new ArrayList<Endereco>();
                Endereco endereco = new Endereco();
                endereco.setLogradouro("");
                enderecos.add(endereco);
                cliente.setEnderecos(enderecos);*/
            }
            if(clienteDTO.getTelefones() != null) {
                cliente.setTelefones(clienteDTO.getTelefones());
            }
            else {
                List<Telefone> telefones = new ArrayList<Telefone>();
                Telefone telefone = new Telefone();
                telefone.setNumero("");
                telefones.add(telefone);
                cliente.setTelefones(telefones);
            }
            if(clienteDTO.getCartoes() != null)
                cliente.setCartoes(clienteDTO.getCartoes());
            else {
                /*
                List<Cartao> cartoes = new ArrayList<Cartao>();
                Cartao cartao = new Cartao();
                cartao.setCodigoSeguranca("");
                cartoes.add(cartao);
                cliente.setCartoes(cartoes);*/
            }
            usuario.setPermissao("cliente");
            cliente.setUsuario(usuario);

        } else if(operacao.equals("consultar")) {
            if(clienteDTO.getUsuario() != null) {
                cliente.setUsuario(clienteDTO.getUsuario());
            }
        }

        return cliente;
    }

    @Override
    public void setView(Resultado resultado, String json, HttpServletResponse response) throws IOException {

        Cliente cliente = (Cliente) resultado.getEntidades().get(0);

        Gson gson = new Gson();
        ClienteDTO clienteDTO = gson.fromJson(json, ClienteDTO.class);
        String operacao = clienteDTO.getOperacao();

        if(operacao.equals("consultar")) {

            List<Cliente> clientes = new ArrayList<>();
            for(EntidadeDominio entidade : resultado.getEntidades()) {
                clientes.add((Cliente) entidade);
            }

            String clientesJson = new Gson().toJson(clientes);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(clientesJson);
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
                out.print("{'mensagem':'Cliente cadastrado com sucesso'}");
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
                out.print("{'mensagem':'Cliente altrerado com sucesso'}");
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
                resultado.setMsg("Cliente: " + cliente.getNome() + " desativado com sucesso!");
            } else {
                resultado.setMsg(resultado.getMsg());
            }

        }

    }

}