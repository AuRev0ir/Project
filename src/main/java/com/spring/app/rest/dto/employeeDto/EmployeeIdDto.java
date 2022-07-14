package com.spring.app.rest.dto.employeeDto;

import com.spring.app.domain.Employee;

public class EmployeeIdDto {

    private Long idEmployee;

    public EmployeeIdDto(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public static EmployeeIdDto toDto(Employee employee){
        return new EmployeeIdDto(employee.getIdEmployee());
    }

    //get
    public Long getIdEmployee() {
        return idEmployee;
    }
}
