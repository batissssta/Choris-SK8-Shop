package com.ecommerce.controlador.restController;

import com.ecommerce.dominio.Troca;
import com.ecommerce.servico.TrocaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("trocas")
public class TrocaController {
    @Autowired
    TrocaServico trocaServico;

    @PostMapping
    @RequestMapping(value="/salvar" ,  method = RequestMethod.POST)
    public String salvar(@RequestBody Troca troca) {
        return trocaServico.salvar(troca);
    }

    @GetMapping
    public List<Troca> consultar() {
        return trocaServico.consultar();
    }

    @GetMapping("/{id}")
    public Troca buscarPorId (@PathVariable  Long id) {
        return trocaServico.buscarPorId(id);
    }

    @PostMapping("/buscarPorIdPedido/{id}")
    public Troca buscarPorIdPedido (@PathVariable  Long id) {
        return trocaServico.buscarPorIdPedido(id);
    }

}
