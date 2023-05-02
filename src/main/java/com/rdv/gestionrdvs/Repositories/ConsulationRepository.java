package com.rdv.gestionrdvs.Repositories;

import com.rdv.gestionrdvs.entities.Consultation;
import com.rdv.gestionrdvs.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsulationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByRdv_Doctor(Doctor doctor);
}
