package com.ecommerce.repositorio;

import com.ecommerce.dominio.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepositorio extends JpaRepository<Marca, Long> {
}
