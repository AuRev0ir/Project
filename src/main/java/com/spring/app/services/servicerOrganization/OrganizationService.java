package com.spring.app.services.servicerOrganization;

import com.spring.app.rest.dto.organizationDto.OrganizationDto;
import com.spring.app.rest.dto.organizationDto.OrganizationDtoOnlyNane;

import java.util.List;

public interface OrganizationService {

    OrganizationDto updateOrganization(OrganizationDto dto, String name);
    OrganizationDtoOnlyNane addOrganization(OrganizationDto dto);
    List<OrganizationDto> getOrganizations();
    String removeOrganization(String name);
}
