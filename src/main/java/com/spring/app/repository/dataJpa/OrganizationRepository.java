package com.spring.app.repository.dataJpa;

import com.spring.app.domain.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    Optional<Organization> findByName(String name);
    List<Organization> findAll();
}
