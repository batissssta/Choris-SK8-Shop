package com.ecommerce.dominio;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="troca")
public class Troca extends EntidadeDominio {


    @Column(name = "motivo")
    private String motivo;

    @OneToMany(targetEntity = ItemTroca.class, mappedBy = "troca")
    private List<ItemTroca> itensTroca;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    public Troca(String motivo, List<ItemTroca> itensTroca, Pedido pedido) {
        this.motivo = motivo;
        this.itensTroca = itensTroca;
        this.pedido = pedido;
    }

    public Troca(Long id, String motivo, List<ItemTroca> itensTroca, Pedido pedido) {
        super(id);
        this.motivo = motivo;
        this.itensTroca= itensTroca;
        this.pedido = pedido;
    }

    public Troca() {
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public List<ItemTroca> getItensTroca() {
        return itensTroca;
    }

    public void setItensTroca(List<ItemTroca> itensTroca) {
        this.itensTroca = itensTroca;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
