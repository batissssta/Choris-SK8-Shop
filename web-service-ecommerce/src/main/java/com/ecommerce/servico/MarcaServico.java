package com.ecommerce.servico;

import com.ecommerce.dominio.Marca;
import com.ecommerce.repositorio.MarcaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaServico {

    @Autowired
    MarcaRepositorio marcaRepositorio;

    public List<Marca> consultar() {
        return marcaRepositorio.findAll();
    }
}
