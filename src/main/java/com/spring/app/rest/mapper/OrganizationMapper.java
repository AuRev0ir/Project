package com.spring.app.rest.mapper;


import com.spring.app.dao.model.Organization;
import com.spring.app.rest.dto.organization.OrganizationDto;
import com.spring.app.rest.dto.organization.OrganizationFillFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrganizationMapper {

    OrganizationMapper INSTANCE = Mappers.getMapper(OrganizationMapper.class);

    OrganizationDto toDto (Organization organization);

    Organization toDomainObject (OrganizationFillFormDto dto);


}
