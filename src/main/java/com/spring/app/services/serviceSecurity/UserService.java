package com.spring.app.services.serviceSecurity;

import com.spring.app.rest.dto.registrationDto.RoleDto;
import com.spring.app.rest.dto.registrationDto.UserDto;
import com.spring.app.rest.pojo.FormRegistration;

import java.util.List;
import java.util.Set;

public interface UserService {

    String newUser (FormRegistration formRegistration);

    Set<RoleDto> allRoles();

    List<UserDto> allUsers();

    UserDto userByName(String nameUser);

    String deleteUser(String nameDeleteUser);

    String addRoleUser(String newNameRole, String nameUser);

    String removeRoleUser(String deleteRole, String nameUser);


}
