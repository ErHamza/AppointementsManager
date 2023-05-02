package com.rdv.gestionrdvs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Entity
@DiscriminatorValue("doctor")

@NoArgsConstructor
public class Doctor extends User{


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "speciality")
    @JsonIgnoreProperties("doctor")
    private Speciality speciality;

    public Doctor( Long id, String name, String password, String email, int phone_number, String image_name
           ,Speciality speciality ) {
        super(id , name, password, email, phone_number,image_name,Role.DOCTOR);


    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public List<Rdv> getRdv() {
        return rdv;
    }

    @OneToMany(mappedBy = "doctor" , fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("doctor")
     private final List<Rdv> rdv= new ArrayList<>();


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(Role.DOCTOR.name()));
    }

    @Override
    public String getUsername() {
        return getEmail();
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
