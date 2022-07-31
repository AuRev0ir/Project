package com.spring.app.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false )
    private String lastName;

    private String thirdName;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private LocalDate hireDate;

    @ManyToOne(targetEntity = Organization.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    public Employee() {}

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



    //get
    public long getId() {
        return id;
    }

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

    public Organization getOrganization() {
        return organization;
    }

    //set


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}
