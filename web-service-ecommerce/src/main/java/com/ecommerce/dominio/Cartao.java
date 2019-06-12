package com.ecommerce.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="cartao")
public class Cartao extends EntidadeDominio {
    private String codigoSeguranca;
    @Column(name="numero")
    private String numeroCartao;
    @Column(name="validade")
    private Date dataValidade;
    private Boolean preferido;
    private String nomeImpresso;
    @Column(name="bandeira")
    private String bandeiraCartao;

    @ManyToOne(targetEntity = Cliente.class)
    @JoinColumn(name = "id_cliente")
    @JsonIgnore
    private Cliente cliente;

    public Cartao() {
    }

    public Cartao(String codigoSeguranca, String numeroCartao, Date dataValidade, Boolean preferido, String nomeImpresso, String bandeiraCartao, Cliente cliente) {
        this.codigoSeguranca = codigoSeguranca;
        this.numeroCartao = numeroCartao;
        this.dataValidade = dataValidade;
        this.preferido = preferido;
        this.nomeImpresso = nomeImpresso;
        this.bandeiraCartao = bandeiraCartao;
        this.cliente = cliente;
    }

    public Cartao(Long id, String codigoSeguranca, String numeroCartao, Date dataValidade, Boolean preferido, String nomeImpresso, String bandeiraCartao, Cliente cliente) {
        super(id);
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
