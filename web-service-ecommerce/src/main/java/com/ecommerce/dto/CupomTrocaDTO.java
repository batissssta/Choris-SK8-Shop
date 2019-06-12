package com.ecommerce.dto;

import com.ecommerce.dominio.Cliente;

public class CupomTrocaDTO extends EntidadeDTO {

    private Double valor;
    private Cliente cliente;

    public CupomTrocaDTO(String operacao, Long id, Double valor, Cliente cliente) {
        super(operacao, id);
        this.valor = valor;
        this.cliente = cliente;
    }

    public CupomTrocaDTO(String operacao, Long id) {
        super(operacao, id);
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
