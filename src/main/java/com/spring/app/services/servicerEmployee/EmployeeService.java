package com.spring.app.services.servicerEmployee;

import com.spring.app.rest.dto.employeeDto.EmployeeDto;
import com.spring.app.rest.dto.employeeDto.EmployeeDtoOnlyId;

import java.util.List;

public interface EmployeeService {

    EmployeeDto updateEmployee(EmployeeDto dto, Long idOrganization, Long idEmployee);
    EmployeeDtoOnlyId addEmployee(EmployeeDto dto, Long idOrganization);
    List<EmployeeDto> getEmployees(Long idOrganization);

    String removeEmployee(Long idOrganization, Long idEmployee);
}
