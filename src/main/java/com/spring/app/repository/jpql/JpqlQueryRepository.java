package com.spring.app.repository.jpql;

import com.spring.app.domain.Employee;

import java.util.List;

public interface JpqlQueryRepository {


    List<Employee> newMethodSorted(String name);

    void newMethodDeleteEmployeeById(Long idEmployee);

    void newMethodDeleteOrganizationById(String name);

}
