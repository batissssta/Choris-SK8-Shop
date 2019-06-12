package com.ecommerce.negocio.cliente;

import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.negocio.IStrategy;

public class ValidaConfirmaSenha implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente)entidade;
        if(!(cliente.getSenha().equals(cliente.getConfirmarSenha()))) {
            return "As duas senhas devem coincidir";
        }
        return null;
    }

}
