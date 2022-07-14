package com.spring.app.rest.dto.organizationDto;

import com.spring.app.domain.Organization;

public class OrganizationIdDto {

    private Long idOrganization;

    public OrganizationIdDto(Long idOrganization) {
        this.idOrganization = idOrganization;
    }

    public static OrganizationIdDto toDto(Organization organization){
        return new OrganizationIdDto(organization.getIdOrganization());
    }

    //get
    public Long getIdOrganization() {
        return idOrganization;
    }
}
