package com.vivero.repositorios;

import com.vivero.entidades.Cliente;
import com.vivero.entidades.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepositorio extends JpaRepository<Compra, String> {
}
