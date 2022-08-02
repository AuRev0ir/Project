package com.spring.app.service.organization;

import com.spring.app.rest.dto.organization.OrganizationDto;
import com.spring.app.rest.dto.organization.OrganizationFillFormDto;

import java.util.List;

public interface OrganizationService {

    OrganizationDto update (OrganizationFillFormDto dto, String name);
    OrganizationDto create (OrganizationFillFormDto dto);
    List<OrganizationDto> getAll ();
    String remove (String name);
}
