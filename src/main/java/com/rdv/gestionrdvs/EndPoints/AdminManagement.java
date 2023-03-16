package com.rdv.gestionrdvs.EndPoints;


import com.rdv.gestionrdvs.Authentification.AuthService;
import com.rdv.gestionrdvs.Services.Idoctor;
import com.rdv.gestionrdvs.Services.Ipatient;
import com.rdv.gestionrdvs.Services.Irdv;
import com.rdv.gestionrdvs.Services.Ispeciality;
import com.rdv.gestionrdvs.entities.*;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/admin")

public class AdminManagement {
    private final Ipatient ipatient;
    private final Irdv irdv;

    private final Idoctor idoctor;
    private  final AuthService authService;
    private final Ispeciality ispeciality;

    @GetMapping("patients-list")
    public List<Patient> lister(){

        return ipatient.patientsList();

    }

    @GetMapping("patients/{id}")
    public ResponseEntity<Patient> getPatientInfo(@PathVariable("id") Long id){
        Patient patient= ipatient.findPatient(id).get();
        if (ipatient.findPatient(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(patient);
    }

    @PostMapping("doctors/register")
    public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor ){

        if(idoctor.findByEmail(doctor.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.IM_USED).body("this email already exists");
        }
        authService.register(doctor);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping("speciality/add")

    public ResponseEntity<?> addSpeciality(@RequestBody Speciality speciality){

        ispeciality.addSpeciality(speciality);
        return ResponseEntity.ok(speciality);

    }

    @GetMapping("speciality/list")
    public ResponseEntity<?> getListSpeciality(){
        List<Speciality> listSpeciality = ispeciality.findAll();
        return ResponseEntity.ok(listSpeciality);
    }


    @GetMapping("rdv/list")
    public ResponseEntity<?> getListRdvDoctor
            (@AuthenticationPrincipal User user , @RequestParam("doctor_id") Long id){
        Doctor doctor = idoctor.findDoctor(id).orElse(null);
        if (doctor==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("give a valid doctor id");
        }
        List<Rdv> rdvs =  irdv.listRdvbyDoctor(doctor);
        return ResponseEntity.ok(rdvs);
    }

}
