package com.spring.app.rest.dto.employee;

import com.spring.app.dao.model.Employee;
import com.spring.app.dao.model.Organization;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class EmployeeDto {


    String firstName;

    String lastName;

    String thirdName;

    String jobTitle;

    LocalDate hireDate;



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

}
