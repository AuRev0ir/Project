package com.spring.app.rest.dto.security;

import com.spring.app.dao.model.Role;
import com.spring.app.dao.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;
import java.util.Set;


@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class UserRegistrationDto {

    String name;

    String password;

    String email;

    LocalDate registrationDate;


    public static User toDomainObject (UserRegistrationDto dto, Set<Role> roles, String password) {
        return new User(
                dto.getName(),
                password,
                dto.getEmail(),
                dto.getRegistrationDate(),
                roles);
    }
}
