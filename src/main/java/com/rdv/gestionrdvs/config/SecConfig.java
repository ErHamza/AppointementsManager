package com.rdv.gestionrdvs.config;


import com.rdv.gestionrdvs.entities.Role;
import com.rdv.gestionrdvs.entities.User;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.http.HttpRequest;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeHttpRequests().
                requestMatchers("/api/v0/manage/**"
                        ,"/auth/**","/api/v0/admin/**", "/api/**")
                .permitAll()

                .requestMatchers("/consultation/**").hasRole(Role.PATIENT.name())

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class);


    return httpSecurity.build();
    }
}
