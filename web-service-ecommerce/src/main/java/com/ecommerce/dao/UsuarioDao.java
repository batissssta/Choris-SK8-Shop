package com.ecommerce.dao;

import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.dominio.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao extends AbstractDao {
    public UsuarioDao(Connection connection, String table, String idTable) {
        super(connection, table, idTable);
    }
    public UsuarioDao(String table, String idTable) {
        super(table, idTable);
    }
    public UsuarioDao() {
        super("usuario", "id");
    }

    @Override
    public void salvar(EntidadeDominio entidadeDominio) {
        Usuario usuario = (Usuario) entidadeDominio;
        int idUsuario = 0;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO " + table + "(nome, permissao, ");		// criadno o codigo sql
        sql.append("email, senha) ");
        sql.append("VALUES (?, ?, ?, ?)");

        try{
            openConnection();
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, usuario.getNome());
            pst.setString(2, usuario.getPermissao());
            pst.setString(3, usuario.getEmail());
            pst.setString(4, usuario.getSenha());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();  // Gera os id automaticamente
            if(rs.next()){
                idUsuario = rs.getInt(1);
            }
            usuario.setId((long)idUsuario);
        }catch(SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) {

    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) {
        List<EntidadeDominio> usuarios = new ArrayList<EntidadeDominio>();
        Usuario usuario = (Usuario) entidadeDominio;

        StringBuilder sql;
        // consulta para realizar login
        if(usuario.getEmail() != null && usuario.getSenha() != null) {
            sql = new StringBuilder();
            sql.append("SELECT *");
            sql.append(" FROM " + table);
            sql.append(" where email LIKE '" + usuario.getEmail() + "'");
            sql.append(" AND senha LIKE '" + usuario.getSenha()+"'");
        } else {
            sql = new StringBuilder();
            sql.append("SELECT * ");
            sql.append("FROM " + table);
        }
        try {
            ResultSet rs;
            openConnection();
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString());
            rs = pst.executeQuery();

            while(rs.next()){

                usuario = new Usuario();
                usuario.setNome(rs.getString("nome"));
                usuario.setPermissao(rs.getString("permissao"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setId(rs.getLong("id"));
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return usuarios;
    }

    public Usuario consultarUltimoId() {
        Usuario usuario = new Usuario();

        StringBuilder sql;
            sql = new StringBuilder();
            sql.append("SELECT *");
            sql.append(" FROM " + table);
            sql.append(" ORDER BY id DESC limit 1");

        try {
            ResultSet rs;
            openConnection();
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString());
            rs = pst.executeQuery();

            while(rs.next()){

                usuario.setNome(rs.getString("nome"));
                usuario.setPermissao(rs.getString("permissao"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setId(rs.getLong("id"));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return usuario;
    }

}
