package com.example.security.full.security.app.repository;


import com.example.security.full.security.app.model.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {

}
