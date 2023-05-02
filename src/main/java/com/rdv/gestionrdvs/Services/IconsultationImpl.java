package com.rdv.gestionrdvs.Services;

import com.rdv.gestionrdvs.Repositories.ConsulationRepository;
import com.rdv.gestionrdvs.entities.Consultation;
import com.rdv.gestionrdvs.entities.Doctor;
import com.rdv.gestionrdvs.entities.Rdv;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class IconsultationImpl implements Iconsultation {
    private ConsulationRepository consulationRepository;

    @Override
    public Consultation addConsultation(Consultation consultation) {

        return consulationRepository.save(consultation);
    }

    @Override
    public List<Consultation> listConsultation() {
        return consulationRepository.findAll();
    }

    @Override
    public List<Consultation> findConsultations(Doctor doctor) {
        return consulationRepository.findByRdv_Doctor(doctor);
    }
}
