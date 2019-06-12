package com.ecommerce.servico;

import com.ecommerce.dominio.ItemPedido;
import com.ecommerce.dominio.ItemTroca;
import com.ecommerce.repositorio.ItemPedidoRepositorio;
import com.ecommerce.repositorio.ItemTrocaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemTrocaServico {
    @Autowired
    ItemTrocaRepositorio itemTrocaRepositorio;

    public void salvar(ItemTroca itemTroca) {
        itemTrocaRepositorio.saveAndFlush(itemTroca);
    }

    public void editar(ItemTroca itemTroca, Long id) {
        itemTrocaRepositorio.save(itemTroca);
    }

}
