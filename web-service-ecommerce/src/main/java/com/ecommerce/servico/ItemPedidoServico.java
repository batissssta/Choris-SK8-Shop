package com.ecommerce.servico;

import com.ecommerce.dominio.ItemPedido;
import com.ecommerce.repositorio.ItemPedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoServico {
    @Autowired
    ItemPedidoRepositorio itemPedidoRepositorio;

    public void salvar(ItemPedido itemPedido) {
        itemPedidoRepositorio.saveAndFlush(itemPedido);
    }

    public void editar(ItemPedido itemPedido, Long id) {
        itemPedidoRepositorio.save(itemPedido);
    }

}
