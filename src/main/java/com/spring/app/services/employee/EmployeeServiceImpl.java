package com.spring.app.services.employee;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import com.spring.app.repository.employee.JpaEmployeeRepository;
import com.spring.app.repository.organization.JpaOrganizationRepository;
import com.spring.app.rest.dto.employee.EmployeeDto;
import com.spring.app.rest.dto.employee.EmployeeIdDto;
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
    public EmployeeDto updateEmployee(EmployeeDto dto, String organizationName, Long employeeId) {


        Organization organizationFromDB  = jpaOrganizationRepository.findByName(organizationName)
                .orElseThrow(RepositoryException::new);

        // Если employee существует, то получим его id, в ином случае получаем исключения
        Long id = jpaEmployeeRepository.sortEmployeeByOrganizationName(organizationFromDB.getName())
                .stream()
                .filter(employee -> Objects.equals(employee.getId(), employeeId))
                .findFirst().map(Employee::getId)
                .orElseThrow(ServiceException::new);

        // Получаем Employee с Repository
        Employee employeeFromDB  = jpaEmployeeRepository.findById(id)
                .orElseThrow(RepositoryException::new);

        // Update Employee
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

        return EmployeeDto.toDto(
                jpaEmployeeRepository.save(employeeFromDB));
    }

    @Override
    public EmployeeIdDto addEmployee(EmployeeDto dto, String name) {
        Organization organizationFromDB = jpaOrganizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);
        return EmployeeIdDto.toDto(
                jpaEmployeeRepository.save(EmployeeDto.toDomainObject(
                        dto, organizationFromDB)));
    }

    @Override
    public List<EmployeeDto> getEmployees(String name) {
        Organization organizationFromDB = jpaOrganizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);
        return jpaEmployeeRepository
                .sortEmployeeByOrganizationName(organizationFromDB.getName())
                .stream()
                .map(EmployeeDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String removeEmployee(String organizationName, Long employeeId) {

        Employee employeeFromDB = jpaEmployeeRepository.findById(employeeId)
                .orElseThrow(RepositoryException::new);
        Organization organizationFromDB = jpaOrganizationRepository.findByName(organizationName)
                .orElseThrow(RepositoryException::new);
        
        Long id = jpaEmployeeRepository.sortEmployeeByOrganizationName(organizationFromDB.getName())
                .stream()
                .filter(employee -> Objects.equals(employee.getId(),employeeFromDB.getId()))
                .findFirst()
                .map(Employee::getId)
                .orElseThrow(ServiceException::new);

        jpaEmployeeRepository.removeEmployeeById(id);
        
        return "Employee successfully deleted";

    }
}
