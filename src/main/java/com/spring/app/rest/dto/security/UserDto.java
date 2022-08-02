package com.spring.app.rest.dto.security;

import com.spring.app.dao.model.User;
import com.spring.app.rest.mapper.RoleMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class UserDto {

    String name;

    String email;

    LocalDate registrationDate;

    Set<RoleDto> roles;

}
