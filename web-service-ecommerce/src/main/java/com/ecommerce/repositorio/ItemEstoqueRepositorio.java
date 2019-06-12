package com.ecommerce.repositorio;

import com.ecommerce.dominio.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemEstoqueRepositorio extends JpaRepository<ItemEstoque, Long> {
    public ItemEstoque findByVestimenta_Id(Long id);
}
