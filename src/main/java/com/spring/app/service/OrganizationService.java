package com.spring.app.service;

import com.spring.app.rest.dto.OrganizationDto;

import java.util.List;

public interface OrganizationService {

    void update (OrganizationDto dto, String name);
    Long create (OrganizationDto dto);
    List<OrganizationDto> getAll ();
    void remove (String name);
}
