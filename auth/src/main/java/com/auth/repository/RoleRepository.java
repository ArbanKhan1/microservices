package com.auth.repository;

import com.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByNameIn(List<String> roles);
    Optional<Role> findByName(String role);
}