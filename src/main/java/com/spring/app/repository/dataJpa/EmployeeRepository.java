package com.spring.app.repository.dataJpa;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;
import com.spring.app.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository <Employee, Long> {

    List<Employee> findAll();

}
