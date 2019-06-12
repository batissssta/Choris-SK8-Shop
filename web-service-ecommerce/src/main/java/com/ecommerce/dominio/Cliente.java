package com.ecommerce.dominio;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "cliente")
public class Cliente extends EntidadeDominio {

    @Column(name = "nome")
    private String nome;
    @Column(name = "genero")
    private String genero;
    @Column(name = "data_nascimento")
    private Date dataNascimento;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "email")
    private String email;
    @Column(name = "senha")
    private String senha;
    @Column(name = "confirma_senha")
    private String confirmarSenha;
    @OneToMany(targetEntity = Telefone.class, mappedBy = "cliente")
    private List<Telefone> telefones;
    @OneToMany(targetEntity = Endereco.class, mappedBy = "cliente")
    private List<Endereco> enderecos;
    @OneToMany(targetEntity = Cartao.class, mappedBy = "cliente")
    private List<Cartao> cartoes;
    @OneToMany(targetEntity = CupomTroca.class, mappedBy = "cliente")
    private List<CupomTroca> cuponsTroca;
    @Column(name="status")
    private String status;
    @OneToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    public Cliente() {
    }

    public Cliente(String nome, String genero, Date dataNascimento, String cpf, String email, String senha, String confirmarSenha, List<Telefone> telefones, List<Cartao> cartoes, List<Endereco> enderecos, String status, Usuario usuario, List<CupomTroca> cuponsTroca) {
        this.nome = nome;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.confirmarSenha = confirmarSenha;
        this.telefones = telefones;
        this.cartoes = cartoes;
        this.enderecos = enderecos;
        this.status = status;
        this.usuario = usuario;
        this.cuponsTroca = cuponsTroca;
    }

    public Cliente(Long id, String nome, String genero, Date dataNascimento, String cpf, String email, String senha, String confirmarSenha, List<Telefone> telefones, List<Cartao> cartoes, List<Endereco> enderecos, String status, Usuario usuario, List<CupomTroca> cuponsTroca) {
        super(id);
        this.nome = nome;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.confirmarSenha = confirmarSenha;
        this.telefones = telefones;
        this.cartoes = cartoes;
        this.enderecos = enderecos;
        this.status = status;
        this.usuario = usuario;
        this.cuponsTroca = cuponsTroca;
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

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
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
