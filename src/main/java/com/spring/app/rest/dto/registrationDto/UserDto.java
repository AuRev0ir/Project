package com.spring.app.rest.dto.registrationDto;

import com.spring.app.domain.Organization;
import com.spring.app.domain.User;
import com.spring.app.rest.dto.organizationDto.OrganizationDto;

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
                user.getName(),
                user.getEmail(),
                user.getRegistrationDate(),
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

}
