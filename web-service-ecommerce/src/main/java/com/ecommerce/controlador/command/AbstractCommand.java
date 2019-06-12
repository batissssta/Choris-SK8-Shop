package com.ecommerce.controlador.command;

import com.ecommerce.controlador.fachada.Fachada;
import com.ecommerce.controlador.fachada.IFachada;

public abstract class AbstractCommand implements ICommand {
    protected IFachada fachada = new Fachada();
}
