package com.spring.app.rest.pojo;

import java.time.LocalDate;
import java.util.Set;

public class FormRegistration {

    private String name;

    private String email;

    private LocalDate registrationDate;

    private String password;

    public FormRegistration(String name,
                            String email,
                            LocalDate registrationDate,
                            String password,
                            Set<String> roleNewUser) {
        this.name = name;
        this.email = email;
        this.registrationDate = registrationDate;
        this.password = password;
    }


    //get
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public String getPassword() {
        return password;
    }


}
