package com.spring.app.services.servicerEmployee;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import com.spring.app.repository.dataJpa.JpaEmployeeRepository;
import com.spring.app.repository.dataJpa.JpaOrganizationRepository;
import com.spring.app.rest.dto.employeeDto.EmployeeDto;
import com.spring.app.rest.dto.employeeDto.EmployeeDtoOnlyId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final JpaEmployeeRepository jpaEmployeeRepository;
    private final JpaOrganizationRepository jpaOrganizationRepository;

    public EmployeeServiceImpl(
                               JpaEmployeeRepository jpaEmployeeRepository,
                               JpaOrganizationRepository jpaOrganizationRepository) {
        this.jpaEmployeeRepository = jpaEmployeeRepository;
        this.jpaOrganizationRepository = jpaOrganizationRepository;
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto dto, String name, Long id) {


        Organization organizationFromTheRepository  = jpaOrganizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);

        // Если employee существует, то получим его id, в ином случае получаем исключения
        Long idUpdatedEmployee = jpaEmployeeRepository.newMethodSortedPlus(organizationFromTheRepository.getName())
                .stream()
                .filter(employee -> Objects.equals(employee.getId(), id))
                .findFirst().map(Employee::getId)
                .orElseThrow(ServiceException::new);

        // Получаем Employee с Repository
        Employee employeeFromTheRepository  = jpaEmployeeRepository.findById(idUpdatedEmployee)
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
                jpaEmployeeRepository.save(employeeFromTheRepository));
    }

    @Override
    public EmployeeDtoOnlyId addEmployee(EmployeeDto dto, String name) {
        Organization organizationFromTheRepository = jpaOrganizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);
        return EmployeeDtoOnlyId.toDto(
                jpaEmployeeRepository.save(EmployeeDto.toDomainObject(
                        dto, organizationFromTheRepository)));
    }

    @Override
    public List<EmployeeDto> getEmployees(String name) {
        Organization organizationFromTheRepository = jpaOrganizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);
        return jpaEmployeeRepository
                .newMethodSortedPlus(organizationFromTheRepository.getName())
                .stream()
                .map(EmployeeDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String removeEmployee(String name, Long id) {

        Employee employeeFromTheRepository = jpaEmployeeRepository.findById(id)
                .orElseThrow(RepositoryException::new);
        Organization organizationFromTheRepository = jpaOrganizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);
        
        Long employeeId = jpaEmployeeRepository.newMethodSortedPlus(organizationFromTheRepository.getName())
                .stream()
                .filter(employee -> Objects.equals(employee.getId(),employeeFromTheRepository.getId()))
                .findFirst()
                .map(Employee::getId)
                .orElseThrow(ServiceException::new);

        jpaEmployeeRepository.newMethodDeleteEmployeeById(employeeId);
        
        return "Employee successfully deleted";

    }
}
