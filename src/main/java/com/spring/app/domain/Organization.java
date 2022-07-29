package com.spring.app.domain;


import javax.persistence.*;


@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organization")
    private long id;

    @Column(name = "organization_Rating", nullable = false)
    private long rating;


    @Column(name = "name_organization", unique = true)
    private String name;


    @Column(name = "description_organization")
    private String description;

    public Organization() {}

    public Organization(String name, String description,
                        Long rating) {
        this.name = name;
        this.description = description;
        this.rating = rating;
    }


    //get
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getRating() {
        return rating;
    }

    //set
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}
