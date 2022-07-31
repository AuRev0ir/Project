package com.spring.app.services.security;

import com.spring.app.rest.dto.security.RoleDto;
import com.spring.app.rest.dto.security.UserDto;
import com.spring.app.rest.dto.security.UserRegistrationDto;

import java.util.List;
import java.util.Set;

public interface UserService {

    String addUser(UserRegistrationDto dto);

    Set<RoleDto> getRoles();

    List<UserDto> getUsers();

    UserDto getUserByName(String name);

    String removeUser(String name);

    String addUserRole(String roleName, String userName);

    String removeUserRole(String roleName, String userName);


}
