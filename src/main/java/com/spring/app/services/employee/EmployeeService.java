package com.spring.app.services.employee;

import com.spring.app.rest.dto.employee.EmployeeDto;
import com.spring.app.rest.dto.employee.EmployeeIdDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto updateEmployee(EmployeeDto dto, String organizationName, Long employeeId);
    EmployeeIdDto addEmployee(EmployeeDto dto, String name);
    List<EmployeeDto> getEmployees(String name);
    String removeEmployee(String organizationName, Long employeeId);
}
