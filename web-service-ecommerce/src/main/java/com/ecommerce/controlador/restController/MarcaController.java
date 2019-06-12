package com.ecommerce.controlador.restController;

import com.ecommerce.dominio.Marca;
import com.ecommerce.servico.MarcaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("marcas")
public class MarcaController {

    @Autowired
    MarcaServico marcaServico;

    @GetMapping
    public List<Marca> consultar() {
        return marcaServico.consultar();
    }

}
