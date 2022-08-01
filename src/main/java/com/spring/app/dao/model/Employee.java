package com.spring.app.dao.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false )
    String lastName;

    String thirdName;

    @Column(nullable = false)
    String jobTitle;

    @Column(nullable = false)
    LocalDate hireDate;

    @ManyToOne(targetEntity = Organization.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id", nullable = false)
    Organization organization;


    public Employee(String firstName,
                    String lastName,
                    String thirdName,
                    String jobTitle,
                    LocalDate hireDate,
                    Organization organization) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.thirdName = thirdName;
        this.jobTitle = jobTitle;
        this.hireDate = hireDate;
        this.organization = organization;
    }
}
