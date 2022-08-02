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

    Long id;

    String firstName;

    String lastName;

    String thirdName;

    String jobTitle;

    LocalDate hireDate;

}
