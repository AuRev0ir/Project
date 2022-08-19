package com.spring.app.service;

import com.spring.app.rest.dto.PlainUserDto;
import com.spring.app.rest.dto.RoleDto;
import com.spring.app.rest.dto.UserRegistrationDto;

import java.util.List;
import java.util.Set;

public interface UserService {

    Long create (UserRegistrationDto dto);

    Set<RoleDto> getAllRoles ();

    List<PlainUserDto> getAllUsers ();

    PlainUserDto getUserByName(String name);

    void remove (String name);

    List<RoleDto> getUserRoles(String name);

    void addUserRole (List<RoleDto> roles, String userName);

    void removeUserRole (List<RoleDto> roles, String userName);


}
