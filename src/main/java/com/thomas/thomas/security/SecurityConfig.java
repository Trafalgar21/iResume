package com.thomas.thomas.security;

import com.thomas.thomas.filter.CustomAuthenticationFilter;
import com.thomas.thomas.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration @EnableWebSecurity @EnableGlobalMethodSecurity(prePostEnabled = true) @RequiredArgsConstructor
public class SecurityConfig {
    UserDetailsService userDetailsService;

    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        //Authorization
        //Login
        http.authorizeHttpRequests().requestMatchers("/login/**", "/api/token/refresh/**").permitAll();

        //ROLE USER
        http.authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/api/profiles", "/api/profile/{id}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN");

        //ROLE ADMIN
        http.authorizeHttpRequests().requestMatchers(HttpMethod.POST,
                "/api/profile/save", "/api/role/save", "/api/role/addToUser")
                .hasAnyAuthority("ROLE_ADMIN");


        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean(authenticationConfiguration)));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }





}
