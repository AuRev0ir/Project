package com.spring.app.configApp;

import com.spring.app.services.serviceSecurity.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers(HttpMethod.PATCH,"/organizations/{idOrganization}")
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/organizations/{idOrganization}")
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH,"/organizations/{idOrganization}/employees/{idEmployee}")
                .hasAuthority("USER")
                .antMatchers(HttpMethod.POST, "/organizations/{idOrganization}/employees")
                .hasAuthority("USER")
                .antMatchers(HttpMethod.POST,"/apiAdmin/userRegistration").not().fullyAuthenticated()    //Не знаю, как будет правильней, но пусть регестрируют и дают роли только Админы
                .antMatchers("/apiAdmin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/api/v1/organizations");    //стартовая страница

        return http.build();
    }
}
