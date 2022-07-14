package com.spring.app.services.servicerEmployee;

import com.spring.app.rest.dto.employeeDto.EmployeeDto;
import com.spring.app.rest.dto.employeeDto.EmployeeIdDto;
import com.spring.app.rest.pojo.FormEmployee;

import java.util.List;

public interface EmployeeService {

    EmployeeDto editingEmployee (FormEmployee formEmployee, Long idOrganization, Long idEmployee);
    EmployeeIdDto addEmployee(FormEmployee formEmployee, Long idOrganization);
    List<EmployeeDto> employeeList(Long idOrganization);

    String deleteEmployee(Long idOrganization, Long idEmployee);
}
