package com.rdv.gestionrdvs.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Rdv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rdv_id;
    private LocalDateTime date_rdv;
    private Boolean isCanceld;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn( foreignKey = @ForeignKey(name =
    "fk_doctor_id"))
    @JsonIgnoreProperties("rdv")
    private Doctor doctor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(  foreignKey = @ForeignKey(name=
    "fk_patient_id"))
    @JsonIgnoreProperties("rdv")
    private Patient patient;

//    @OneToOne
//    @JoinColumn(name = "consultation-id")
//    private Consultation consultation;

}
