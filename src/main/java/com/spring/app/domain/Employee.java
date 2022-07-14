package com.spring.app.domain;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEmployee;

    @Column(name = "name_employee", nullable = false)
    private String nameEmployee;

    @Column(name = "surname_employee", nullable = false )
    private String surnameEmployee;

    @Column(name = "patronymic_employee", nullable = false)
    private String patronymicEmployee;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "data_of_employment", nullable = false)
    private LocalDate dateOfEmployment;

    @ManyToOne(targetEntity = Organization.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;


    public Employee() {}

    public Employee(String nameEmployee, String surnameEmployee,
                    String patronymicEmployee, String jobTitle,
                    LocalDate dateOfEmployment, Organization organization) {

        this.nameEmployee = nameEmployee;
        this.surnameEmployee = surnameEmployee;
        this.patronymicEmployee = patronymicEmployee;
        this.jobTitle = jobTitle;
        this.dateOfEmployment = dateOfEmployment;
        this.organization = organization;
    }



    //get
    public long getIdEmployee() {
        return idEmployee;
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

    public Organization getOrganization() {
        return organization;
    }

    //set


    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public void setSurnameEmployee(String surnameEmployee) {
        this.surnameEmployee = surnameEmployee;
    }

    public void setPatronymicEmployee(String patronymicEmployee) {
        this.patronymicEmployee = patronymicEmployee;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setDateOfEmployment(LocalDate dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }
}
