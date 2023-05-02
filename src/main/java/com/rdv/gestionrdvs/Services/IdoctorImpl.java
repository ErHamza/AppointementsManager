package com.rdv.gestionrdvs.Services;

import com.rdv.gestionrdvs.Repositories.DoctorRepository;
import com.rdv.gestionrdvs.entities.Doctor;
import com.rdv.gestionrdvs.entities.Speciality;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class IdoctorImpl implements Idoctor {
    private DoctorRepository doctorRepository;
    @Override
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctor(Long id) {
    }
    @Override
    public Optional<Doctor> findDoctor(Long id) {
        return doctorRepository.findById(id);
    }
    @Override
    public List<Doctor> doctorsList() {

        return doctorRepository.findAll();

    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return doctorRepository.findDoctorByEmail(email);
    }

    @Override
    public List<Doctor> doctorsListBySpeciality(Speciality speciality) {
        return doctorRepository.findDoctorBySpeciality(speciality);
    }
}
