package com.spring.app.rest.controller;


import com.spring.app.rest.dto.organization.OrganizationDto;
import com.spring.app.rest.dto.organization.OrganizationNameDto;
import com.spring.app.services.organization.OrganizationService;
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

@Tag(name = "Organization API")
@RestController
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }


    @Operation(summary = "Organization creation form")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Successful operation",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrganizationNameDto.class)
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
    @PostMapping ("/organizations")
    public OrganizationNameDto addOrganization (@RequestBody OrganizationDto formOrganization){
        return organizationService.addOrganization(formOrganization);
    }


    @Operation(summary = "Organization edit")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Successful operation",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrganizationDto.class)
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
    @PatchMapping("/organizations/{name}")
    public OrganizationDto editingOrganization (@PathVariable("name") String name,
                                                @RequestBody OrganizationDto formOrganization){
        return organizationService.updateOrganization(formOrganization, name);
    }


    @Operation(summary = "Delete an organization with all employees")
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
    @DeleteMapping ("/organizations/{name}")
    public ResponseEntity<String> deleteOrganization(@PathVariable("name") String name){
        return ResponseEntity.ok(organizationService.removeOrganization(name));
    }

    @Operation(summary = "List of all organizations by rating")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Successful operation",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = OrganizationDto.class))
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
    @GetMapping("/organizations")
    public List<OrganizationDto> organizationList (){
        return organizationService.getOrganizations();
    }
}
