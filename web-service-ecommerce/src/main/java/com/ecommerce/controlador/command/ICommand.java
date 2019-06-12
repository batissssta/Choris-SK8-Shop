package com.ecommerce.controlador.command;

import com.ecommerce.controlador.Resultado;
import com.ecommerce.controlador.fachada.Fachada;
import com.ecommerce.dominio.EntidadeDominio;

public interface ICommand {
    public Resultado executar(EntidadeDominio entidade);
}
