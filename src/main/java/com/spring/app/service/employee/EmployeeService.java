package com.spring.app.service.employee;

import com.spring.app.rest.dto.employee.EmployeeFillFormDto;
import com.spring.app.rest.dto.employee.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto update (EmployeeFillFormDto dto, Long id);
    EmployeeDto create (EmployeeFillFormDto dto, String organizationName);
    List<EmployeeDto> getAll (String organizationName);
    String remove (Long id);
}
