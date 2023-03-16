package com.rdv.gestionrdvs.EndPoints;


import com.rdv.gestionrdvs.RdvTools.Rdvtools;
import com.rdv.gestionrdvs.Services.Irdv;
import com.rdv.gestionrdvs.entities.Doctor;
import com.rdv.gestionrdvs.entities.Rdv;
import com.rdv.gestionrdvs.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/doctor/rdv")
public class ManageRdvByDoctor {
    private static final int MAX_NUMBER_OF_APPOINTMENTS_PER_DAY = 10;
    private final Irdv irdv;
    private final Rdvtools rdvtools;


   @PutMapping("cancel/{id}")
   public ResponseEntity CancelRdv(@AuthenticationPrincipal User user, @PathVariable Long id){
       if (id==null || irdv.findRdvById(id).isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
       Rdv rdv= irdv.findRdvById(id).orElse(null);
       assert rdv != null;
       if(!user.getUser_id().equals(rdv.getDoctor().getUser_id() )){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       }
       irdv.cancelRdv(id);
       return ResponseEntity.ok().build();
   }

   @GetMapping("list")
   public List<Rdv> getListRdvDoctor(@AuthenticationPrincipal Doctor doctor){
       return irdv.listRdvbyDoctor(doctor);
   }

   @PutMapping("postpone")
    public ResponseEntity postpone(@RequestBody Map<?,?> map ,@AuthenticationPrincipal User user){

       Long id = Long.parseLong( map.get("rdv_id").toString());
       Rdv rdv = irdv.findRdvById(id).orElse(null);
       if ( rdv==null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
       if(!user.getUser_id().equals( rdv.getDoctor().getUser_id() )){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       }

       long timestamp =Long.parseLong( map.get("timestamp").toString() );
       Instant instant= Instant.ofEpochMilli(timestamp);
       LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
       Long number_Appointements = rdvtools.CountAppointementsForDoctor(dateTime,rdv.getDoctor());
       if (number_Appointements >=MAX_NUMBER_OF_APPOINTMENTS_PER_DAY ){
           return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("schedule is full");
       }

       rdv.setDate_rdv(dateTime);
       irdv.addRdv(rdv);
       return ResponseEntity.ok().build();
   }


}
