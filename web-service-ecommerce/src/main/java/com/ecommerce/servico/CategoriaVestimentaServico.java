package com.ecommerce.servico;

import com.ecommerce.dominio.CategoriaVestimenta;
import com.ecommerce.repositorio.CategoriaVestimentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaVestimentaServico {

    @Autowired
    CategoriaVestimentaRepositorio categoriaVestimentaRepositorio;

    public List<CategoriaVestimenta> consultar() {
        return categoriaVestimentaRepositorio.findAll();
    }

}
