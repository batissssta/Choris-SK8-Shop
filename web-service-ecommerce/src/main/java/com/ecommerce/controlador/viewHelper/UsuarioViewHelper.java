package com.ecommerce.controlador.viewHelper;

import com.ecommerce.controlador.Resultado;
import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.dominio.Usuario;
import com.ecommerce.dto.UsuarioDTO;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UsuarioViewHelper implements IViewHelper {
    @Override
    public EntidadeDominio getEntidade(String json) throws IOException {
        Usuario usuario = new Usuario();

        Gson gson = new Gson();
        UsuarioDTO usuarioDTO = gson.fromJson(json, UsuarioDTO.class);
        String operacao = usuarioDTO.getOperacao();

        if(usuarioDTO.getId() != null) {
            usuario.setId(usuarioDTO.getId());
        }

        if(operacao.equals("salvar") || operacao.equals("alterar")) {
            if (usuarioDTO.getNome() != null)
                usuario.setNome(usuarioDTO.getNome());
            else
                usuario.setNome("");
            if(usuarioDTO.getEmail() != null)
                usuario.setEmail(usuarioDTO.getEmail());
            else
                usuario.setEmail("");
            if(usuarioDTO.getSenha() != null)
                usuario.setSenha(usuarioDTO.getSenha());
            else
                usuario.setSenha("");
            if(usuarioDTO.getPermissao() != null)
                usuario.setPermissao(usuarioDTO.getPermissao());
            else
                usuario.setPermissao("");

        } else if(operacao.equals("consultar")) {
            if(usuarioDTO.getSenha() != null && usuarioDTO.getEmail() != null) {
                usuario.setSenha(usuarioDTO.getSenha());
                usuario.setEmail(usuarioDTO.getEmail());
            }
        }
        return usuario;
    }

    @Override
    public void setView(Resultado resultado, String json, HttpServletResponse response) throws IOException, IndexOutOfBoundsException {

        /*if(resultado.getEntidades().get(0) != null) {
            Usuario usuario = (Usuario) resultado.getEntidades().get(0);
        }*/

        Gson gson = new Gson();
        UsuarioDTO usuarioDTO = gson.fromJson(json, UsuarioDTO.class);
        String operacao = usuarioDTO.getOperacao();

        if(operacao.equals("consultar")) {

            List<Usuario> usuarios = new ArrayList<>();
            for(EntidadeDominio entidade : resultado.getEntidades()) {
                usuarios.add((Usuario) entidade);
            }

            String usuariosJson = new Gson().toJson(usuarios);

            if(usuariosJson.equals("[]")) {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print("{'Nenhum usuário encontrado'}");
                out.flush();
            } else {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(usuariosJson);
                out.flush();
            }

            //request.setAttribute("resultado", resultado);
        }

    }
}
