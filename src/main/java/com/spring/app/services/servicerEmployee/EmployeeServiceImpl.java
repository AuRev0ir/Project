package com.spring.app.services.servicerEmployee;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import com.spring.app.repository.dataJpa.EmployeeRepository;
import com.spring.app.repository.dataJpa.OrganizationRepository;
import com.spring.app.rest.dto.employeeDto.EmployeeDto;
import com.spring.app.rest.dto.employeeDto.EmployeeDtoOnlyId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;

    public EmployeeServiceImpl(
                               EmployeeRepository employeeRepository,
                               OrganizationRepository organizationRepository) {
        this.employeeRepository = employeeRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto dto, String name, Long id) {


        Organization organizationFromTheRepository  = organizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);

        // Если employee существует, то получим его id, в ином случае получаем исключения
        Long idUpdatedEmployee = employeeRepository.newMethodSortedPlus(organizationFromTheRepository.getName())
                .stream()
                .filter(employee -> Objects.equals(employee.getId(), id))
                .findFirst().map(Employee::getId)
                .orElseThrow(ServiceException::new);

        // Получаем Employee с Repository
        Employee employeeFromTheRepository  = employeeRepository.findById(idUpdatedEmployee)
                .orElseThrow(RepositoryException::new);

        // Update Employee
        if(!Objects.equals(employeeFromTheRepository.getFirstName(),dto.getFirstName())){
            employeeFromTheRepository.setFirstName(dto.getFirstName());
        }
        if(!Objects.equals(employeeFromTheRepository.getLastName(),dto.getLastName())){
            employeeFromTheRepository.setLastName(dto.getLastName());
        }
        if(!Objects.equals(employeeFromTheRepository.getThirdName(),dto.getThirdName())){
            employeeFromTheRepository.setThirdName(dto.getThirdName());
        }
        if(!Objects.equals(employeeFromTheRepository.getJobTitle(),dto.getJobTitle())){
            employeeFromTheRepository.setJobTitle(dto.getJobTitle());
        }
        if (!Objects.equals(employeeFromTheRepository.getHireDate(),dto.getHireDate())){
            employeeFromTheRepository.setHireDate(dto.getHireDate());
        }

        return EmployeeDto.toDto(
                employeeRepository.save(employeeFromTheRepository));
    }

    @Override
    public EmployeeDtoOnlyId addEmployee(EmployeeDto dto, String name) {
        Organization organizationFromTheRepository = organizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);
        return EmployeeDtoOnlyId.toDto(
                employeeRepository.save(EmployeeDto.toDomainObject(
                        dto, organizationFromTheRepository)));
    }

    @Override
    public List<EmployeeDto> getEmployees(String name) {
        Organization organizationFromTheRepository = organizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);
        return employeeRepository
                .newMethodSortedPlus(organizationFromTheRepository.getName())
                .stream()
                .map(EmployeeDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String removeEmployee(String name, Long id) {

        Employee employeeFromTheRepository = employeeRepository.findById(id)
                .orElseThrow(RepositoryException::new);
        Organization organizationFromTheRepository = organizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);
        
        Long employeeId = employeeRepository.newMethodSortedPlus(organizationFromTheRepository.getName())
                .stream()
                .filter(employee -> Objects.equals(employee.getId(),employeeFromTheRepository.getId()))
                .findFirst()
                .map(Employee::getId)
                .orElseThrow(ServiceException::new);

        employeeRepository.newMethodDeleteEmployeeById(employeeId);
        
        return "Employee successfully deleted";

    }
}
