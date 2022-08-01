package com.spring.app.rest.controller;

import com.spring.app.rest.dto.security.UserDto;
import com.spring.app.service.security.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userDetailsService;

    @GetMapping()
    public List<UserDto> getAll(){
        return userDetailsService.getAllUsers();
    }

    @GetMapping("/{name}")
    public UserDto getUserByName (@RequestParam("name") String name) {
        return userDetailsService.getUserByName(name);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> remove (@PathVariable("name") String name) {
        return ResponseEntity.ok(userDetailsService.remove(name));
    }

}
