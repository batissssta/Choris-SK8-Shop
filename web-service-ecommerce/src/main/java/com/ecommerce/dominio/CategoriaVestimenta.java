package com.ecommerce.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categoria_vestimenta")
public class CategoriaVestimenta extends EntidadeDominio {

    @Column(name = "nome")
    private String nome;

    public CategoriaVestimenta() {
    }


    public CategoriaVestimenta(String nome, Long id) {
        super(id);
        this.nome = nome;
    }

    public CategoriaVestimenta(Long id) {
        super(id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
