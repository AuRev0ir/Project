package com.spring.app.rest.pojo;

import com.spring.app.domain.Role;

import java.time.LocalDate;
import java.util.Set;

public class FormRegistration {

    private String nameNewUser;

    private String emailNewUser;

    private LocalDate dateOfEmploymentNewUser;

    private String passwordNewUser;

    private Set<String> roleNewUser;

    public FormRegistration(String nameNewUser,
                            String emailNewUser,
                            LocalDate dateOfEmploymentNewUser,
                            String passwordNewUser,
                            Set<String> roleNewUser) {
        this.nameNewUser = nameNewUser;
        this.emailNewUser = emailNewUser;
        this.dateOfEmploymentNewUser = dateOfEmploymentNewUser;
        this.passwordNewUser = passwordNewUser;
        this.roleNewUser = roleNewUser;
    }


    //get
    public String getNameNewUser() {
        return nameNewUser;
    }

    public String getEmailNewUser() {
        return emailNewUser;
    }

    public LocalDate getDateOfEmploymentNewUser() {
        return dateOfEmploymentNewUser;
    }

    public String getPasswordNewUser() {
        return passwordNewUser;
    }

    public Set<String> getRolesNewUser() {
        return roleNewUser;
    }

    //set

    public void setNameNewUser(String nameNewUser) {
        this.nameNewUser = nameNewUser;
    }

    public void setEmailNewUser(String emailNewUser) {
        this.emailNewUser = emailNewUser;
    }

    public void setDateOfEmploymentNewUser(LocalDate dateOfEmploymentNewUser) {
        this.dateOfEmploymentNewUser = dateOfEmploymentNewUser;
    }

    public void setPasswordNewUser(String passwordNewUser) {
        this.passwordNewUser = passwordNewUser;
    }

    public void setRoleNewUser(Set<String> roleNewUser) {
        this.roleNewUser = roleNewUser;
    }
}
