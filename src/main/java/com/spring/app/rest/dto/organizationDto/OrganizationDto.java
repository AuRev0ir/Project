package com.spring.app.rest.dto.organizationDto;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;

public class OrganizationDto {

    private String name;

    private String description;

    private Long rating;


    public OrganizationDto(Long rating,
                           String name,
                           String description) {
        this.rating = rating;
        this.name = name;
        this.description = description;
    }

    public static OrganizationDto toDto (Organization organization){
        return new OrganizationDto(
                organization.getRating(),
                organization.getName(),
                organization.getDescription());
    }

    public static Organization toDomainObject (OrganizationDto dto) {
        return new Organization(
                dto.getName(),
                dto.getDescription(),
                dto.getRating());
    }

    //get

    public Long getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
