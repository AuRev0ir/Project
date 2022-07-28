package com.spring.app.rest.controller;


import com.spring.app.rest.dto.registrationDto.RoleDto;
import com.spring.app.rest.dto.registrationDto.UserDto;
import com.spring.app.rest.pojo.FormRegistration;
import com.spring.app.services.serviceSecurity.UserDetailServiceImpl;
import com.spring.app.services.serviceSecurity.UserService;
import io.swagger.v3.oas.annotations.Hidden;
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

    private final UserService userDetailService;

    public RegistrationController(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
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
    public ResponseEntity<String> addNewUser (@RequestBody FormRegistration formRegistration){
        return ResponseEntity.ok(userDetailService.addUser(formRegistration));
    }


    //Controller-ы  для тестирования БД

    @Hidden
    @GetMapping("/users")
    public List<UserDto> allUsers(){
        return userDetailService.getUsers();
    }

    @Hidden
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(@RequestParam String nameDeleteUser){
        return ResponseEntity.ok(userDetailService.removeUser(nameDeleteUser));
    }

    @Hidden
    @GetMapping("/roles")
    public Set<RoleDto> allRoles (){
        return userDetailService.getRoles();
    }


    @Hidden
    @PatchMapping("/users/addRole")
    public ResponseEntity<String> addRoleUser(@RequestParam String nameUser,
                              @RequestParam String newNameRole){
        return ResponseEntity.ok(userDetailService.addUserRole(newNameRole, nameUser));
    }

    @Hidden
    @PatchMapping ("users/deleteRole")
    public ResponseEntity<String> removeRoleUser (@RequestParam String nameUser,
                                  @RequestParam String deleteNameRole){
        return ResponseEntity.ok(userDetailService.removeUserRole(deleteNameRole, nameUser));
    }
    @Hidden
    @GetMapping("/users/userByName")
    public UserDto userByName(@RequestParam String userName){
        return userDetailService.getUserByName(userName);
    }



}
