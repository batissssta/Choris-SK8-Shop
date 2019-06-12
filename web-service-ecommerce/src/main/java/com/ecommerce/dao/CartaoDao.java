package com.ecommerce.dao;

import com.ecommerce.dominio.Cartao;
import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.EntidadeDominio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartaoDao extends AbstractDao {
    public CartaoDao(Connection connection, String table, String idTable) {
        super(connection, table, idTable);
    }

    public CartaoDao(String table, String idTable) {
        super(table, idTable);
    }

    public CartaoDao() {
        super("cartao", "id");
    }

    @Override
    public void salvar(EntidadeDominio entidadeDominio) {

        Cartao cartao = (Cartao)entidadeDominio;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO " + table + "(codigo_seguranca, numero, ");
        sql.append("validade, preferido, nome_impresso, bandeira, id_cliente) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?)");

        try{
            openConnection();
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, cartao.getCodigoSeguranca());
            pst.setString(2, cartao.getNumeroCartao());
            pst.setDate(3, new Date(cartao.getDataValidade().getTime()));
            pst.setBoolean(4, cartao.getPreferido());
            pst.setString(5, cartao.getNomeImpresso());
            pst.setString(6, cartao.getBandeiraCartao());
            pst.setLong(7, cartao.getCliente().getId());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();  // Gera os id automaticamente
            int idCartao = 0;
            if(rs.next()){
                idCartao = rs.getInt(1);
            }
            cartao.setId((long)idCartao);
        }catch(SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) {
        openConnection();

        PreparedStatement pst = null;
        Cartao cartao = (Cartao)entidadeDominio;
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE "+table+" SET ");
        sql.append("codigo_seguranca=?, ");
        sql.append("numero=?, ");
        sql.append("validade=?, ");
        sql.append("preferido=?, ");
        sql.append("nome_impresso=?, ");
        sql.append("bandeira=? ");
        sql.append("WHERE " + idtable + " = " + cartao.getId()+";");

        try{
            pst = connection.prepareStatement(sql.toString());
            pst.setString(1, cartao.getCodigoSeguranca());
            pst.setString(2, cartao.getNumeroCartao());
            pst.setDate(3, new Date(0));
            pst.setBoolean(4, cartao.getPreferido());
            pst.setString(5, cartao.getNomeImpresso());
            pst.setString(6, cartao.getBandeiraCartao());
            pst.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) {

        Cartao cart = (Cartao) entidadeDominio;
        List<EntidadeDominio> cartoes = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        if(cart.getId() == null && cart.getCliente().getId() == null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
        }else if(cart.getId() != null && cart.getCliente().getId() == null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
            sql.append(" WHERE " + idtable + " = " + cart.getId());
        }else if(cart.getId() == null && cart.getCliente().getId() != null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
            sql.append(" WHERE id_cliente = " + cart.getCliente().getId());
        }else if(cart.getId() != null && cart.getCliente().getId() != null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
            sql.append(" WHERE " + idtable + " = " + cart.getId());
        }

        try {
            openConnection();
            ResultSet rs;
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString());
            rs = pst.executeQuery();

            while(rs.next()){
                Cartao cartao = new Cartao();
                cartao.setBandeiraCartao(rs.getString("bandeira"));
                cartao.setNomeImpresso(rs.getString("nome_impresso"));
                cartao.setPreferido(rs.getBoolean("preferido"));
                cartao.setDataValidade(new java.util.Date(rs.getDate("validade").getTime()));
                cartao.setCodigoSeguranca(rs.getString("codigo_seguranca"));
                cartao.setNumeroCartao(rs.getString("numero"));
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id_cliente"));
                cartao.setCliente(cliente);
                cartao.setId(rs.getLong("id"));
                cartoes.add(cartao);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cartoes;
    }

    public Cartao buscarUltimoId() {
        Cartao cartao = new Cartao();

        String query = "SELECT * FROM cartao ORDER BY ID DESC LIMIT 1";

        try {
            openConnection();

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result;

            result = stmt.executeQuery();
            result.first();

            cartao.setId(result.getLong("id"));
            stmt.close();

            return cartao;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cartao buscarPorId(Long id) {
        Cartao cartao = new Cartao();

        String query = "SELECT * FROM cartao where id = " + id;

        try {
            openConnection();

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result;

            result = stmt.executeQuery();
            result.first();

            cartao.setId(result.getLong("id"));
            cartao.setCodigoSeguranca(result.getString("codigo_seguranca"));
            cartao.setNumeroCartao(result.getString("numero"));
            cartao.setDataValidade(result.getDate("validade"));
            cartao.setPreferido(result.getBoolean("preferido"));
            cartao.setNomeImpresso(result.getString("nome_impresso"));
            cartao.setBandeiraCartao(result.getString("bandeira"));
            stmt.close();

            return cartao;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
