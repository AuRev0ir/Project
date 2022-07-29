package com.spring.app.repository.dataJpa;

import com.spring.app.domain.Organization;
import com.spring.app.repository.jpql.JpqlNew;
import com.spring.app.repository.nativeQuery.OrganizationNativeQueryRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends CrudRepository<Organization, Long>, JpqlNew, OrganizationNativeQueryRepository {

    Optional<Organization> findByName(String name);
    List<Organization> findAll();
}
