package com.ecommerce.repositorio;

import com.ecommerce.dominio.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ItemPedidoRepositorio extends JpaRepository<ItemPedido, Long> {

}
