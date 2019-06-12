package com.ecommerce.dao;

import com.ecommerce.dominio.Cartao;
import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.CupomTroca;
import com.ecommerce.dominio.EntidadeDominio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CupomTrocaDao extends AbstractDao {
    public CupomTrocaDao(Connection connection, String table, String idTable) {
        super(connection, table, idTable);
    }

    public CupomTrocaDao(String table, String idTable) {
        super(table, idTable);
    }

    public CupomTrocaDao() {
        super("cupom_troca", "id");
    }

    @Override
    public void salvar(EntidadeDominio entidadeDominio) {

        CupomTroca cupomTroca = (CupomTroca)entidadeDominio;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO " + table + "(valor, id_cliente) ");
        sql.append("VALUES (?, ?)");

        try{
            openConnection();
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pst.setDouble(1, cupomTroca.getValor());
            pst.setLong(2, cupomTroca.getCliente().getId());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();  // Gera os id automaticamente
            int idCupom = 0;
            if(rs.next()){
                idCupom = rs.getInt(1);
            }
            cupomTroca.setId((long)idCupom);
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
        CupomTroca cupomTroca = (CupomTroca) entidadeDominio;
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE "+table+" SET ");
        sql.append("valor=?, ");
        sql.append("id_cliente=? ");
        sql.append("WHERE " + idtable + " = " + cupomTroca.getId()+";");

        try{
            pst = connection.prepareStatement(sql.toString());
            pst.setDouble(1, cupomTroca.getValor());
            pst.setLong(2, cupomTroca.getCliente().getId());
            pst.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) {

        CupomTroca cupom = (CupomTroca) entidadeDominio;
        List<EntidadeDominio> cuponsTroca = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

        if(cupom.getId() == null && cupom.getCliente().getId() == null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
        }else if(cupom.getId() != null && cupom.getCliente().getId() == null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
            sql.append(" WHERE " + idtable + " = " + cupom.getId());
        }else if(cupom.getId() == null && cupom.getCliente().getId() != null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
            sql.append(" WHERE id_cliente = " + cupom.getCliente().getId());
        }else if(cupom.getId() != null && cupom.getCliente().getId() != null){
            sql.append("SELECT * ");
            sql.append("FROM " + table);
            sql.append(" WHERE " + idtable + " = " + cupom.getId());
        }

        try {
            openConnection();
            ResultSet rs;
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString());
            rs = pst.executeQuery();

            while(rs.next()){
                CupomTroca cupomTroca = new CupomTroca();
                cupomTroca.setValor(rs.getDouble("valor"));
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id_cliente"));
                cupomTroca.setCliente(cliente);
                cupomTroca.setId(rs.getLong("id"));
                cuponsTroca.add(cupomTroca);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cuponsTroca;
    }

    public CupomTroca buscarUltimoId() {
        CupomTroca cupomTroca = new CupomTroca();

        String query = "SELECT * FROM cupom_troca ORDER BY ID DESC LIMIT 1";

        try {
            openConnection();

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result;

            result = stmt.executeQuery();
            result.first();

            cupomTroca.setId(result.getLong("id"));
            stmt.close();

            return cupomTroca;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CupomTroca buscarPorId(Long id) {
        CupomTroca cupomTroca = new CupomTroca();

        String query = "SELECT * FROM cupom_troca where id = " + id;

        try {
            openConnection();

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result;

            result = stmt.executeQuery();
            result.first();

            cupomTroca.setId(result.getLong("id"));
            cupomTroca.setValor(result.getDouble("valor"));
            stmt.close();

            return cupomTroca;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
