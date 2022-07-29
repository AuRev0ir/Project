package com.spring.app.services.servicerEmployee;

import com.spring.app.rest.dto.employeeDto.EmployeeDto;
import com.spring.app.rest.dto.employeeDto.EmployeeDtoOnlyId;

import java.util.List;

public interface EmployeeService {

    EmployeeDto updateEmployee(EmployeeDto dto, String name, Long idEmployee);
    EmployeeDtoOnlyId addEmployee(EmployeeDto dto, String name);
    List<EmployeeDto> getEmployees(String name);

    String removeEmployee(String name, Long idEmployee);
}
