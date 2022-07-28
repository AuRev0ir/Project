package com.spring.app.rest.dto.organizationDto;

import com.spring.app.domain.Organization;

public class OrganizationDtoOnlyId {

    private Long id;

    public OrganizationDtoOnlyId(Long id) {
        this.id = id;
    }

    public static OrganizationDtoOnlyId toDto(Organization organization){
        return new OrganizationDtoOnlyId(organization.getId());
    }

    //get
    public Long getId() {
        return id;
    }
}
