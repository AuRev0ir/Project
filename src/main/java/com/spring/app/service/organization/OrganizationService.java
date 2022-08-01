package com.spring.app.service.organization;

import com.spring.app.rest.dto.organization.OrganizationDto;
import com.spring.app.rest.dto.organization.OrganizationNameDto;

import java.util.List;

public interface OrganizationService {

    OrganizationDto update (OrganizationDto dto, String name);
    OrganizationNameDto create (OrganizationDto dto);
    List<OrganizationDto> getAll ();
    String remove (String name);
}
