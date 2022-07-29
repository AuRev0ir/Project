package com.spring.app.rest.dto.organizationDto;

import com.spring.app.domain.Organization;

public class OrganizationDtoOnlyNane {

    private String name;

    public OrganizationDtoOnlyNane(String name) {
        this.name = name;
    }

    public static OrganizationDtoOnlyNane toDto(Organization organization){
        return new OrganizationDtoOnlyNane(organization.getName());
    }

    //get


    public String getName() {
        return name;
    }
}
