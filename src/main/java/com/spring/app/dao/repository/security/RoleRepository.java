package com.spring.app.dao.repository.security;

import com.spring.app.dao.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Set<Role>findAll();
    Optional<Role> findByName(String name);
}
