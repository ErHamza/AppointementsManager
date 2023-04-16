package com.rdv.gestionrdvs.EndPoints;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.rdv.gestionrdvs.Authentification.AuthService;
import com.rdv.gestionrdvs.Authentification.AuthForm;
import com.rdv.gestionrdvs.Authentification.AuthenticationResponse;
//import com.rdv.gestionrdvs.Services.EmailService;
import com.rdv.gestionrdvs.Authentification.RegisterForm;
import com.rdv.gestionrdvs.Services.FileUploadUtil;
import com.rdv.gestionrdvs.Services.Ipatient;
import com.rdv.gestionrdvs.entities.Patient;
import com.rdv.gestionrdvs.entities.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class Authentification {

    private final Ipatient ipatient;
    private final AuthService authService;
//    private final EmailService emailService;


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("register")
    public ResponseEntity<?> addPatient(@RequestParam("file") MultipartFile image,
                                        @RequestParam("patient") String patient_string) throws IOException {

        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("no image");
        }

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Patient patient;
            patient = objectMapper.readValue(patient_string , Patient.class);
            //maybe not the best approach
            Patient new_patient = new Patient(null, patient.getUsername()
            , patient.getPassword(),patient.getEmail(), patient.getPhone_number() ,
                    image.getOriginalFilename() );

        String email = patient.getEmail();
        if(ipatient.findByEmail(email).isPresent()){
            return ResponseEntity.status(HttpStatus.IM_USED).body("this email already exists");
        }


        //TODO finish later
//        emailService.sendEmail("heerraji123@gmail.com","done","welcome");

        return ResponseEntity.ok(authService.register(new_patient, image));

        } catch(JsonProcessingException e){
            return ResponseEntity.badRequest().body("problem"+ e);
        }
    }
    @CrossOrigin
    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> authLogin(@RequestBody AuthForm authForm) {


        return ResponseEntity.ok(authService.auhtenticate(authForm));

    }

@CrossOrigin
@GetMapping("/user-image")
public ResponseEntity<byte[]> getImage(@AuthenticationPrincipal User user)
        throws IOException {

    System.out.println(user);
    String path = "user-photos/"+ user.getUser_id()+ "/display_picture/" +user.getImage_name();
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
    public ResponseEntity<?> image(@RequestBody MultipartFile image , @AuthenticationPrincipal User user) throws IOException {
        System.out.println(user);
        String uploadDir = "user-photos/" + 33;
        FileUploadUtil.saveFile(uploadDir, image.getOriginalFilename(), image);


        return ResponseEntity.ok().build();
    }






}
