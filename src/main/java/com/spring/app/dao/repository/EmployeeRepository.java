package com.spring.app.dao.repository;

import com.spring.app.dao.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    @Query(value = "SELECT s FROM Employee s " +
            "WHERE s.organization.name = ?1 " +
            "ORDER BY s.hireDate ASC")
    List<Employee> findEmployeesByOrganizationName(String organizationName);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Employee s WHERE s.id = ?1 ")
    void removeEmployeeById(Long id);

    void deleteAllByOrganization_Id(Long organizationId);
}
