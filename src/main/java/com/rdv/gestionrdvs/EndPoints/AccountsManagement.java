package com.rdv.gestionrdvs.EndPoints;



import com.rdv.gestionrdvs.Authentification.AuthService;
import com.rdv.gestionrdvs.Repositories.UserRepository;
import com.rdv.gestionrdvs.Services.Idoctor;
import com.rdv.gestionrdvs.config.JwtService;
import com.rdv.gestionrdvs.entities.*;
import com.rdv.gestionrdvs.Services.Ipatient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("api/v0/manage")
public class AccountsManagement {
    private final Idoctor idoctor;
    private final Ipatient ipatient;
    private final UserRepository userRepository;

    @CrossOrigin
    @GetMapping("doctors-list")
    public List<Doctor> listerDocs(){
        return idoctor.doctorsList();
    }


    @GetMapping("doctors/{id}")
    public ResponseEntity<Doctor> getDoctorInfo(@PathVariable("id") Long id){
        Doctor doctor = idoctor.findDoctor(id).orElse(null);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("patients/{id}")
    public ResponseEntity<Patient> findPatientById(@PathVariable("id") Long id,
                                                   @AuthenticationPrincipal User user){

        Patient patient= ipatient.findPatient(id).get();
        if(!patient.getUser_id().equals(user.getUser_id())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (ipatient.findPatient(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(patient);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") Long id,
                                                   @AuthenticationPrincipal User logged_user){


        if (userRepository.findById(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userRepository.findById(id).orElse(null);
        if(!user.getUser_id().equals(logged_user.getUser_id())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        return ResponseEntity.ok(user);
    }



}
