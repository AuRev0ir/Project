package com.spring.app.service.impl;

import com.spring.app.dao.repository.EmployeeRepository;
import com.spring.app.dao.repository.OrganizationRepository;
import com.spring.app.exception.NotFoundEntityException;
import com.spring.app.rest.dto.EmployeeDto;
import com.spring.app.rest.mapper.EmployeeMapper;
import com.spring.app.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;
    OrganizationRepository organizationRepository;
    EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public void update (EmployeeDto employeeFillFormDto, Long id) {
        var employeeFromDB  = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Employee not found in database"));
        employeeMapper.updateFromDto(employeeFillFormDto, employeeFromDB);
        employeeRepository.save(employeeFromDB);
    }

    @Override
    @Transactional
    public Long create (EmployeeDto employeeFillFormDto, String organizationName) {
        var organizationFromDB = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NotFoundEntityException("Organization not found in database"));
        var newEmployee = employeeMapper.toEntity(employeeFillFormDto);
        newEmployee.setOrganization(organizationFromDB);
        var employee = employeeRepository.save(newEmployee);
        return employee.getId();
    }

    @Override
    public List<EmployeeDto> getAll (String organizationName) {
        var organizationFromDB = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NotFoundEntityException("Organization not found in database"));
        return employeeRepository
                .findEmployeesByOrganizationName(organizationFromDB.getName())
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void remove (Long id) {
        var employeeFromDB = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Employee not found in database"));
        employeeRepository.removeEmployeeById(employeeFromDB.getId());
    }
}
