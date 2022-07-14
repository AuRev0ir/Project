package com.spring.app.domain;


import javax.persistence.*;


@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idOrganization;

    @Column(name = "organization_Rating", nullable = false)
    private long ratingOrganization;


    @Column(name = "name_organization", nullable = false) // позже постаить уникальность
    private String nameOrganization;


    @Column(name = "description_organization")
    private String descriptionOrganization;

    public Organization() {}

    public Organization(String nameOrganization, String descriptionOrganization,
                        Long ratingOrganization) {
        this.nameOrganization = nameOrganization;
        this.descriptionOrganization = descriptionOrganization;
        this.ratingOrganization = ratingOrganization;
    }


    //get
    public long getIdOrganization() {
        return idOrganization;
    }

    public String getNameOrganization() {
        return nameOrganization;
    }

    public String getDescriptionOrganization() {
        return descriptionOrganization;
    }

    public long getRatingOrganization() {
        return ratingOrganization;
    }

    //set
    public void setNameOrganization(String nameOrganization) {
        this.nameOrganization = nameOrganization;
    }

    public void setDescriptionOrganization(String descriptionOrganization) {
        this.descriptionOrganization = descriptionOrganization;
    }

    public void setRatingOrganization(long ratingOrganization) {
        this.ratingOrganization = ratingOrganization;
    }
}
