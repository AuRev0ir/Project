package com.spring.app.rest.controller;


import com.spring.app.rest.dto.security.RoleDto;
import com.spring.app.rest.dto.security.UserDto;
import com.spring.app.rest.dto.security.UserRegistrationDto;
import com.spring.app.services.security.UserDetailsServiceImpl;
import com.spring.app.services.security.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/apiAdmin")
@RestController
public class RegistrationController {

    private final UserService userDetailsService;

    public RegistrationController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    // Основной контроллер для регистрации
    @Tag(name = "REGISTRATION")
    @Operation(summary = "User registration")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Successful operation",
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = String.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "500", description = "An error occurred on the server",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Client Error",
                    content = {@Content(schema =@Schema())}),
            @ApiResponse(responseCode = "403", description = "Access blocked",
                    content = {@Content(schema = @Schema())})
    })
    @PostMapping("/userRegistration")
    public ResponseEntity<String> addNewUser (@RequestBody UserRegistrationDto userRegistrationDto){
        return ResponseEntity.ok(userDetailsService.addUser(userRegistrationDto));
    }


    //Controller-ы  для тестирования БД


    @GetMapping("/users")
    public List<UserDto> allUsers(){
        return userDetailsService.getUsers();
    }


    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(@RequestParam String nameDeleteUser){
        return ResponseEntity.ok(userDetailsService.removeUser(nameDeleteUser));
    }


    @GetMapping("/roles")
    public Set<RoleDto> allRoles (){
        return userDetailsService.getRoles();
    }



    @PatchMapping("/users/addRole")
    public ResponseEntity<String> addRoleUser(@RequestParam String nameUser,
                              @RequestParam String newNameRole){
        return ResponseEntity.ok(userDetailsService.addUserRole(newNameRole, nameUser));
    }


    @PatchMapping ("users/deleteRole")
    public ResponseEntity<String> removeRoleUser (@RequestParam String nameUser,
                                  @RequestParam String deleteNameRole){
        return ResponseEntity.ok(userDetailsService.removeUserRole(deleteNameRole, nameUser));
    }

    @GetMapping("/users/userByName")
    public UserDto userByName(@RequestParam String userName){
        return userDetailsService.getUserByName(userName);
    }



}
