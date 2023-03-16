package com.rdv.gestionrdvs.EndPoints;
import com.rdv.gestionrdvs.RdvTools.Rdvtools;
import com.rdv.gestionrdvs.Services.Idoctor;
import com.rdv.gestionrdvs.Services.Ipatient;
import com.rdv.gestionrdvs.Services.Irdv;
import com.rdv.gestionrdvs.entities.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import  java.lang.Long;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v0/rdv")
public class ManageRdvs {
    private static final int MAX_NUMBER_OF_APPOINTMENTS_PER_DAY = 10;
    private Ipatient ipatient;
    private Idoctor idoctor;
    private Irdv irdv;
    private Rdvtools rdvtools;


    // create an Apointement
    //TODO add exceptions
    @PostMapping("add")
    public ResponseEntity<?> addRdv(@RequestBody Map<String,Long> map,
                                    @AuthenticationPrincipal User user){

        Long doctor_id = Long.parseLong( map.get("doctor_id").toString());
        Patient patient_ = ipatient.findPatient(user.getUser_id()).orElse(null);
        Doctor doctor = idoctor.findDoctor(doctor_id).orElse(null);
        if (patient_ ==null || doctor==null ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!patient_.getUser_id().equals(user.getUser_id()) ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login with your account");
        }

        long timestamp =Long.parseLong( map.get("timestamp").toString() );
        Instant instant= Instant.ofEpochMilli(timestamp);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        Long number_of_Appointments = rdvtools.CountAppointementsForDoctor(dateTime,doctor);
        System.out.println(number_of_Appointments);
        if (number_of_Appointments >= MAX_NUMBER_OF_APPOINTMENTS_PER_DAY){
            return  ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("we can't accept more Rdv");
        }
        Rdv rdv = new Rdv(null , dateTime ,false,doctor , patient_  );
        irdv.addRdv(rdv);
        return ResponseEntity.ok(rdv);
    }
    @GetMapping("list")
     public List<Rdv> listerRdv(){
        return irdv.listerRdv();
    }



    //Cancel an Appointment
    @PutMapping("cancel/{id}")
    public ResponseEntity<?> cancelRdv(@PathVariable("id") Long id, @AuthenticationPrincipal User user){

        Rdv rdv =  irdv.findRdvById(id).orElse(null);
        if (!user.getUser_id().equals(rdv.getPatient().getUser_id()) || !user.getRole().
                equals(Role.ADMIN)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("not authorized to perform this action");

        }
        irdv.cancelRdv(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("postpone")
    public ResponseEntity<String> postpone(@RequestBody Map<?,?> map ,@AuthenticationPrincipal User user){
        System.out.println(user);
        Long id = Long.parseLong( map.get("rdv_id").toString());
        Rdv rdv = irdv.findRdvById(id).orElse(null);

        if ( rdv==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(!user.getUser_id().equals( rdv.getPatient().getUser_id() )){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        long timestamp =Long.parseLong( map.get("timestamp").toString() );
        Instant instant= Instant.ofEpochMilli(timestamp);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        Long number_Appointements = rdvtools.CountAppointementsForDoctor(dateTime,rdv.getDoctor());
        if (number_Appointements >=MAX_NUMBER_OF_APPOINTMENTS_PER_DAY ){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("schedule is full");
        }
        System.out.println("-------");
        System.out.println(timestamp);
        System.out.println(System.currentTimeMillis());
        System.out.println(dateTime);
        rdv.setDate_rdv(dateTime);
        irdv.addRdv(rdv);
        return ResponseEntity.ok().build();
    }




    // URI to find rdv by id
    @GetMapping("find/{id}")
    public Rdv findRdv(@PathVariable Long id){
        return irdv.findRdvById(id).orElse(null);
    }
}
