package com.example.security.full.security.app.repository;


import com.example.security.full.security.app.model.Sucursal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal,Long> {
    List<Sucursal> findSucursalByCiudadId(Long ciudadId);



}
