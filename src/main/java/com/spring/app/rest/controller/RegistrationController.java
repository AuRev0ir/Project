package com.spring.app.rest.controller;


import com.spring.app.rest.dto.security.UserRegistrationDto;
import com.spring.app.service.security.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationController {

    UserService userService;

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
    @PostMapping("/registration")
    public ResponseEntity<String> create (@RequestBody UserRegistrationDto dto){
        return ResponseEntity.ok(userService.create(dto));
    }
}
