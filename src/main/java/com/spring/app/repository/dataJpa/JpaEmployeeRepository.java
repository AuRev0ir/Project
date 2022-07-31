package com.spring.app.repository.dataJpa;

import com.spring.app.domain.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaEmployeeRepository extends CrudRepository <Employee, Long>, JpqlQueryRepository {

    List<Employee> findAll();

}
