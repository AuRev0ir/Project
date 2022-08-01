package com.spring.app.rest.controller;


import com.spring.app.rest.dto.security.RoleDto;
import com.spring.app.service.security.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    UserService userService;

    @GetMapping()
    public Set<RoleDto> getAll (){
        return userService.getAllRoles();
    }

    @PatchMapping("/addRole")
    public ResponseEntity<String> addUserRole (@RequestParam String nameUser,
                                               @RequestParam String nameRole){
        return ResponseEntity.ok(userService.addUserRole(nameRole, nameUser));
    }


    @PatchMapping ("/removeRole")
    public ResponseEntity<String> removeUserRole (@RequestParam String nameUser,
                                                  @RequestParam String nameRole){
        return ResponseEntity.ok(userService.removeUserRole(nameRole, nameUser));
    }
}
