package com.spring.app.services.servicerOrganization;

import com.spring.app.rest.dto.organizationDto.OrganizationDto;
import com.spring.app.rest.dto.organizationDto.OrganizationDtoOnlyId;

import java.util.List;

public interface OrganizationService {

    OrganizationDto updateOrganization(OrganizationDto dto, Long idOrganization);
    OrganizationDtoOnlyId addOrganization(OrganizationDto dto);
    List<OrganizationDto> getOrganizations();
    String removeOrganization(Long idOrganization);
}
