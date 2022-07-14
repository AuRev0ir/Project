package com.spring.app.rest.dto.organizationDto;

import com.spring.app.domain.Organization;

public class OrganizationDto {

    private Long id;
    private Long ratingOrganization;

    private String nameOrganization;

    private String descriptionOrganization;


    public OrganizationDto(Long id,
                           Long ratingOrganization,
                           String nameOrganization,
                           String descriptionOrganization) {
        this.ratingOrganization = ratingOrganization;
        this.nameOrganization = nameOrganization;
        this.descriptionOrganization = descriptionOrganization;
        this.id = id;
    }

    public static OrganizationDto toDto (Organization organization){
        return new OrganizationDto(
                organization.getIdOrganization(),
                organization.getRatingOrganization(),
                organization.getNameOrganization(),
                organization.getDescriptionOrganization());
    }

    //get


    public Long getId() {
        return id;
    }

    public Long getRatingOrganization() {
        return ratingOrganization;
    }

    public String getNameOrganization() {
        return nameOrganization;
    }

    public String getDescriptionOrganization() {
        return descriptionOrganization;
    }
}
