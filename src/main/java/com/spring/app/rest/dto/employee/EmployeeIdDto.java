package com.spring.app.rest.dto.employee;

import com.spring.app.dao.model.Employee;

public class EmployeeIdDto {

    private Long id;

    public EmployeeIdDto(Long id) {
        this.id = id;
    }

    public static EmployeeIdDto toDto(Employee employee) {
        return new EmployeeIdDto(
                employee.getId()
        );
    }

    public Long getId() {
        return id;
    }
}
