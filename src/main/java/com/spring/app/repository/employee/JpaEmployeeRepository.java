package com.spring.app.repository.employee;

import com.spring.app.domain.Employee;
import com.spring.app.repository.querys.JpqlQueryRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaEmployeeRepository extends CrudRepository <Employee, Long>, JpqlQueryRepository {

    List<Employee> findAll();

}
