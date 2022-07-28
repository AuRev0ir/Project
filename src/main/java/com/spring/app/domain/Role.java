package com.spring.app.domain;


import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long id;

    @Column(name = "name_role")
    private String name;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    //get
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //set
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
