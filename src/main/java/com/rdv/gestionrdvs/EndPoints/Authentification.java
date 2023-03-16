package com.rdv.gestionrdvs.EndPoints;


import com.rdv.gestionrdvs.Authentification.AuthService;
import com.rdv.gestionrdvs.Authentification.AuthForm;
import com.rdv.gestionrdvs.Authentification.AuthenticationResponse;
import com.rdv.gestionrdvs.Services.Ipatient;
import com.rdv.gestionrdvs.entities.Patient;
import com.rdv.gestionrdvs.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class Authentification {

    private final Ipatient ipatient;


    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;



    @PostMapping("register")
    public ResponseEntity<?> addPatient(@RequestBody Patient patient){


        String email = patient.getEmail();

        if(ipatient.findByEmail(email).isPresent()){
            return ResponseEntity.status(HttpStatus.IM_USED).body("this email already exists");

        }

        return ResponseEntity.ok(authService.register(patient));
    }
    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> auth(@RequestBody AuthForm authForm) {


        return ResponseEntity.ok(authService.auhtenticate(authForm));
    }





}
