package com.ecommerce.dto;

import java.util.Date;

public class PeriodoDTO {
    private Date dataInicial, dataFinal;
    Long idVestimenta;

    public PeriodoDTO(Date dataInicial, Date dataFinal) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public PeriodoDTO(Date dataInicial, Date dataFinal, Long idVestimenta) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.idVestimenta = idVestimenta;
    }

    public PeriodoDTO() {
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Long getIdVestimenta() {
        return idVestimenta;
    }

    public void setIdVestimenta(Long idVestimenta) {
        this.idVestimenta = idVestimenta;
    }
}
