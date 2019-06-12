package com.ecommerce.dao;

import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.util.ConexaoMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public abstract class AbstractDao implements IDao {

    protected Connection connection;
    protected String table;     // Nome da tabela que irá ser manipulada
    protected String idtable;   // Nome da coluna que contem o id a ser manipulado
    protected boolean controlTransaction = true;    // Controla as transações, se ja estiver aberta ou se ela está fechada

    public AbstractDao(Connection connection, String table, String idTable){
        this.connection = connection;
        this.table = table;
        this.idtable = idTable;
    }

    public AbstractDao(String table, String idTable){
        this.table = table;
        this.idtable = idTable;
    }

    protected void openConnection(){
        connection = new ConexaoMySql().getConecction();
    }

    protected void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) {
        // TODO Auto-generated method stub
        openConnection();
        PreparedStatement pst = null;
        StringBuilder sb = new StringBuilder();
        // DELETE FROM NomeTABELA WHERE NomePK = ?
        sb.append("DELETE FROM ");
        sb.append(table);
        sb.append(" WHERE ");
        sb.append(idtable);
        sb.append(" = ");
        sb.append("?");
        try{
            pst = connection.prepareStatement(sb.toString());	// manda para o pst(preparedStatement) o sql que sera executado no banco
            pst.setLong(1, entidadeDominio.getId());		// Substitui o primeiro ?(parametro) pelo valor fornecido
            pst.executeUpdate();						// executa a update no banco
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
