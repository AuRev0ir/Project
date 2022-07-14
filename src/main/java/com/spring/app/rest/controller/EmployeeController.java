package com.spring.app.rest.controller;


import com.spring.app.rest.dto.employeeDto.EmployeeDto;
import com.spring.app.rest.dto.employeeDto.EmployeeIdDto;
import com.spring.app.rest.pojo.FormEmployee;
import com.spring.app.services.servicerEmployee.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @PatchMapping("/organizations/{idOrganization}/employees/{idEmployee}")
    public EmployeeDto editingEmployee (@PathVariable("idOrganization") Long idOrganization,
                                        @PathVariable("idEmployee") Long idEmployee,
                                        @RequestBody FormEmployee formEmployee) {
        return employeeService.editingEmployee(formEmployee,idOrganization,idEmployee);
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
    @DeleteMapping("/organizations/{idOrganization}/employees/{idEmployee}")
    public String deleteEmployee(@PathVariable("idOrganization") Long idOrganization,
                                 @PathVariable("idEmployee") Long idEmployee){
        return employeeService.deleteEmployee(idOrganization, idEmployee);
    }



    @Operation(summary = "Employee creation form")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Successful operation",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeIdDto.class)
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
    @PostMapping("/organizations/{idOrganization}/employees")
    public EmployeeIdDto addEmployee (@PathVariable("idOrganization") Long idOrganization,
                                      @RequestBody FormEmployee formEmployee){
        return employeeService.addEmployee(formEmployee, idOrganization);
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
    @GetMapping("/organizations/{idOrganization}")
    public List<EmployeeDto> employeeList (@PathVariable("idOrganization") Long idOrganization){
        return employeeService.employeeList(idOrganization);
    }
}
