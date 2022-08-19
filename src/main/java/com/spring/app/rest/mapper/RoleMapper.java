package com.spring.app.rest.mapper;

import com.spring.app.dao.model.Role;
import com.spring.app.rest.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface RoleMapper {

    RoleDto toDto (Role role);

    List<RoleDto> toDtoList(List<Role> roles);

    @Mapping(target = "id", ignore = true)
    Role toEntity(RoleDto roleDto);

    List<Role> toEntityList(List<RoleDto> roles);
}
