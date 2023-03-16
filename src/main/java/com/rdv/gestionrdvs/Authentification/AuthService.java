package com.rdv.gestionrdvs.Authentification;

import com.rdv.gestionrdvs.Repositories.UserRepository;
import com.rdv.gestionrdvs.Services.Idoctor;
import com.rdv.gestionrdvs.Services.Ipatient;
import com.rdv.gestionrdvs.config.JwtService;
import com.rdv.gestionrdvs.entities.Doctor;
import com.rdv.gestionrdvs.entities.Patient;
import com.rdv.gestionrdvs.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final Ipatient ipatient;
    private final JwtService jwtService;
    private final Idoctor idoctor;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse auhtenticate(AuthForm authForm) {
        System.out.println("fff");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authForm.getUsername(),
                        authForm.getPassword()
                )
        );
        System.out.println("ok");
        var user = userRepository.findByEmail(authForm.getUsername()).orElseThrow();
        System.out.println(user.getUser_id());
        var token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);

    }


    public AuthenticationResponse register(Patient patient) {
        patient.setRole(Role.PATIENT);
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        ipatient.addPatient(patient);
        var token = jwtService.generateToken(patient);
        return new AuthenticationResponse(token);

    }

    public void register(Doctor doctor){
        doctor.setRole(Role.DOCTOR);
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        idoctor.addDoctor(doctor);
    }
}
