package com.spring.app.service.employee;

import com.spring.app.dao.model.Employee;
import com.spring.app.dao.model.Organization;
import com.spring.app.exception.NotFoundEntityException;
import com.spring.app.dao.repository.employee.EmployeeRepository;
import com.spring.app.dao.repository.organization.OrganizationRepository;
import com.spring.app.rest.dto.employee.EmployeeDto;
import com.spring.app.rest.dto.employee.EmployeeIdDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;
    OrganizationRepository organizationRepository;

    @Override
    public EmployeeDto update (EmployeeDto dto, Long id) {

        Employee employeeFromDB  = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Employee not found in database"));

        if(!Objects.equals(employeeFromDB.getFirstName(),dto.getFirstName())){
            employeeFromDB.setFirstName(dto.getFirstName());
        }
        if(!Objects.equals(employeeFromDB.getLastName(),dto.getLastName())){
            employeeFromDB.setLastName(dto.getLastName());
        }
        if(!Objects.equals(employeeFromDB.getThirdName(),dto.getThirdName())){
            employeeFromDB.setThirdName(dto.getThirdName());
        }
        if(!Objects.equals(employeeFromDB.getJobTitle(),dto.getJobTitle())){
            employeeFromDB.setJobTitle(dto.getJobTitle());
        }
        if (!Objects.equals(employeeFromDB.getHireDate(),dto.getHireDate())){
            employeeFromDB.setHireDate(dto.getHireDate());
        }

        return EmployeeDto.toDto(employeeRepository.save(employeeFromDB));
    }

    @Override
    public EmployeeIdDto create (EmployeeDto dto, String organizationName) {
        Organization organizationFromDB = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NotFoundEntityException("Organization not found in database"));
        return EmployeeIdDto.toDto(
                employeeRepository.save(EmployeeDto.toDomainObject(
                        dto, organizationFromDB)));
    }

    @Override
    public List<EmployeeDto> getAll (String organizationName) {
        Organization organizationFromDB = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NotFoundEntityException("Organization not found in database"));
        return employeeRepository
                .findEmployeesByOrganizationName(organizationFromDB.getName())
                .stream()
                .map(EmployeeDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String remove (Long id) {

        Employee employeeFromDB = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Employee not found in database"));

        employeeRepository.removeEmployeeById(employeeFromDB.getId());

        return "Employee successfully deleted";

    }
}
