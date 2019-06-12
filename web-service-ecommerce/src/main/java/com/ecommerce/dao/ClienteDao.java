package com.ecommerce.dao;

import com.ecommerce.dominio.*;
import com.ecommerce.dto.EntidadeDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao extends AbstractDao {
    public ClienteDao(Connection connection, String table, String idTable) {
        super(connection, table, idTable);
    }
    public ClienteDao(String table, String idTable) {
        super(table, idTable);
    }
    public ClienteDao() {
        super("cliente", "id");
    }

    @Override
    public void salvar(EntidadeDominio entidadeDominio) {

        Cliente cliente = (Cliente)entidadeDominio;
        int idCliente = 0;

        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.salvar(cliente.getUsuario());

        Usuario usuarioCadastrado = usuarioDao.consultarUltimoId();

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO " + table + "(nome, genero, ");		// criadno o codigo sql
        sql.append("email, senha, data_nascimento, cpf, status, id_usuario) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        try{
            openConnection();
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getGenero());
            pst.setString(3, cliente.getEmail());
            pst.setString(4, cliente.getSenha());
            pst.setDate(5, new java.sql.Date(cliente.getDataNascimento().getTime()));
            pst.setString(6, cliente.getCpf());
            pst.setString(7, cliente.getStatus());
            pst.setLong(8, usuarioCadastrado.getId());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();  // Gera os id automaticamente
            if(rs.next()){
                idCliente = rs.getInt(1);
            }
            cliente.setId((long)idCliente);
        }catch(SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        if(cliente.getTelefones() != null) {
            IDao telefoneDao = new TelefoneDao();
            for(Telefone telefone : cliente.getTelefones()) {
                telefone.setCliente(new Cliente());
                telefone.getCliente().setId((long)idCliente);
                telefoneDao.salvar(telefone);
            }
        }

        if(cliente.getEnderecos() != null) {
            IDao enderecoDao = new EnderecoDao();
            for(Endereco endereco : cliente.getEnderecos()) {
                endereco.setCliente(new Cliente());
                endereco.getCliente().setId((long)idCliente);
                enderecoDao.salvar(endereco);
            }

        }

        if(cliente.getCartoes() != null) {
            IDao cartaoDao = new CartaoDao();
            for(Cartao cartao : cliente.getCartoes()) {
                cartao.setCliente(new Cliente());
                cartao.getCliente().setId((long)idCliente);
                cartaoDao.salvar(cartao);
            }
        }

    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) {

        Cliente cliente = (Cliente)entidadeDominio;
        StringBuilder sql = new StringBuilder();

        if(cliente.getStatus().trim().equals("INATIVO")){
            sql.append("UPDATE "+table+" SET ");
            sql.append("status = ? ");
            sql.append("WHERE " + idtable + " = " + cliente.getId());
            try{
                openConnection();
                PreparedStatement pst = null;
                pst = connection.prepareStatement(sql.toString());
                pst.setString(1, cliente.getStatus());
                pst.executeUpdate();
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        else{
            sql.append("UPDATE "+table+" SET ");
            sql.append("genero=?, ");
            sql.append("data_nascimento=?, ");
            sql.append("email=?, ");
            sql.append("status=?, ");
            sql.append("cpf=?, ");
            sql.append("nome=? ");
            sql.append("WHERE " + idtable + " = " + cliente.getId()+";");

            try{
                openConnection();
                PreparedStatement pst = null;
                pst = connection.prepareStatement(sql.toString());
                pst.setString(1, cliente.getGenero());
                pst.setDate(2, new java.sql.Date(cliente.getDataNascimento().getTime()));
                pst.setString(3, cliente.getEmail());
                pst.setString(4, cliente.getStatus());
                pst.setString(5, cliente.getCpf());
                pst.setString(6, cliente.getNome());
                pst.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }

    }

    // Consulta um telefone com base no ID de telefone recebido por parametro
    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) {

        List<EntidadeDominio> clientes = new ArrayList<EntidadeDominio>();
        Cliente cliente = (Cliente) entidadeDominio;

        StringBuilder sql;
        // consulta para realizar login
        if(cliente.getEmail() != null && cliente.getSenha() != null) {
            sql = new StringBuilder();
            sql.append("SELECT * ");
            sql.append("FROM " + table + " where status like 'ATIVO' ");
            sql.append("AND email LIKE " + cliente.getEmail());
            sql.append(" AND senha LIKE " + cliente.getSenha());
        }
        // consulta de cliente pelo id do usuario
        else if(cliente.getUsuario() != null) {
            sql = new StringBuilder();
            sql.append("SELECT * ");
            sql.append("FROM " + table+ " ");
            sql.append("WHERE id_usuario = " + cliente.getUsuario().getId());
        }
        else {
            sql = new StringBuilder();
            sql.append("SELECT * ");
            sql.append("FROM " + table + " where status like 'ATIVO'");
        }
        try {
            ResultSet rs;
            openConnection();
            PreparedStatement pst = null;
            pst = connection.prepareStatement(sql.toString());
            rs = pst.executeQuery();

            IDao enderecoDao = new EnderecoDao();
            IDao telefoneDao = new TelefoneDao();
            IDao cartaoDao = new CartaoDao();
            IDao cupomTrocaDao = new CupomTrocaDao();

            while(rs.next()){
                List<Endereco> enderecos = new ArrayList<>();
                Endereco endereco = new Endereco();

                List<Telefone> telefones = new ArrayList<>();
                Telefone telefone = new Telefone();

                List<Cartao> cartoes = new ArrayList<>();
                Cartao cartao = new Cartao();

                List<CupomTroca> cuponsTroca = new ArrayList<>();
                CupomTroca cupomTroca = new CupomTroca();

                Usuario usuario = new Usuario();

                cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setGenero(rs.getString("genero"));
                cliente.setDataNascimento(new java.util.Date(rs.getDate("data_nascimento").getTime()));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setEmail(rs.getString("email"));
                cliente.setSenha(rs.getString("senha"));
                cliente.setStatus(rs.getString("status"));
                cliente.setId(rs.getLong("id"));

                cliente.setUsuario(usuario);
                cliente.getUsuario().setId(rs.getLong("id_usuario"));

                endereco.setCliente(cliente);
                telefone.setCliente(cliente);
                cartao.setCliente(cliente);
                cupomTroca.setCliente(cliente);

                for(EntidadeDominio entidade : enderecoDao.consultar(endereco)) {
                    enderecos.add((Endereco)entidade);
                }

                for(EntidadeDominio entidade : cartaoDao.consultar(cartao)) {
                    cartoes.add((Cartao)entidade);
                }

                for(EntidadeDominio entidade : telefoneDao.consultar(telefone)) {
                    telefones.add((Telefone)entidade);
                }

                for(EntidadeDominio entidade : cupomTrocaDao.consultar(cupomTroca)) {
                    cuponsTroca.add((CupomTroca)entidade);
                }

                cliente.setEnderecos(enderecos);
                cliente.setTelefones(telefones);
                cliente.setCartoes(cartoes);
                cliente.setCuponsTroca(cuponsTroca);

                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return clientes;
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) {
        Cliente cliente = (Cliente) entidadeDominio;
        cliente.setStatus("INATIVO");
        alterar(cliente);
    }
}
