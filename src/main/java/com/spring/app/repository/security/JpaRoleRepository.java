package com.spring.app.repository.security;

import com.spring.app.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface JpaRoleRepository extends CrudRepository<Role, Long> {

    Set<Role>findAll();

    Optional<Role> findByName(String name);
}
