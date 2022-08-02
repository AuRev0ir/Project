package com.spring.app.rest.mapper;


import com.spring.app.dao.model.Role;
import com.spring.app.rest.dto.security.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {


    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto toDto (Role role);

}
