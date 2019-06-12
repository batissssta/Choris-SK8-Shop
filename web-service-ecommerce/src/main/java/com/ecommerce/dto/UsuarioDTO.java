package com.ecommerce.dto;

public class UsuarioDTO extends EntidadeDTO {

    private String nome;
    private String email;
    private String senha;
    private String permissao;

    public UsuarioDTO(String operacao, Long id, String nome, String email, String senha, String permissao) {
        super(operacao, id);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.permissao = permissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }
}
