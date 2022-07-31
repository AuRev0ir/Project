package com.spring.app.rest.dto.employee;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;

import java.time.LocalDate;

public class EmployeeDto {
    private String firstName;

    private String lastName;

    private String thirdName;

    private String jobTitle;

    private LocalDate hireDate;

    public EmployeeDto(
                       String firstName,
                       String lastName,
                       String thirdName,
                       String jobTitle,
                       LocalDate hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.thirdName = thirdName;
        this.jobTitle = jobTitle;
        this.hireDate = hireDate;
    }

    public static EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getThirdName(),
                employee.getJobTitle(),
                employee.getHireDate());
    }

    public static Employee toDomainObject (EmployeeDto dto, Organization organization) {
        return new Employee(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getThirdName(),
                dto.getJobTitle(),
                dto.getHireDate(),
                organization);
    }

    //get

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }
}
