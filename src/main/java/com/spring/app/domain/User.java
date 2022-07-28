package com.spring.app.domain;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id_user")
    private Long id;

    @Column(name = "user_name", unique = true)
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "user_email")
    private String email;

    @Column(name = "date_of_employment")
    private LocalDate registrationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns =@JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set <Role> roles;

    public User() {}

    public User(String name,
                String password,
                String email,
                LocalDate registrationDate,
                Set<Role> roles) {
        this.name = name;
        this.email = email;
        this.registrationDate = registrationDate;
        this.roles = roles;
        this.password = password;
    }

    //get
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    //set

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
