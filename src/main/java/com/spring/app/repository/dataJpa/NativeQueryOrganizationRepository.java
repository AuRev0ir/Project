package com.spring.app.repository.dataJpa;

import com.spring.app.domain.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NativeQueryOrganizationRepository extends CrudRepository<Organization,Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM organizations s ORDER BY rating DESC")
    List<Organization> sortOrganizationsByRating();
}
