package com.spring.app.rest.mapper;

import com.spring.app.dao.model.User;
import com.spring.app.rest.dto.PlainUserDto;
import com.spring.app.rest.dto.UserRegistrationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper  {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRegistrationDto dto);

    PlainUserDto toDto (User user);
}
