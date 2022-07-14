package com.spring.app.rest.pojo;

import java.time.LocalDate;

public class FormEmployee {

    private String nameEmployee;
    private String surnameEmployee;
    private String patronymicEmployee;
    private String jobTitle;
    private LocalDate dateOfEmployment;


    public FormEmployee(String nameEmployee,
                        String surnameEmployee,
                        String patronymicEmployee,
                        String jobTitle,
                        LocalDate dateOfEmployment) {
        this.nameEmployee = nameEmployee;
        this.surnameEmployee = surnameEmployee;
        this.patronymicEmployee = patronymicEmployee;
        this.jobTitle = jobTitle;
        this.dateOfEmployment = dateOfEmployment;
    }

    //get
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
