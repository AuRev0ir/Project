package com.spring.app.configApp;

import com.spring.app.services.serviceSecurity.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailServiceImpl userDetailService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
                http.authorizeRequests()
                        .antMatchers(HttpMethod.POST, "/organizations")
                        .hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.PATCH,"/organizations/{idOrganization}")
                        .hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/organizations/{idOrganization}")
                        .hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.PATCH,"/organizations/{idOrganization}/employees/{idEmployee}")
                        .hasAuthority("USER")
                        .antMatchers(HttpMethod.POST, "/organizations/{idOrganization}/employees")
                        .hasAuthority("USER")
                        .antMatchers("/apiAdmin/**").hasAuthority("ADMIN")    //Не знаю, как будет правильней, но пусть регестрируют и дают роли только Админы
                .anyRequest().authenticated()
                .and()
                        .formLogin()
                        .defaultSuccessUrl("/api/v1/organizations");    //стартовая страница

    }
}
