package com.spring.app.rest.dto.registrationDto;

import com.spring.app.domain.Role;
import com.spring.app.domain.User;


import java.time.LocalDate;
import java.util.Set;

public class UserRegistrationDto {

    private String name;

    private String email;

    private LocalDate registrationDate;

    private String password;

    public UserRegistrationDto (String name, String email, LocalDate registrationDate) {
        this.name = name;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public static User toDomainObject (UserRegistrationDto dto, Set<Role> roles, String password) {
        return new User(
                dto.getName(),
                password,
                dto.getEmail(),
                dto.getRegistrationDate(),
                roles);
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
