package com.spring.app.services.servicerOrganization;

import com.spring.app.rest.dto.organizationDto.OrganizationDto;
import com.spring.app.rest.dto.organizationDto.OrganizationIdDto;
import com.spring.app.rest.pojo.FormOrganization;

import java.util.List;

public interface OrganizationService {

    OrganizationDto editingOrganization (FormOrganization formOrganization, Long idOrganization);
    OrganizationIdDto addOrganization(FormOrganization formOrganization);
    List<OrganizationDto> organizationList();
    String deleteOrganization(Long idOrganization);
}
