package com.example.security.full.security.app.repository;



import com.example.security.full.security.app.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MarcaRepository extends JpaRepository<Marca,Long> {
}
