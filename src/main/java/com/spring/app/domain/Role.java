package com.spring.app.domain;


import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Column(name = "name_role")
    private String nameRole;

    public Role() {}

    public Role(String nameRole) {
        this.nameRole = nameRole;
    }

    //get
    public Long getIdRole() {
        return idRole;
    }

    public String getNameRole() {
        return nameRole;
    }

    //set
    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }
}
