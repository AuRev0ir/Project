package com.spring.app.dao.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_seq_gen")
    @SequenceGenerator(name = "employee_id_seq_gen", sequenceName = "employees_id_seq", allocationSize = 1)
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
