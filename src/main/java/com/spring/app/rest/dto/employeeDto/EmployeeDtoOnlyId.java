package com.spring.app.rest.dto.employeeDto;

import com.spring.app.domain.Employee;

public class EmployeeDtoOnlyId {

    private Long id;

    public EmployeeDtoOnlyId(Long id) {
        this.id = id;
    }

    public static EmployeeDtoOnlyId toDto(Employee employee) {
        return new EmployeeDtoOnlyId (
                employee.getId()
        );
    }

    public Long getId() {
        return id;
    }
}
