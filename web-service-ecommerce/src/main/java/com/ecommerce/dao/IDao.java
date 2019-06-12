package com.ecommerce.dao;

import com.ecommerce.dominio.EntidadeDominio;

import java.util.List;

public interface IDao {

    public void salvar(EntidadeDominio entidadeDominio);

    public void alterar(EntidadeDominio entidadeDominio);

    public void excluir(EntidadeDominio entidadeDominio);

    public List<EntidadeDominio> consultar(EntidadeDominio entidadeDominio);

}
