package com.spring.app.dao.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String name;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    LocalDate registrationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns =@JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set <Role> roles;

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
}
