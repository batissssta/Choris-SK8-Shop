package com.ecommerce.negocio.vestimenta;

import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.dominio.Vestimenta;
import com.ecommerce.negocio.IStrategy;

public class ValidaValorDeVenda implements IStrategy {
    @Override
    public String processar(EntidadeDominio entidade) {
        Vestimenta vestimenta = (Vestimenta)entidade;
        if(vestimenta.getValor() <= 0) {
            return "O valor de venda de uma vestimenta não pode ser menor ou igual a 0";
        }
        return null;
    }
}
