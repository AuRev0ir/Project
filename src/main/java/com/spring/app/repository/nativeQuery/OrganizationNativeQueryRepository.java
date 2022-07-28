package com.spring.app.repository.nativeQuery;

import com.spring.app.domain.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganizationNativeQueryRepository extends CrudRepository<Organization,Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM Organizations s ORDER BY organization_rating DESC")
    List<Organization> sortOrganizationsByRating();
}
