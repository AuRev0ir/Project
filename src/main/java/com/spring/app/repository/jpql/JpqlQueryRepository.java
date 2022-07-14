package com.spring.app.repository.jpql;

import com.spring.app.domain.Employee;

import java.util.List;

public interface JpqlQueryRepository {

    List<Employee> sortedEmployees(Long idOrganization);

    void deleteEmployeeById(Long idEmployee);

    void deleteOrganizationById(Long idOrganization);

}
