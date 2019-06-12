package com.ecommerce.controlador.restController;


import com.ecommerce.dominio.ItemEstoque;
import com.ecommerce.servico.ItemEstoqueServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("itens-estoque")
public class ItemEstoqueController {

    @Autowired
    ItemEstoqueServico itemEstoqueServico;

    @PostMapping
    @RequestMapping(value="/salvar" ,  method = RequestMethod.POST)
    public String salvar(@RequestBody ItemEstoque itemEstoque) {
        return itemEstoqueServico.salvar(itemEstoque);
    }

    @GetMapping
    public List<ItemEstoque> consultar() {
        return itemEstoqueServico.consultar();
    }

    @PostMapping("/editar/{id}")
    public String editar(@RequestBody ItemEstoque itemEstoque, @PathVariable Long id) {
        return itemEstoqueServico.editar(itemEstoque, id);
    }

    @GetMapping("excluir/{id}")
    public void excluir(@PathVariable  Long id) {
        itemEstoqueServico.excluir(id);
    }

    @GetMapping("/{id}")
    public ItemEstoque buscarPorId (@PathVariable  Long id) {
        return itemEstoqueServico.buscarPorId(id);
    }

    @GetMapping("/vestimenta/{id}")
    public ItemEstoque buscarPorIdVestimenta (@PathVariable  Long id) {
        return itemEstoqueServico.buscarPorIdVestimenta(id);
    }


}
