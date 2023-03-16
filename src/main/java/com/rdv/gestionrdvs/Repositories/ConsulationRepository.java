package com.rdv.gestionrdvs.Repositories;

import com.rdv.gestionrdvs.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsulationRepository extends JpaRepository<Consultation, Long> {
}
