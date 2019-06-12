package com.ecommerce.dominio;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="pedido")
public class Pedido extends EntidadeDominio{

    @Column(name = "data")
    private Date data;

    @Column(name = "valor")
    private Double valor;

    @ManyToOne(targetEntity = Usuario.class)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(targetEntity = Endereco.class)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @ManyToOne(targetEntity = Cartao.class)
    @JoinColumn(name = "id_cartao")
    private Cartao cartao;

    @ManyToOne(targetEntity = CupomTroca.class)
    @JoinColumn(name = "id_cupom_troca")
    private CupomTroca cupomTroca;

    @OneToMany(targetEntity = ItemPedido.class, mappedBy = "pedido")
    private List<ItemPedido> itensPedido;

    @ManyToOne(targetEntity = StatusPedido.class)
    @JoinColumn(name="id_status")
    private StatusPedido statusPedido;

    public Pedido(Date data, Double valor, Usuario usuario, Endereco endereco, Cartao cartao, CupomTroca cupomTroca, List<ItemPedido> itensPedido, StatusPedido statusPedido) {
        this.data = data;
        this.valor = valor;
        this.usuario = usuario;
        this.endereco = endereco;
        this.cartao = cartao;
        this.cupomTroca = cupomTroca;
        this.itensPedido = itensPedido;
        this.statusPedido = statusPedido;
    }

    public Pedido(Long id, Date data, Double valor, Usuario usuario, Endereco endereco, Cartao cartao, CupomTroca cupomTroca, List<ItemPedido> itensPedido, StatusPedido statusPedido) {
        super(id);
        this.data = data;
        this.valor = valor;
        this.usuario = usuario;
        this.endereco = endereco;
        this.cartao = cartao;
        this.cupomTroca = cupomTroca;
        this.itensPedido = itensPedido;
        this.statusPedido = statusPedido;
    }

    public Pedido() {
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public CupomTroca getCupomTroca() {
        return cupomTroca;
    }

    public void setCupomTroca(CupomTroca cupomTroca) {
        this.cupomTroca = cupomTroca;
    }
}
