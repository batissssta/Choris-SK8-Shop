package com.ecommerce.repositorio;

import com.ecommerce.dominio.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusPedidoRepositorio extends JpaRepository<StatusPedido, Long> {
    public StatusPedido findAllByDescricao(String descricao);
}
