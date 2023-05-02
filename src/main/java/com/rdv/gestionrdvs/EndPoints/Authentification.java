package com.rdv.gestionrdvs.EndPoints;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.rdv.gestionrdvs.Authentification.*;
//import com.rdv.gestionrdvs.Services.EmailService;
import com.rdv.gestionrdvs.Services.EmailService;
import com.rdv.gestionrdvs.Services.FileUploadUtil;
import com.rdv.gestionrdvs.Services.Idoctor;
import com.rdv.gestionrdvs.Services.Ipatient;
import com.rdv.gestionrdvs.Tools.EmailDetails;
import com.rdv.gestionrdvs.entities.Doctor;
import com.rdv.gestionrdvs.entities.Patient;
import com.rdv.gestionrdvs.entities.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.rdv.gestionrdvs.Authentification.IncorrectPasswordException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class Authentification {

    private final Ipatient ipatient;
    private final AuthService authService;
    private  final Idoctor idoctor;
    private final EmailService emailService;
//    private final EmailService emailService;


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("register")
    public ResponseEntity<?> addPatient(@RequestPart(value = "file" ) MultipartFile image,
//                                        @RequestParam(value = "patient") String patient_string
                                        @RequestBody Patient patient
                                      ) throws IOException {

        if (  image.isEmpty() ) {
            return ResponseEntity.badRequest().body("no image");
        }

        try{

//            ObjectMapper objectMapper = new ObjectMapper();
//            Patient patient;
//            patient = objectMapper.readValue(patient_string , Patient.class);
//            System.out.println(patient);
//

             String email = patient.getEmail();
            if(ipatient.findByEmail(email).isPresent()){
            return ResponseEntity.status(HttpStatus.IM_USED).body("this email already exists");
        }
            System.out.println("new");
            System.out.println(patient);


        //TODO finish later
//        emailService.sendEmail("heerraji123@gmail.com","done","welcome");

        return ResponseEntity.ok(authService.register(patient, image));

        } catch(JsonProcessingException e){
            return ResponseEntity.badRequest().body("problem"+ e);
        }
    }
    @CrossOrigin
    @PostMapping("login")
    public ResponseEntity<?> authLogin(@RequestBody AuthForm authForm) {

        try{
            AuthenticationResponse authenticationResponse=authService.auhtenticate(authForm);
            return ResponseEntity.ok(authenticationResponse);
        }catch(IncorrectPasswordException  e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED ).body(e.getMessage());


        }


    }

@CrossOrigin
@GetMapping("/doctor-image/{id}")
public ResponseEntity<byte[]> getImage(@PathVariable("id") Long doctor_id)
        throws IOException {
    System.out.println(doctor_id);
    Doctor doctor = idoctor.findDoctor(doctor_id).orElse(null);
    assert doctor != null;
    String path = "doctors-photos/"+ doctor.getUser_id()+ "/display_picture/" +doctor.getImage_name();
    Path imagePath = Paths.get(path);

    byte[] imageBytes = Files.readAllBytes(imagePath);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);
    headers.setContentLength(imageBytes.length);
    return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
}




    //maybe later : create this endpoint to allow user to change their photo
    @CrossOrigin
    @PostMapping("change-image")
    public ResponseEntity<?> image(@RequestBody(required = false) MultipartFile image , @AuthenticationPrincipal User user) throws IOException {
        System.out.println(user);
        String uploadDir = "user-photos/" + 33;
        FileUploadUtil.saveFile(uploadDir, image.getOriginalFilename(), image);
        return ResponseEntity.ok().build();
    }


// this end point wast a test to see if sending email works
@CrossOrigin
@PostMapping("send")
    public String sendMail(@RequestBody EmailDetails details)
    {

        System.out.println(details);
        return  emailService.sendSimpleMail(details);
    }







}
