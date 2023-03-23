package com.example.security.full.security.app.repository;

import com.example.security.full.security.app.model.ERole;
import com.example.security.full.security.app.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
