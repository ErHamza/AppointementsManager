package com.rdv.gestionrdvs.EndPoints;
import com.rdv.gestionrdvs.Services.Iconsultation;
import com.rdv.gestionrdvs.Services.Ipatient;
import com.rdv.gestionrdvs.Services.Irdv;
import com.rdv.gestionrdvs.entities.Consultation;
import com.rdv.gestionrdvs.entities.Doctor;
import com.rdv.gestionrdvs.entities.Patient;
import com.rdv.gestionrdvs.entities.Rdv;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("api/v0/consultations")

public class ManageConsulation {
    private final Irdv irdv;

    private final Iconsultation iconsulation;
    private final Ipatient ipatient;


    @PostMapping("/add")
    private ResponseEntity<?> addConsulaion(@RequestParam("diagnostic") String diagnostic,
                                                       @RequestParam("rdv_id") Long rdv_id){
        Rdv rdv = irdv.findRdvById(rdv_id).orElse(null);
        assert rdv != null;
        rdv.setIsDone(true);
        irdv.addRdv(rdv);

        Consultation consultation= new Consultation(null, diagnostic, rdv);
        return ResponseEntity.status(HttpStatus.CREATED).body(iconsulation.addConsultation(consultation));
    }

    @GetMapping("/list")
        List<Consultation> listerConsulation(@AuthenticationPrincipal Doctor doctor){
            return iconsulation.findConsultations(doctor);
        }


       // should Add more security mesures so that only my dector can rertirieve patients pics
    @GetMapping("patient/{id}")
    ResponseEntity<Patient> getPatientDetails(@AuthenticationPrincipal Doctor doctor , @PathVariable("id") Long id ){
        return ResponseEntity.ok(ipatient.findPatient(id).orElse(null));

    }

    @CrossOrigin
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/patient-image/{id}")
    public ResponseEntity<byte[]> getPatientImage(@PathVariable("id") Long patient_id)
            throws IOException {
        System.out.println(patient_id);
        Patient patient = ipatient.findPatient(patient_id).orElse(null);
        assert patient != null;
        String path = "user-photos/"+ patient.getUser_id()+ "/display_picture/" +patient.getImage_name();
        Path imagePath = Paths.get(path);
        byte[] imageBytes = Files.readAllBytes(imagePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }


}
