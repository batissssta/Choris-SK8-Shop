package com.ecommerce.dao;

import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.Endereco;
import com.ecommerce.dominio.EntidadeDominio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDao extends AbstractDao {
    public EnderecoDao(Connection connection, String table, String idTable) {
        super(connection, table, idTable);
    }

    public EnderecoDao(String table, String idTable) {
        super(table, idTable);
    }


    public EnderecoDao() {
        super("endereco", "id");
    }

    @Override
    public void salvar(EntidadeDominio entidadeDominio) {

        Endereco endereco = (Endereco)entidadeDominio;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO " + table + "(logradouro, numero, ");		// criadno o codigo sql
        sql.append("cep, tipo_logradouro, tipo_residencia, tipo_endereco, bairro, cidade, estado, pais, id_cliente) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        try{
            openConnection();
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, endereco.getLogradouro());
            pst.setString(2, endereco.getNumero());
            pst.setString(3, endereco.getCep());
            pst.setString(4, endereco.getTipoLogradouro());
            pst.setString(5, endereco.getTipoResidencia());
            pst.setString(6, endereco.getTipoEndereco());
            pst.setString(7, endereco.getBairro());
            pst.setString(8, endereco.getCidade());
            pst.setString(9, endereco.getEstado());
            pst.setString(10, endereco.getPais());
            pst.setLong(11, endereco.getCliente().getId());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();  // Gera os id automaticamente
            int idEndereco = 0;
            if(rs.next()){
                idEndereco = rs.getInt(1);
            }
            endereco.setId((long)idEndereco);

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
        Endereco endereco = (Endereco)entidadeDominio;
        StringBuilder sql = new StringBuilder();


        sql.append("UPDATE "+table+" SET ");
        sql.append("logradouro=?, ");
        sql.append("numero=?, ");
        sql.append("cep=?, ");
        sql.append("tipo_logradouro=?, ");
        sql.append("tipo_residencia=?, ");
        sql.append("tipo_endereco=?, ");
        sql.append("bairro=?, ");
        sql.append("cidade=?, ");
        sql.append("estado=?, ");
        sql.append("pais=? ");
        sql.append("WHERE " + idtable + " = " + endereco.getId()+";");

        try{
            pst = connection.prepareStatement(sql.toString());
            pst.setString(1, endereco.getLogradouro());
            pst.setString(2, endereco.getNumero());
            pst.setString(3, endereco.getCep());
            pst.setString(4, endereco.getTipoLogradouro());
            pst.setString(5, endereco.getTipoResidencia());
            pst.setString(6, endereco.getTipoEndereco());
            pst.setString(7, endereco.getBairro());
            pst.setString(8, endereco.getCidade());
            pst.setString(9, endereco.getEstado());
            pst.setString(10, endereco.getPais());

            pst.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) {

        Endereco end = (Endereco) entidadeDominio;
        List<EntidadeDominio> enderecos = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        if(end.getId() == null && end.getCliente().getId() == null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
        }else if(end.getId() != null && end.getCliente().getId() == null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
            sql.append(" WHERE " + idtable + " = " + end.getId());
        }else if(end.getId() == null && end.getCliente().getId() != null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
            sql.append(" WHERE id_cliente = " + end.getCliente().getId());
        }else if(end.getId() != null && end.getCliente().getId() != null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
            sql.append(" WHERE " + idtable + " = " + end.getId());
        }

        try {
            openConnection();
            ResultSet rs;
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString());
            rs = pst.executeQuery();

            while(rs.next()){
                Endereco endereco = new Endereco();
                endereco.setLogradouro(rs.getString("logradouro"));
                endereco.setTipoEndereco(rs.getString("tipo_endereco"));
                endereco.setPais(rs.getString("pais"));
                endereco.setEstado(rs.getString("estado"));
                endereco.setCidade(rs.getString("cidade"));
                endereco.setBairro(rs.getString("bairro"));
                endereco.setTipoResidencia(rs.getString("tipo_residencia"));
                endereco.setTipoLogradouro(rs.getString("tipo_logradouro"));
                endereco.setCep(rs.getString("cep"));
                endereco.setNumero(rs.getString("numero"));
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id_cliente"));
                endereco.setCliente(cliente);
                endereco.setId(rs.getLong("id"));
                enderecos.add(endereco);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enderecos;

    }

    public Endereco buscarUltimoId() {
        Endereco endereco = new Endereco();

        String query = "SELECT * FROM endereco ORDER BY ID DESC LIMIT 1";

        try {
            openConnection();

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result;

            result = stmt.executeQuery();
            result.first();

            endereco.setId(result.getLong("id"));
            stmt.close();

            return endereco;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Endereco buscarPorId(Long id) {
        Endereco endereco = new Endereco();

        String query = "SELECT * FROM endereco where id = " + id;

        try {
            openConnection();

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result;

            result = stmt.executeQuery();
            result.first();

            endereco.setId(result.getLong("id"));
            endereco.setLogradouro(result.getString("logradouro"));
            endereco.setNumero(result.getString("numero"));
            endereco.setCep(result.getString("cep"));
            endereco.setTipoLogradouro(result.getString("tipo_logradouro"));
            endereco.setTipoResidencia(result.getString("tipo_residencia"));
            endereco.setTipoEndereco(result.getString("tipo_endereco"));
            endereco.setBairro(result.getString("bairro"));
            endereco.setCidade(result.getString("cidade"));
            endereco.setEstado(result.getString("estado"));
            endereco.setPais(result.getString("pais"));
            stmt.close();

            return endereco;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
