package com.rdv.gestionrdvs.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@DiscriminatorValue("patient")

public class Patient extends User{

    public Patient( Long patient_id, String name, String password, String email, int phone_number, String image_name) {
        super(patient_id , name, password, email, phone_number,  image_name , Role.PATIENT);
    }


    @OneToMany(mappedBy = "patient" , fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JsonIgnoreProperties("patient")
    private final List<Rdv> rdv= new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.PATIENT.name()));
    }
    @Override

    public String getUsername() {
        return this.getEmail();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
