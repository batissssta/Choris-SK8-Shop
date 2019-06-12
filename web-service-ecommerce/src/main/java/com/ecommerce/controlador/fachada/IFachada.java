package com.ecommerce.controlador.fachada;

import com.ecommerce.controlador.Resultado;
import com.ecommerce.dominio.EntidadeDominio;

import java.util.List;

public interface IFachada {

    public Resultado salvar(EntidadeDominio entidade);
    public Resultado alterar(EntidadeDominio entidade);
    public Resultado excluir(EntidadeDominio entidade);
    public Resultado consultar(EntidadeDominio entidade);

}
