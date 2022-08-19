package com.spring.app.dao.repository;

import com.spring.app.dao.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM organizations s ORDER BY rating DESC")
    List<Organization> sortOrganizationsByRating();

    @Transactional // Эта аннотация не работает в интерфейсах
    @Modifying
    @Query(value = "DELETE FROM Organization s WHERE s.name = ?1 ")
    void removeOrganizationByName(String name);

    Optional<Organization> findByName(String name);

    boolean existsByName(String organizationName);
}
