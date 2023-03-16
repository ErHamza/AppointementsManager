package com.rdv.gestionrdvs.Repositories;

import com.rdv.gestionrdvs.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

      Optional<Patient> findById(Long id);

      Optional<Patient> findPatientByEmail(String email);

}
