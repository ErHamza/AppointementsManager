package com.rdv.gestionrdvs.Services;

import com.rdv.gestionrdvs.entities.Consultation;
import com.rdv.gestionrdvs.entities.Rdv;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Iconsultation {
    Consultation addConsultation(Consultation consultation);
    List<Consultation> listConsultation();

}
