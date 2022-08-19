package com.spring.app.dao.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "organizations")
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_id_seq_gen")
    @SequenceGenerator(name = "organization_id_seq_gen", sequenceName = "organizations_id_seq", allocationSize = 1)
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
