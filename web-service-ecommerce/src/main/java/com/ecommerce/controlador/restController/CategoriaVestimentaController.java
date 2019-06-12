package com.ecommerce.controlador.restController;

import com.ecommerce.dominio.CategoriaVestimenta;
import com.ecommerce.repositorio.CategoriaVestimentaRepositorio;
import com.ecommerce.servico.CategoriaVestimentaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categoriasVestimenta")
public class CategoriaVestimentaController {

    @Autowired
    CategoriaVestimentaServico categoriaVestimentaServico;

    @GetMapping
    public List<CategoriaVestimenta> consultar() {
        return categoriaVestimentaServico.consultar();
    }

}
