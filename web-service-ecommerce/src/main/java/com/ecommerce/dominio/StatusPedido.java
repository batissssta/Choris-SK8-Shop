package com.ecommerce.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="status_pedido")
public class StatusPedido extends EntidadeDominio {
    @Column(name="descricao")
    private String descricao;

    public StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public StatusPedido(Long id, String descricao) {
        super(id);
        this.descricao = descricao;
    }

    public StatusPedido() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
