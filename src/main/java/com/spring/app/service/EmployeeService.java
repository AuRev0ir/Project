package com.spring.app.service;

import com.spring.app.rest.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    void update (EmployeeDto dto, Long id);
    Long create (EmployeeDto dto, String organizationName);
    List<EmployeeDto> getAll (String organizationName);
    void remove (Long id);
}
