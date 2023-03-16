package com.rdv.gestionrdvs.Services;

import com.rdv.gestionrdvs.Repositories.PatientRepository;
import com.rdv.gestionrdvs.entities.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IpatientImpl implements Ipatient {

    private PatientRepository patientRepository;
    @Override
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void DeletePatient(Long id) {

    }

    @Override
    public Optional<Patient> findPatient(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public List<Patient> patientsList() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findPatientByEmail(email);
    }
}
