package com.spring.app.rest.mapper;


import com.spring.app.dao.model.Role;
import com.spring.app.dao.model.User;
import com.spring.app.rest.dto.security.RoleDto;
import com.spring.app.rest.dto.security.UserDto;
import com.spring.app.rest.dto.security.UserRegistrationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper  {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "password", target = "password")
    User toDomainObject (UserRegistrationDto dto, String password, Set<Role> roles);

    @Mapping(source = "roles", target = "roles")
    UserDto toDto (User user, Set <RoleDto> roles);


}
