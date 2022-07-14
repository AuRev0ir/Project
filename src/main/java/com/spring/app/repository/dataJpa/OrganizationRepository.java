package com.spring.app.repository.dataJpa;

import com.spring.app.domain.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    List<Organization> findAll();
}
