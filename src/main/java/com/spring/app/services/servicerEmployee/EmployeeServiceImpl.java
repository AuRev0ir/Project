package com.spring.app.services.servicerEmployee;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import com.spring.app.repository.dataJpa.EmployeeRepository;
import com.spring.app.repository.dataJpa.OrganizationRepository;
import com.spring.app.repository.jpql.JpqlQueryRepository;
import com.spring.app.rest.dto.employeeDto.EmployeeDto;
import com.spring.app.rest.dto.employeeDto.EmployeeIdDto;
import com.spring.app.rest.pojo.FormEmployee;
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
    public EmployeeDto editingEmployee(FormEmployee formEmployee, Long idOrganization, Long idEmployee) {

        boolean isSuccessfully = false;

        Employee editingEmployee = employeeRepository.findById(idEmployee).orElseThrow(RepositoryException::new);
        List<Employee> employeeList = jpqlQueryRepository.sortedEmployees(idOrganization);
        for (Employee employee : employeeList) {
            if (Objects.equals(employee.getIdEmployee(),idEmployee)) {
                if (!Objects.equals(employee.getNameEmployee(),formEmployee.getNameEmployee())){
                    editingEmployee.setNameEmployee(formEmployee.getNameEmployee());
                }
                if (!Objects.equals(employee.getSurnameEmployee(),formEmployee.getSurnameEmployee())){
                    editingEmployee.setSurnameEmployee(formEmployee.getSurnameEmployee());
                }
                if(!Objects.equals(employee.getPatronymicEmployee(),formEmployee.getPatronymicEmployee())){
                    editingEmployee.setPatronymicEmployee(formEmployee.getPatronymicEmployee());
                }
                if(!Objects.equals(employee.getJobTitle(),formEmployee.getJobTitle())){
                    editingEmployee.setJobTitle(formEmployee.getJobTitle());
                }
                if(!Objects.equals(employee.getDateOfEmployment(),formEmployee.getDateOfEmployment())){
                    editingEmployee.setDateOfEmployment(formEmployee.getDateOfEmployment());
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
    public EmployeeIdDto addEmployee(FormEmployee formEmployee, Long idOrganization) {
        Organization organizationById = organizationRepository.findById(idOrganization)
                .orElseThrow(RepositoryException::new);
        Employee newEmployee = new Employee(
                formEmployee.getNameEmployee(),
                formEmployee.getPatronymicEmployee(),
                formEmployee.getSurnameEmployee(),
                formEmployee.getJobTitle(),
                formEmployee.getDateOfEmployment(), organizationById);
        employeeRepository.save(newEmployee);
        return EmployeeIdDto.toDto(newEmployee);
    }

    @Override
    public List<EmployeeDto> employeeList(Long idOrganization) {
        return jpqlQueryRepository
                .sortedEmployees(idOrganization)
                .stream()
                .map(EmployeeDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteEmployee(Long idOrganization, Long idEmployee) {
        boolean isSuccessfully = false;
        List<Employee> employeeList = jpqlQueryRepository.sortedEmployees(idOrganization);
        for (Employee employee : employeeList) {
            if(Objects.equals(employee.getIdEmployee(), idEmployee)){
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
