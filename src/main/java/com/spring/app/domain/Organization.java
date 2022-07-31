package com.spring.app.domain;

import javax.persistence.*;

@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id", nullable = false)
    private long id;

    @Column(nullable = false)
    private long rating;

    @Column(name = "organization_name", unique = true, nullable = false)
    private String name;

    @Column(name = "organization_description")
    private String description;

    public Organization() {}

    public Organization(String name,
                        String description,
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
