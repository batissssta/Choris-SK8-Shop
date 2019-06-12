package com.ecommerce.negocio.cliente;

import com.ecommerce.dominio.Cliente;
import com.ecommerce.dominio.EntidadeDominio;
import com.ecommerce.negocio.IStrategy;

public class ValidaPadraoSenha implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidade) {
        Cliente cliente = (Cliente)entidade;

        if (!cliente.getSenha().matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$")) {
            return ("A senha deve conter no mínimo 8 caracteres, entre eles números e letras.");
        }
        return null;
    }

}
