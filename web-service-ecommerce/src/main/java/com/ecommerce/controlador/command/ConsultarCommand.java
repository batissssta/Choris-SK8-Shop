package com.ecommerce.controlador.command;

import com.ecommerce.controlador.Resultado;
import com.ecommerce.dominio.EntidadeDominio;

public class ConsultarCommand extends AbstractCommand {
    @Override
    public Resultado executar(EntidadeDominio entidade) {
        return fachada.consultar(entidade);
    }
}
