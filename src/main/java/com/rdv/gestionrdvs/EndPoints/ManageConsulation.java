package com.rdv.gestionrdvs.EndPoints;
import com.rdv.gestionrdvs.Services.Iconsultation;
import com.rdv.gestionrdvs.Services.Irdv;
import com.rdv.gestionrdvs.entities.Consultation;
import com.rdv.gestionrdvs.entities.Rdv;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor

@RequestMapping("api/v0/consultations")
public class ManageConsulation {
    private final Irdv irdv;
    private final Iconsultation iconsulation;


    @PostMapping("/add")
    private Consultation addConsulaion(@RequestBody Map<String, Long> map){
        String diagnostic = map.get("diagnostic").toString();
        Long rdv_id = Long.parseLong(map.get("rdv_id").toString());
        System.out.println(rdv_id);
        Rdv rdv = irdv.findRdvById(rdv_id).orElse(null);
        System.out.println(rdv);
        Consultation consultation= new Consultation(null, diagnostic, rdv);
        return iconsulation.addConsultation(consultation);
    }

    @GetMapping("/list")
        List<Consultation> listerConsulation(){

            return iconsulation.listConsultation();
        }

}
