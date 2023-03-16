package com.rdv.gestionrdvs.Services;

import com.rdv.gestionrdvs.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface Ipatient {
    Patient addPatient(Patient patient);
    void DeletePatient(Long id);
    Optional<Patient> findPatient(Long id);
    List<Patient> patientsList();
    Optional<Patient> findByEmail(String email);

}
