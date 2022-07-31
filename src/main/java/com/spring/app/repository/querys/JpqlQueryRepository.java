package com.spring.app.repository.querys;

import com.spring.app.domain.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JpqlQueryRepository {

    @Query(value = "SELECT s FROM Employee s " +
            "WHERE s.organization.name = ?1 " +
            "ORDER BY s.hireDate ASC")
    List<Employee> sortEmployeeByOrganizationName(String name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Employee s WHERE s.id = ?1 ")
    void removeEmployeeById(Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Organization s WHERE s.name = ?1 ")
    void removeOrganizationByName(String name);

}
