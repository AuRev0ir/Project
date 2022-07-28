package com.spring.app.rest.dto.registrationDto;

import com.spring.app.domain.Role;

public class RoleDto {
    private String role;

    public RoleDto(String role) {
        this.role = role;
    }

    public static RoleDto toDto (Role role){
        return new RoleDto(role.getName());
    }

    //get
    public String getRole() {
        return role;
    }
}
