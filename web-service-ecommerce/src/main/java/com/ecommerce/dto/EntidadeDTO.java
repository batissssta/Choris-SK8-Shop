package com.ecommerce.dto;

public class EntidadeDTO {
    private String operacao;
    private Long id;

    public EntidadeDTO(String operacao, Long id) {
        this.operacao = operacao;
        this.id = id;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
