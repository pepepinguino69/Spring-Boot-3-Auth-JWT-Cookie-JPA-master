package com.example.security.full.security.app.repository;


import com.example.security.full.security.app.model.Vehiculo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo,Long>, JpaSpecificationExecutor<Vehiculo> {
    List<Vehiculo> findVehiculoBySucursalId(Long sucursalId);
    List<Vehiculo> findVehiculoByModeloCategoriaId(Long categoriaId);
}
