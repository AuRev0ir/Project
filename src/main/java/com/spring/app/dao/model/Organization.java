package com.spring.app.dao.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "organizations")
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long rating;

    @Column(unique = true, nullable = false)
    String name;

    String description;

    public Organization(String name,
                        String description,
                        Long rating) {
        this.name = name;
        this.description = description;
        this.rating = rating;
    }

}
