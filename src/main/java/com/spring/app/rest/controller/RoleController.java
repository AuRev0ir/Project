package com.spring.app.rest.controller;

import com.spring.app.rest.dto.RoleDto;
import com.spring.app.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController implements RoleApi {

    RoleService roleService;

    @Override
    public ResponseEntity<Long> addRole(RoleDto role) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(roleService.addNewRole(role));
    }

    @Override
    public ResponseEntity<Void> deleteRole(String name) {
        roleService.deleteRole(name);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
