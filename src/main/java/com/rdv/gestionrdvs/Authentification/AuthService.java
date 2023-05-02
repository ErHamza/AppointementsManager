package com.rdv.gestionrdvs.Authentification;

import com.rdv.gestionrdvs.Repositories.UserRepository;
import com.rdv.gestionrdvs.Services.FileUploadUtil;
import com.rdv.gestionrdvs.Services.Idoctor;
import com.rdv.gestionrdvs.Services.Ipatient;
import com.rdv.gestionrdvs.config.JwtService;
import com.rdv.gestionrdvs.entities.Doctor;
import com.rdv.gestionrdvs.entities.Patient;
import com.rdv.gestionrdvs.entities.Role;
import com.rdv.gestionrdvs.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthService<T> {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final Ipatient ipatient;
    private final JwtService jwtService;
    private final Idoctor idoctor;

    private final PasswordEncoder passwordEncoder;


    public AuthenticationResponse auhtenticate(AuthForm authForm) throws IncorrectPasswordException {
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authForm.getUsername(),
                        authForm.getPassword()
                ));


        }catch( Exception e){
            throw new IncorrectPasswordException("Incorrect Password or email");

        }

        System.out.println("done login");
        var user = userRepository.findByEmail(authForm.getUsername()).orElseThrow();
        Map<String, Long> map = new HashMap();
        map.put("user_id", user.getUser_id());
        var token = jwtService.generateToken(map ,user);
        return new AuthenticationResponse(token);

    }


    public AuthenticationResponse register(Patient patient, MultipartFile image) throws IOException {
        //Todo ad expire time to the response
        patient.setRole(Role.PATIENT);
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        ipatient.addPatient(patient);
        Patient to_retrieve = ipatient.findByEmail(patient.getEmail()).orElse(null);
        System.out.println(to_retrieve);
        if (image != null){
            String uploadDir = "user-photos/" + patient.getUser_id() + "/display_picture";
            FileUploadUtil.saveFile(uploadDir, image.getOriginalFilename(), image);
        }

        Map<String, Long> map = new HashMap();
        assert to_retrieve != null;
        map.put("user_id", to_retrieve.getUser_id());
        var token = jwtService.generateToken(map,to_retrieve);
        return new AuthenticationResponse(token);


    }


        public ResponseEntity<?> register(Doctor doctor, MultipartFile image) throws IOException {
            //Todo ad expire time to the response
            doctor.setRole(Role.DOCTOR);
            doctor.setUser_id(null);
            doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
            idoctor.addDoctor(doctor);
            //because the Id is assigned automatically by the database I had to save it and retrieve it again
            // so I can know the Id of the new user
            Doctor to_retrieve = idoctor.findByEmail(doctor.getEmail()).orElse(null);
            String uploadDir = "doctors-photos/" + to_retrieve.getUser_id() + "/display_picture";
            try{
                FileUploadUtil.saveFile(uploadDir, image.getOriginalFilename(), image);
            }
            catch (Exception e){
                System.out.println(e);

            }


            return ResponseEntity.ok().build();



    }
}
