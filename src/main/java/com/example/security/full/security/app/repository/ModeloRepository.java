package com.example.security.full.security.app.repository;


import com.example.security.full.security.app.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    List<Modelo> findByNombre(String nombre);
    List<Modelo> findByCategoriaId(Long categoriaId);


}
