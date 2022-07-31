package com.spring.app.rest.dto.organization;

import com.spring.app.domain.Organization;

public class OrganizationNameDto {

    private String name;

    public OrganizationNameDto(String name) {
        this.name = name;
    }

    public static OrganizationNameDto toDto(Organization organization){
        return new OrganizationNameDto(organization.getName());
    }

    //get


    public String getName() {
        return name;
    }
}
