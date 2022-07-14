package com.spring.app.rest.dto.employeeDto;

import com.spring.app.domain.Employee;

import java.time.LocalDate;

public class EmployeeDto {

    private Long id;
    private String nameEmployee;

    private String surnameEmployee;

    private String patronymicEmployee;

    private String jobTitle;

    private LocalDate dateOfEmployment;

    public EmployeeDto(Long id,
                       String nameEmployee,
                       String surnameEmployee,
                       String patronymicEmployee,
                       String jobTitle,
                       LocalDate dateOfEmployment) {
        this.nameEmployee = nameEmployee;
        this.surnameEmployee = surnameEmployee;
        this.patronymicEmployee = patronymicEmployee;
        this.jobTitle = jobTitle;
        this.dateOfEmployment = dateOfEmployment;
        this.id = id;
    }

    public static EmployeeDto toDto(Employee employee){
        return new EmployeeDto(
                employee.getIdEmployee(),
                employee.getNameEmployee(),
                employee.getSurnameEmployee(),
                employee.getPatronymicEmployee(),
                employee.getJobTitle(),
                employee.getDateOfEmployment());
    }

    //get

    public Long getId() {
        return id;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public String getSurnameEmployee() {
        return surnameEmployee;
    }

    public String getPatronymicEmployee() {
        return patronymicEmployee;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }
}
