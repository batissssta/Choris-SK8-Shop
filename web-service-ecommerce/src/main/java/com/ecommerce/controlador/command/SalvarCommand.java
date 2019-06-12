package com.ecommerce.controlador.command;

import com.ecommerce.controlador.Resultado;
import com.ecommerce.controlador.fachada.Fachada;
import com.ecommerce.dominio.EntidadeDominio;

public class SalvarCommand extends AbstractCommand {

    @Override
    public Resultado executar(EntidadeDominio entidade) {
        return fachada.salvar(entidade);
    }
}
