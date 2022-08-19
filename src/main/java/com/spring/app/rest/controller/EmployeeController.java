package com.spring.app.rest.controller;

import com.spring.app.rest.dto.EmployeeDto;
import com.spring.app.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController implements EmployeeApi { // Чтобы появились эти интерфейсы и DTO необходимо скомпилировать проект

    EmployeeService employeeService;

    @Override
    public ResponseEntity<Long> addNewEmployee(String organizationName, EmployeeDto employeeDto) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(employeeService.create(employeeDto, organizationName));
    }

    @Override
    public ResponseEntity<Void> deleteEmployeeById(Long id) {
        employeeService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<EmployeeDto>> getAllHiredEmployeesInOrganization(String organizationName) {
        return ResponseEntity.ok(employeeService.getAll(organizationName));
    }

    @Override
    public ResponseEntity<Void> updateEmployeeData(Long id, EmployeeDto employeeDto) {
        employeeService.update(employeeDto, id);
        return ResponseEntity.noContent().build();
    }
}
