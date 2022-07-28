package com.spring.app.services.serviceSecurity;

import com.spring.app.rest.dto.registrationDto.RoleDto;
import com.spring.app.rest.dto.registrationDto.UserDto;
import com.spring.app.rest.dto.registrationDto.UserRegistrationDto;

import java.util.List;
import java.util.Set;

public interface UserService {

    String addUser(UserRegistrationDto userRegistrationDto);

    Set<RoleDto> getRoles();

    List<UserDto> getUsers();

    UserDto getUserByName(String nameUser);

    String removeUser(String nameDeleteUser);

    String addUserRole(String newNameRole, String nameUser);

    String removeUserRole(String deleteRole, String nameUser);


}
