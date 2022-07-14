package com.spring.app.domain;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(name = "user_name")
    private String nameUser;

    @Column(name = "password")
    private String passwordUser;

    @Column(name = "user_email")
    private String emailUser;

    @Column(name = "date_of_employment")
    private LocalDate dateOfEmployment;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns =@JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles;

    public User() {}

    public User(String nameUser,
                String passwordUser,
                String emailUser,
                LocalDate dateOfEmployment,
                Set<Role> roles) {
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.dateOfEmployment = dateOfEmployment;
        this.roles = roles;
        this.passwordUser = passwordUser;
    }

    //get
    public Long getIdUser() {
        return idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    //set

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void setDateOfEmployment(LocalDate dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
