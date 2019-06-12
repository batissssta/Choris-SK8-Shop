package com.ecommerce.dto;

import com.ecommerce.dominio.Cliente;

public class TelefoneDTO extends EntidadeDTO {

    private String numero;
    private String tipo;
    private Cliente cliente;

    public TelefoneDTO(String operacao, Long id, String numero, String tipo, Cliente cliente) {
        super(operacao, id);
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
