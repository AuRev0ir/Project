package com.spring.app.rest.controller;


import com.spring.app.rest.dto.employeeDto.EmployeeDto;
import com.spring.app.rest.dto.employeeDto.EmployeeDtoOnlyId;
import com.spring.app.services.servicerEmployee.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee API")
@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }



    @Operation(summary = "Employee edit")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Successful operation",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeDto.class)
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
    @PatchMapping("/organizations/{name}/employees/{idEmployee}")
    public EmployeeDto editingEmployee (@PathVariable("name") String name,
                                        @PathVariable("idEmployee") Long idEmployee,
                                        @RequestBody EmployeeDto dto) {
        return employeeService.updateEmployee(dto,name,idEmployee);
    }



    @Operation(summary = "Delete an employee")
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
    @DeleteMapping("/organizations/{name}/employees/{idEmployee}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("name") String name,
                                         @PathVariable("idEmployee") Long idEmployee){
        return ResponseEntity.ok(employeeService.removeEmployee(name, idEmployee));
    }



    @Operation(summary = "Employee creation form")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Successful operation",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeDtoOnlyId.class)
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
    @PostMapping("/organizations/{name}/employees")
    public EmployeeDtoOnlyId addEmployee (@PathVariable("name") String name,
                                    @RequestBody EmployeeDto formEmployee){
        return employeeService.addEmployee(formEmployee, name);
    }


    @Operation(summary = "List of employees of the organization by date of employment")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Successful operation",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EmployeeDto.class))
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
    @GetMapping("/organizations/{name}")
    public List<EmployeeDto> employeeList (@PathVariable("name") String name){
        return employeeService.getEmployees(name);
    }
}
