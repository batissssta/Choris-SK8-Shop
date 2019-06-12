package com.ecommerce.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="cupom_troca")
public class CupomTroca extends EntidadeDominio {
    @Column(name="valor")
    private Double valor;

    @ManyToOne(targetEntity = Cliente.class)
    @JoinColumn(name = "id_cliente")
    @JsonIgnore
    private Cliente cliente;

    public CupomTroca() {

    }

    public CupomTroca(Double valor, Cliente cliente) {
        this.valor = valor;
        this.cliente = cliente;
    }

    public CupomTroca(Long id, Double valor, Cliente cliente) {
        super(id);
        this.valor = valor;
        this.cliente = cliente;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
