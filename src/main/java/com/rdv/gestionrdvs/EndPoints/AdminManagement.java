package com.rdv.gestionrdvs.EndPoints;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin
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
    public ResponseEntity<Patient> findPatientById(@PathVariable("id") Long id){
        Patient patient= ipatient.findPatient(id).get();
        if (ipatient.findPatient(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(patient);
    }

    @PostMapping("doctors/register")
    public ResponseEntity<?> addDoctor(@RequestParam("doctor") String doctor_string,
                                       @RequestParam("file") MultipartFile image ){
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("no image");
        }
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Doctor doctor;
            doctor = objectMapper.readValue(doctor_string , Doctor.class);
            System.out.println(doctor);
            //maybe not the best approach
            Doctor new_doctor = new Doctor(null,
                    doctor.getUsername()
                    , doctor.getPassword(), doctor.getEmail(), doctor.getPhone_number() ,
                    image.getOriginalFilename(), doctor.getSpeciality() );
            String email = doctor.getEmail();
            if(idoctor.findByEmail(doctor.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.IM_USED).body("this email already exists");
                 }
            //TODO finish later
//        emailService.sendEmail("heerraji123@gmail.com","done","welcome");
            return ResponseEntity.ok(authService.register(new_doctor, image));
        } catch(JsonProcessingException e){
            return ResponseEntity.badRequest().body("problem"+ e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
