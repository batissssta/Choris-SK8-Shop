package com.ecommerce.repositorio;

import com.ecommerce.dominio.Troca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrocaRepositorio extends JpaRepository<Troca, Long> {

    Troca findByPedido_Id(Long id);

}
