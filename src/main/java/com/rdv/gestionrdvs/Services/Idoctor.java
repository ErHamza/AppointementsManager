package com.rdv.gestionrdvs.Services;

import com.rdv.gestionrdvs.entities.Doctor;
import com.rdv.gestionrdvs.entities.Speciality;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Idoctor {
    Doctor addDoctor(Doctor doctor);
    void deleteDoctor(Long id);
    Optional<Doctor> findDoctor(Long id);
    List<Doctor> doctorsList();
    Optional<Doctor> findByEmail(String email);
    List<Doctor> doctorsListBySpeciality(Speciality speciality);


}
