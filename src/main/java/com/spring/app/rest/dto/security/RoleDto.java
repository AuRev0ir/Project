package com.spring.app.rest.dto.security;

import com.spring.app.dao.model.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class RoleDto {


    String role;

    public static RoleDto toDto (Role role){
        return new RoleDto(
                role.getName());
    }

}
