package com.ecommerce.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="telefone")
public class Telefone extends EntidadeDominio {

    private String numero;
    private String tipo;
    @ManyToOne(targetEntity = Cliente.class)
    @JoinColumn(name = "id_cliente")
    @JsonIgnore
    private Cliente cliente;

    public Telefone() {
    }

    public Telefone(String numero, String tipo, Cliente cliente) {
        this.numero = numero;
        this.tipo = tipo;
        this.cliente = cliente;
    }

    public Telefone(Long id, String numero, String tipo, Cliente cliente) {
        super(id);
        this.numero = numero;
        this.tipo = tipo;
        this.cliente = cliente;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
