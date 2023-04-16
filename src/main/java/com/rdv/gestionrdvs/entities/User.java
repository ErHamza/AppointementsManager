package com.rdv.gestionrdvs.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING )
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(length = 40)
    private String username;

    @Column( length = 200)
    private String password;

    @Column(unique = true, length = 40 )
    private String email;
    private int phone_number;

    @Column
    private String image_name;


    @Enumerated(EnumType.STRING)
    private Role role;


}