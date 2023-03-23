package com.example.security.full.security.app.repository;


import com.example.security.full.security.app.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    List<Ciudad> findByPaisId(long id);

}
