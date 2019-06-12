package com.ecommerce.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "item_troca")
public class ItemTroca extends EntidadeDominio {
    @OneToOne
    @JoinColumn(name = "id_vestimenta")
    private Vestimenta vestimenta;

    @Column(name="quantidade")
    private Integer quantidade;

    @ManyToOne(targetEntity = Pedido.class)
    @JoinColumn(name = "id_pedido")
    @JsonIgnore
    private Pedido pedido;

    @ManyToOne(targetEntity = Troca.class)
    @JoinColumn(name = "id_troca")
    @JsonIgnore
    private Troca troca;

    public ItemTroca(Vestimenta vestimenta, Integer quantidade, Pedido pedido, Troca troca) {
        this.vestimenta = vestimenta;
        this.quantidade = quantidade;
        this.pedido = pedido;
        this.troca = troca;
    }

    public ItemTroca(Long id, Vestimenta vestimenta, Integer quantidade, Pedido pedido, Troca troca) {
        super(id);
        this.vestimenta = vestimenta;
        this.quantidade = quantidade;
        this.pedido = pedido;
        this.troca = troca;
    }

    public ItemTroca() {
    }

    public Vestimenta getVestimenta() {
        return vestimenta;
    }

    public void setVestimenta(Vestimenta vestimenta) {
        this.vestimenta = vestimenta;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Troca getTroca() {
        return troca;
    }

    public void setTroca(Troca troca) {
        this.troca = troca;
    }

}
