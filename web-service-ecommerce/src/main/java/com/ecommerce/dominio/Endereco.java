package com.ecommerce.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="endereco")
public class Endereco extends EntidadeDominio {
    private String logradouro;
    private String numero;
    private String cep;
    private String tipoLogradouro;
    private String tipoResidencia;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private String tipoEndereco;

    @ManyToOne(targetEntity = Cliente.class)
    @JoinColumn(name = "id_cliente")
    @JsonIgnore
    private Cliente cliente;

    public Endereco() {
    }

    public Endereco(String logradouro, String numero, String cep, String tipoLogradouro, String tipoResidencia, String bairro, String cidade, String estado, String pais, String tipoEndereco, Cliente cliente) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.tipoLogradouro = tipoLogradouro;
        this.tipoResidencia = tipoResidencia;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.tipoEndereco = tipoEndereco;
        this.cliente = cliente;
    }

    public Endereco(Long id, String logradouro, String numero, String cep, String tipoLogradouro, String tipoResidencia, String bairro, String cidade, String estado, String pais, String tipoEndereco, Cliente cliente) {
        super(id);
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.tipoLogradouro = tipoLogradouro;
        this.tipoResidencia = tipoResidencia;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.tipoEndereco = tipoEndereco;
        this.cliente = cliente;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getTipoResidencia() {
        return tipoResidencia;
    }

    public void setTipoResidencia(String tipoResidencia) {
        this.tipoResidencia = tipoResidencia;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(String tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
