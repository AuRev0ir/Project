package com.spring.app.service.security;

import com.spring.app.rest.dto.security.RoleDto;
import com.spring.app.rest.dto.security.UserDto;
import com.spring.app.rest.dto.security.UserRegistrationDto;

import java.util.List;
import java.util.Set;

public interface UserService {

    String create (UserRegistrationDto dto);

    Set<RoleDto> getAllRoles ();

    List<UserDto> getAllUsers ();

    UserDto getUserByName(String name);

    String remove (String name);

    String addUserRole (String roleName, String userName);

    String removeUserRole (String roleName, String userName);


}
