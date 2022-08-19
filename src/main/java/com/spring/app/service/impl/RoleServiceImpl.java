package com.spring.app.service.impl;

import com.spring.app.dao.repository.RoleRepository;
import com.spring.app.rest.dto.RoleDto;
import com.spring.app.rest.mapper.RoleMapper;
import com.spring.app.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDto> getAllRoles() {
        return roleMapper.toDtoList(repository.findAll());
    }

    @Override
    public Long addNewRole(RoleDto roleDto) {
        var role = roleMapper.toEntity(roleDto);
        var newRole = repository.saveAndFlush(role);
        return newRole.getId();
    }

    @Override
    @Transactional
    public void deleteRole(String roleName) {
        repository.findByName(roleName).ifPresent(repository::delete);
    }
}
