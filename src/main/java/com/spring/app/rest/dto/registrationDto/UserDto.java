package com.spring.app.rest.dto.registrationDto;

import com.spring.app.domain.User;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDto {

    private String name;

    private String email;

    private LocalDate registrationDate;

    private Set<RoleDto> roles;


    public UserDto(String name,
                   String email,
                   LocalDate registrationDate,
                   Set<RoleDto> roles) {
        this.name = name;
        this.email = email;
        this.registrationDate = registrationDate;
        this.roles = roles;
    }

    public static UserDto toDto(User user){
        return new UserDto(
                user.getNameUser(),
                user.getEmailUser(),
                user.getDateOfEmployment(),
                user.getRoles().stream().map(role -> RoleDto.toDto(role)).collect(Collectors.toSet()));
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

    public Set<RoleDto> getRoles() {
        return roles;
    }

    //set

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }
}
