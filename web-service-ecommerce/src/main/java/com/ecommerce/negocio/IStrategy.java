package com.ecommerce.negocio;

import com.ecommerce.dominio.EntidadeDominio;

public interface IStrategy {
	public String processar(EntidadeDominio entidade);
}

