package com.spring.app.repository.security;

import com.spring.app.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String nameUser);
    List<User> findAll();
}
