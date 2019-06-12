package com.ecommerce.dto;

import com.ecommerce.dominio.*;

import java.util.Date;
import java.util.List;

public class ClienteDTO extends EntidadeDTO {
    private String nome;
    private String genero;
    private Date dataNascimento;
    private String cpf;
    private String email;
    private String senha;
    private String confirmarSenha;
    private List<Endereco> enderecos;
    private List<Telefone> telefones;
    private List<Cartao> cartoes;
    private List<CupomTroca> cuponsTroca;
    private String status;
    private Usuario usuario;

    public ClienteDTO(String operacao, Long id) {
        super(operacao, id);
    }

    public ClienteDTO(String operacao, Long id, String nome, String genero, Date dataNascimento, String cpf, String email, String senha, String confirmarSenha, List<Endereco> enderecos, List<Telefone> telefones, List<Cartao> cartoes, String status, Usuario usuario) {
        super(operacao, id);
        this.nome = nome;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.confirmarSenha = confirmarSenha;
        this.enderecos = enderecos;
        this.telefones = telefones;
        this.cartoes = cartoes;
        this.status = status;
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<CupomTroca> getCuponsTroca() {
        return cuponsTroca;
    }

    public void setCuponsTroca(List<CupomTroca> cuponsTroca) {
        this.cuponsTroca = cuponsTroca;
    }
}