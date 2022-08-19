package com.spring.app.service;

import com.spring.app.rest.dto.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAllRoles();

    Long addNewRole(RoleDto roleDto);

    void deleteRole(String roleName);
}
