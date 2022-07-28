package com.spring.app.services.serviceSecurity;

import com.spring.app.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private Long idUserDetails;

    private String nameUserDetails;

    private String emailUserDetails;

    private LocalDate dateOfEmploymentUserDetails;

    private String passwordUserDetails;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long idUserDetails,
                           String nameUserDetails,
                           String emailUserDetails,
                           LocalDate dateOfEmploymentUserDetails,
                           String passwordUserDetails,
                           Collection<? extends GrantedAuthority> authorities) {
        this.idUserDetails = idUserDetails;
        this.nameUserDetails = nameUserDetails;
        this.emailUserDetails = emailUserDetails;
        this.dateOfEmploymentUserDetails = dateOfEmploymentUserDetails;
        this.passwordUserDetails = passwordUserDetails;
        this.authorities = authorities;
    }

    public static UserDetailsImpl buildUserDetails(User user){

        List<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRegistrationDate(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordUserDetails;
    }

    @Override
    public String getUsername() {
        return nameUserDetails;
    }

    public Long getIdUserDetails() {
        return idUserDetails;
    }

    public String getEmailUserDetails() {
        return emailUserDetails;
    }

    public LocalDate getDateOfEmploymentUserDetails() {
        return dateOfEmploymentUserDetails;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
