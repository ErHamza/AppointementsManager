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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@AllArgsConstructor
@RequestMapping("api/v0/manage")
public class AccountsManagement {
    private final Idoctor idoctor;
    private final Ipatient ipatient;

    @GetMapping("doctors-list")
    public List<Doctor> listerDocs(){
        return idoctor.doctorsList();
    }


    @GetMapping("doctors/{id}")
    public ResponseEntity<Doctor> getDoctorInfo(@PathVariable("id") Long id){
        Doctor doctor = idoctor.findDoctor(id).orElse(null);
        return ResponseEntity.ok(doctor);
    }

}
