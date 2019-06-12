package com.ecommerce.dominio;

import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class ItemEstoque extends EntidadeDominio {
    @OneToOne
    @JoinColumn(name = "id_vestimenta")
    private Vestimenta vestimenta;
    private Integer quantidade;
    private Date dataUltimaEntrada;
    private Date dataUltimaBaixa;

    public ItemEstoque(Vestimenta vestimenta, Integer quantidade, Date dataUltimaEntrada, Date dataUltimaBaixa) {
        this.vestimenta = vestimenta;
        this.quantidade = quantidade;
        this.dataUltimaEntrada = dataUltimaEntrada;
        this.dataUltimaBaixa = dataUltimaBaixa;
    }

    public ItemEstoque(Long id, Vestimenta vestimenta, Integer quantidade, Date dataUltimaEntrada, Date dataUltimaBaixa) {
        super(id);
        this.vestimenta = vestimenta;
        this.quantidade = quantidade;
        this.dataUltimaEntrada = dataUltimaEntrada;
        this.dataUltimaBaixa = dataUltimaBaixa;
    }

    public ItemEstoque() {
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

    public Date getDataUltimaEntrada() {
        return dataUltimaEntrada;
    }

    public void setDataUltimaEntrada(Date dataUltimaEntrada) {
        this.dataUltimaEntrada = dataUltimaEntrada;
    }

    public Date getDataUltimaBaixa() {
        return dataUltimaBaixa;
    }

    public void setDataUltimaBaixa(Date dataUltimaBaixa) {
        this.dataUltimaBaixa = dataUltimaBaixa;
    }
}
