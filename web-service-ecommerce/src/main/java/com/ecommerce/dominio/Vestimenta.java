package com.ecommerce.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Vestimenta extends EntidadeDominio {

    @ManyToOne(targetEntity = Marca.class)
    @JoinColumn(name = "id_marca")
    private Marca marca;


    @Column(name = "cor")
    private String cor;


    @Column(name = "tamanho")
    private String tamanho;

    @Column(name = "genero")
    private String genero;

    @ManyToOne(targetEntity = CategoriaVestimenta.class)
    @JoinColumn(name = "id_categoria")
    private CategoriaVestimenta categoriaVestimenta;

    @Column(name = "imagem")
    private String imagem;

    @Column(name = "valor_venda")
    private Double valor;

    public Vestimenta() {
    }

    public Vestimenta(Marca marca, String cor, String tamanho, String genero, CategoriaVestimenta categoriaVestimenta, String imagem, Double valor) {
        this.marca = marca;
        this.cor = cor;
        this.tamanho = tamanho;
        this.genero = genero;
        this.categoriaVestimenta = categoriaVestimenta;
        this.imagem = imagem;
        this.valor = valor;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public CategoriaVestimenta getCategoriaVestimenta() {
        return categoriaVestimenta;
    }

    public void setCategoriaVestimenta(CategoriaVestimenta categoriaVestimenta) {
        this.categoriaVestimenta = categoriaVestimenta;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
