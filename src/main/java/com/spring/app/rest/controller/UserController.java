package com.spring.app.rest.controller;

import com.spring.app.rest.dto.PlainUserDto;
import com.spring.app.rest.dto.RoleDto;
import com.spring.app.rest.dto.UserRegistrationDto;
import com.spring.app.rest.dto.UserRolesDto;
import com.spring.app.service.UserService;
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
public class UserController implements UserApi, UserRolesApi {

    UserService userDetailsService;

    @Override
    public ResponseEntity<Void> deleteUserByName(String name) {
        userDetailsService.remove(name);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<PlainUserDto>> getAllUsers() {
        return ResponseEntity.ok(userDetailsService.getAllUsers());
    }

    @Override
    public ResponseEntity<PlainUserDto> getUserByName(String name) {
        return ResponseEntity.ok(userDetailsService.getUserByName(name));
    }

    @Override
    public ResponseEntity<Long> signUpUser(UserRegistrationDto userRegistrationDto) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userDetailsService.create(userRegistrationDto));
    }

    @Override
    public ResponseEntity<List<RoleDto>> getLinkedRoles(String username) {
        return ResponseEntity.ok(userDetailsService.getUserRoles(username));
    }

    @Override
    public ResponseEntity<Void> linkRoleWithUser(UserRolesDto userRoles) {
        userDetailsService.addUserRole(userRoles.getRoles(), userRoles.getName());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteRoleOfUser(UserRolesDto userRoles) {
        userDetailsService.removeUserRole(userRoles.getRoles(), userRoles.getName());
        return ResponseEntity.noContent().build();
    }
}
