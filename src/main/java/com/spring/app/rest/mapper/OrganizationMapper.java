package com.spring.app.rest.mapper;

import com.spring.app.dao.model.Organization;
import com.spring.app.rest.dto.OrganizationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrganizationMapper {

    OrganizationDto toDto (Organization organization);

    @Mapping(target = "id", ignore = true)
    Organization toEntity(OrganizationDto dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(OrganizationDto organizationFillFormDto, @MappingTarget Organization organization);
}
