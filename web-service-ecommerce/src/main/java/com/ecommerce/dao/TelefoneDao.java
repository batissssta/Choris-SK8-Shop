package com.ecommerce.dao;

import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.dominio.Telefone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefoneDao extends AbstractDao {

    public TelefoneDao() {
        super("telefone", "id");
    }

    public TelefoneDao(Connection connection, String table, String idTable) {
        super(connection, table, idTable);
    }

    public TelefoneDao(String table, String idTable) {
        super(table, idTable);
    }

    @Override
    public void salvar(EntidadeDominio entidadeDominio) {
        Telefone telefone = (Telefone)entidadeDominio;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO " + table + "(numero, id_cliente, tipo) ");
        sql.append("VALUES (?, ?, ?)");

        try{
            openConnection();
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, telefone.getNumero());
            pst.setLong(2, telefone.getCliente().getId());
            pst.setString(3, "Contato");
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            int idTelefone = 0;
            if(rs.next()){
                idTelefone = rs.getInt(1);
            }
            telefone.setId((long)idTelefone);

        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) {

        openConnection();

        PreparedStatement pst = null;
        Telefone telefone = (Telefone)entidadeDominio;
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE "+table+" SET ");
        sql.append("numero=? ");
        sql.append("WHERE " + idtable + " = " + telefone.getId()+";");

        try{
            pst = connection.prepareStatement(sql.toString());
            pst.setString(1, telefone.getNumero());
            pst.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) {

        Telefone tel = (Telefone) entidadeDominio;
        List<EntidadeDominio> telefones = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        if(tel.getId() == null && tel.getCliente().getId() == null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
        }else if(tel.getId() != null && tel.getCliente().getId() == null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
            sql.append(" WHERE " + idtable + " = " + tel.getId());
        }else if(tel.getId() == null && tel.getCliente().getId() != null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
            sql.append(" WHERE id_cliente = " + tel.getCliente().getId());
        }else if(tel.getId() != null && tel.getCliente().getId() != null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
            sql.append(" WHERE " + idtable + " = " + tel.getId());
        }

        try {
            openConnection();
            ResultSet rs;
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString());
            rs = pst.executeQuery();

            while(rs.next()){
                Telefone telefone = new Telefone();
                telefone.setNumero(rs.getString("numero"));
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id_cliente"));
                telefone.setCliente(cliente);
                telefone.setId(rs.getLong("id"));
                telefones.add(telefone);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return telefones;

    }

    public Telefone buscarUltimoId() {
        Telefone telefone = new Telefone();

        String query = "SELECT * FROM telefone ORDER BY ID DESC LIMIT 1";

        try {
            openConnection();

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result;

            result = stmt.executeQuery();
            result.first();

            telefone.setId(result.getLong("id"));
            stmt.close();

            return telefone;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Telefone buscarPorId(Long id) {
        Telefone telefone = new Telefone();

        String query = "SELECT * FROM telefone where id = " + id;

        try {
            openConnection();

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result;

            result = stmt.executeQuery();
            result.first();

            telefone.setId(result.getLong("id"));
            telefone.setNumero(result.getString("numero"));
            stmt.close();

            return telefone;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
