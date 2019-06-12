package com.ecommerce.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
@Table(name = "item_pedido")
public class ItemPedido extends EntidadeDominio {

    @OneToOne
    @JoinColumn(name = "id_vestimenta")
    private Vestimenta vestimenta;

    @Column(name="quantidade")
    private Integer quantidade;

    @ManyToOne(targetEntity = Pedido.class)
    @JoinColumn(name = "id_pedido")
    @JsonIgnore
    private Pedido pedido;

    public ItemPedido(Vestimenta vestimenta, Integer quantidade, Pedido pedido) {
        this.vestimenta = vestimenta;
        this.quantidade = quantidade;
        this.pedido = pedido;
    }

    public ItemPedido(Long id, Vestimenta vestimenta, Integer quantidade, Pedido pedido) {
        super(id);
        this.vestimenta = vestimenta;
        this.quantidade = quantidade;
        this.pedido = pedido;
    }

    public ItemPedido() {
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
}
