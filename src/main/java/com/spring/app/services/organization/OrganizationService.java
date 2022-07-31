package com.spring.app.services.organization;

import com.spring.app.rest.dto.organization.OrganizationDto;
import com.spring.app.rest.dto.organization.OrganizationNameDto;

import java.util.List;

public interface OrganizationService {

    OrganizationDto updateOrganization(OrganizationDto dto, String name);
    OrganizationNameDto addOrganization(OrganizationDto dto);
    List<OrganizationDto> getOrganizations();
    String removeOrganization(String name);
}
