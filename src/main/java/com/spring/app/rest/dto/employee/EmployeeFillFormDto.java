package com.spring.app.rest.dto.employee;


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
public class EmployeeFillFormDto {

    String firstName;

    String lastName;

    String thirdName;

    String jobTitle;

    LocalDate hireDate;

}
