package com.spring.app.rest.pojo;

public class FormOrganization {

    private String nameOrganization;
    private String descriptionOrganization;
    private Long ratingOrganization;

    public FormOrganization(String nameOrganization,
                            String descriptionOrganization,
                            Long ratingOrganization) {
        this.nameOrganization = nameOrganization;
        this.descriptionOrganization = descriptionOrganization;
        this.ratingOrganization = ratingOrganization;
    }
    public String getNameOrganization() {
        return nameOrganization;
    }

    public String getDescriptionOrganization() {
        return descriptionOrganization;
    }

    public Long getRatingOrganization() {
        return ratingOrganization;
    }
}
