package com.spring.app.service.employee;

import com.spring.app.rest.dto.employee.EmployeeDto;
import com.spring.app.rest.dto.employee.EmployeeIdDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto update (EmployeeDto dto, Long id);
    EmployeeIdDto create (EmployeeDto dto, String organizationName);
    List<EmployeeDto> getAll (String organizationName);
    String remove (Long id);
}
