package com.spring.app.services.servicerEmployee;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import com.spring.app.repository.dataJpa.EmployeeRepository;
import com.spring.app.repository.dataJpa.OrganizationRepository;
import com.spring.app.repository.jpql.JpqlQueryRepository;
import com.spring.app.rest.dto.employeeDto.EmployeeDto;
import com.spring.app.rest.dto.employeeDto.EmployeeDtoOnlyId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final JpqlQueryRepository jpqlQueryRepository;
    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;

    public EmployeeServiceImpl(JpqlQueryRepository jpqlQueryRepository,
                               EmployeeRepository employeeRepository,
                               OrganizationRepository organizationRepository) {
        this.jpqlQueryRepository = jpqlQueryRepository;
        this.employeeRepository = employeeRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto dto, Long idOrganization, Long idEmployee) {

        boolean isSuccessfully = false;

        Employee editingEmployee = employeeRepository.findById(idEmployee).orElseThrow(RepositoryException::new);
        List<Employee> employeeList = jpqlQueryRepository.sortedEmployees(idOrganization);
        for (Employee employee : employeeList) {
            if (Objects.equals(employee.getId(),idEmployee)) {
                if (!Objects.equals(employee.getFirstName(),dto.getFirstName())){
                    editingEmployee.setFirstName(dto.getFirstName());
                }
                if (!Objects.equals(employee.getLastName(), dto.getLastName())){
                    editingEmployee.setLastName(dto.getLastName());
                }
                if(!Objects.equals(employee.getThirdName(),dto.getThirdName())){
                    editingEmployee.setThirdName(dto.getThirdName());
                }
                if(!Objects.equals(employee.getJobTitle(),dto.getJobTitle())){
                    editingEmployee.setJobTitle(dto.getJobTitle());
                }
                if(!Objects.equals(employee.getHireDate(),dto.getHireDate())){
                    editingEmployee.setHireDate(dto.getHireDate());
                }

                isSuccessfully = true;
            }
        }

        if (!isSuccessfully){
            throw new ServiceException();
        }
        employeeRepository.save(editingEmployee);
        return EmployeeDto.toDto(editingEmployee);
    }

    @Override
    public EmployeeDtoOnlyId addEmployee(EmployeeDto dto, Long idOrganization) {
        Organization organizationById = organizationRepository.findById(idOrganization)
                .orElseThrow(RepositoryException::new);
        Employee newEmployee = EmployeeDto.toDomainObject(dto, organizationById);
        employeeRepository.save(newEmployee);
        return EmployeeDtoOnlyId.toDto(newEmployee);
    }

    @Override
    public List<EmployeeDto> getEmployees(Long idOrganization) {
        return jpqlQueryRepository
                .sortedEmployees(idOrganization)
                .stream()
                .map(EmployeeDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String removeEmployee(Long idOrganization, Long idEmployee) {
        boolean isSuccessfully = false;
        List<Employee> employeeList = jpqlQueryRepository.sortedEmployees(idOrganization);
        for (Employee employee : employeeList) {
            if(Objects.equals(employee.getId(), idEmployee)){
                jpqlQueryRepository.deleteEmployeeById(idEmployee);
                isSuccessfully = true;
            }
        }
        if (!isSuccessfully){
            throw new ServiceException(); // 500 ошибка
        }
        return "Employee successfully deleted";
    }
}
