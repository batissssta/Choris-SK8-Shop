package com.ecommerce.controlador.viewHelper;

import com.ecommerce.controlador.Resultado;
import com.ecommerce.dominio.EntidadeDominio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IViewHelper {

    public EntidadeDominio getEntidade(String json) throws IOException;

    public void setView(Resultado resultado, String json, HttpServletResponse response) throws IOException;


}

