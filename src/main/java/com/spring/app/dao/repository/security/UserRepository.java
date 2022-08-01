package com.spring.app.dao.repository.security;

import com.spring.app.dao.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);
    List<User> findAll();
}
