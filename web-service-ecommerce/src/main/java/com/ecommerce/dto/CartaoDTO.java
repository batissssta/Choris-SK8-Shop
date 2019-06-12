package com.ecommerce.dto;

import com.ecommerce.dominio.Cliente;

import java.util.Date;

public class CartaoDTO extends EntidadeDTO {

    private String codigoSeguranca;
    private String numeroCartao;
    private Date dataValidade;
    private Boolean preferido;
    private String nomeImpresso;
    private String bandeiraCartao;
    private Cliente cliente;

    public CartaoDTO(String operacao, Long id, String codigoSeguranca, String numeroCartao, Date dataValidade, Boolean preferido, String nomeImpresso, String bandeiraCartao, Cliente cliente) {
        super(operacao, id);
        this.codigoSeguranca = codigoSeguranca;
        this.numeroCartao = numeroCartao;
        this.dataValidade = dataValidade;
        this.preferido = preferido;
        this.nomeImpresso = nomeImpresso;
        this.bandeiraCartao = bandeiraCartao;
        this.cliente = cliente;
    }

    public String getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public void setCodigoSeguranca(String codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Boolean getPreferido() {
        return preferido;
    }

    public void setPreferido(Boolean preferido) {
        this.preferido = preferido;
    }

    public String getNomeImpresso() {
        return nomeImpresso;
    }

    public void setNomeImpresso(String nomeImpresso) {
        this.nomeImpresso = nomeImpresso;
    }

    public String getBandeiraCartao() {
        return bandeiraCartao;
    }

    public void setBandeiraCartao(String bandeiraCartao) {
        this.bandeiraCartao = bandeiraCartao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
