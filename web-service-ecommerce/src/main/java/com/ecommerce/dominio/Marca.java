package com.ecommerce.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "marca")
public class Marca extends EntidadeDominio {

    @Column(name = "nome")
    private String nome;

    public Marca() {
    }

    public Marca(String nome, Long id) {
        super(id);
        this.nome = nome;
    }

    public Marca(Long id) {
        super(id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
